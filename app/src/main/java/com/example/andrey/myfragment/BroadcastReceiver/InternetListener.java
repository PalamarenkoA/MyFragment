package com.example.andrey.myfragment.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.Object.ItemObject;
import com.example.andrey.myfragment.SQLite.DBHelper;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class InternetListener extends BroadcastReceiver
{
    public static boolean NETWORK;
    private DBHelper dbHelper;
    private Firebase myFirebaseRef;
    private ChildEventListener mListener;
    SQLiteDatabase db;
    @Override
    public void onReceive( Context context, Intent intent )
    {
       NETWORK = isNetworkAvailable();



        if(NETWORK){
            Toast.makeText(MainActivity.context,"Соеденение установленно",Toast.LENGTH_LONG).show();
            DBadd(mListener);
        }else{
            Toast.makeText(MainActivity.context,"Соеденение не установленно",Toast.LENGTH_LONG).show();
        }


    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) MainActivity.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void DBadd(ChildEventListener mListener){
        myFirebaseRef = new Firebase("https://myfragment.firebaseio.com/").child("chat");
        dbHelper = new DBHelper(MainActivity.context);
        db = dbHelper.getWritableDatabase();
        try {

            db.delete("myMes", null, null);
        }catch (Exception e){

        }
        mListener = myFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ItemObject item = dataSnapshot.getValue(ItemObject.class);
                ContentValues cv = new ContentValues();
                cv.put("time", item.getDate());
                cv.put("user", item.getUserName());
                cv.put("text", item.getText());
                db.insert("myMes", null, cv);
                Log.d("logos", "" + item.getText());

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
}