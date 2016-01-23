package com.example.andrey.myfragment;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MyService extends Service {
    private Firebase myFirebaseRef;
    private ChildEventListener mListener;
    private Uri uri;
    private Context context;
    static android.support.v4.app.NotificationCompat.Builder builder;
    static NotificationManager mNotificationManager;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
        context = this;
        myFirebaseRef = new Firebase("https://myfragment.firebaseio.com/").child("chat");
         mListener = myFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Intent intent = new Intent(context,MainActivity.class);
                PendingIntent pendingIntentApp = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                TelephonyManager telephonyManager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                ItemObject item = dataSnapshot.getValue(ItemObject.class);
                if (!telephonyManager.getDeviceId().equals(item.getId())) {
                    uri = Uri.parse("android.resource://com.example.andrey.myfragment/" + R.raw.viber_sms);
                    long[] vibrate = new long[]{1000, 1000, 1000, 1000, 1000};
                    builder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.drawable.chat_icon)
                            .setContentText(item.getUserName()+": " + item.getText())
                            .setContentTitle("Новое сообщение")
                            .setSound(uri)
                            .setContentIntent(pendingIntentApp)
                            .setVibrate(vibrate);
                    mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, builder.build());
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
}
