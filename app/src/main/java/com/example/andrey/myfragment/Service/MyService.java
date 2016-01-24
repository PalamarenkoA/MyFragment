package com.example.andrey.myfragment.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;

import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.Object.ItemObject;
import com.example.andrey.myfragment.R;
import com.example.andrey.myfragment.SQLite.DBHelper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MyService extends Service {
    private ChildEventListener mListener;
    private Uri uri;
    private Context context;
    static android.support.v4.app.NotificationCompat.Builder builder;
    static NotificationManager mNotificationManager;
    private DBHelper dbHelper;
    SQLiteDatabase db;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        context = this;

        Firebase myFirebaseRef = new Firebase("https://myfragment.firebaseio.com/").child("chat");
         mListener = myFirebaseRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                 if(keyCheck(dataSnapshot)){
                 dbKeyAdd(dataSnapshot);
                 Intent intent = new Intent(context, MainActivity.class);
                 PendingIntent pendingIntentApp = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                 TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                 ItemObject item = dataSnapshot.getValue(ItemObject.class);
                 if (!telephonyManager.getDeviceId().equals(item.getId())) {
                     uri = Uri.parse("android.resource://com.example.andrey.myfragment/" + R.raw.viber_sms);
                     long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};
                     builder = new NotificationCompat.Builder(context)
                             .setSmallIcon(R.drawable.chat_icon)
                             .setContentText(item.getUserName() + ": " + item.getText())
                             .setContentTitle("Новое сообщение")
                             .setSound(uri)
                             .setContentIntent(pendingIntentApp)
                             .setVibrate(vibrate);
                     mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                     mNotificationManager.notify(1, builder.build());}
                 }

             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(FirebaseError firebaseError) {

             }
         });

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    private void dbKeyAdd(DataSnapshot dataSnapshot){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("key",dataSnapshot.getKey());
        db.insert("myMesId", null, cv);
    }
    private boolean keyCheck(DataSnapshot dataSnapshot){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
        Cursor c = db.query("myMesId", null, null, null, null, null, null);
        ArrayList<String> arrayList = new ArrayList<>();
        if (c.moveToFirst()) {
            int keyColIndex = c.getColumnIndex("key");
            do {
                arrayList.add(c.getString(keyColIndex));
            } while (c.moveToNext());
        }
        c.close();
        if(!arrayList.contains(dataSnapshot.getKey())){
            return true;
        }else{
            return false;
        }}
}
