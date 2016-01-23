package com.example.andrey.myfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.andrey.myfragment.BroadcastReceiver.InternetListener;
import com.example.andrey.myfragment.Fragment.FragmentInput;
import com.example.andrey.myfragment.Fragment.FragmentList;
import com.example.andrey.myfragment.Object.ItemObject;
import com.example.andrey.myfragment.SQLite.DBHelper;
import com.example.andrey.myfragment.Service.MyService;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentInput.onSaveText {
    private FragmentTransaction fTrans;
    private  Fragment fragmentInput;
    private Fragment fragmentList;
    public static String SAVED_USER_NAME = "SaveUserName";
    public static Context context;
    private DBHelper dbHelper;
    private SharedPreferences userName;
    private Firebase myFirebaseRef;
    private ChildEventListener mListener;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateNavigationDrawerToolbar();
        context = this;
        startService(new Intent(this,MyService.class));
        myFirebaseRef = new Firebase("https://myfragment.firebaseio.com/").child("chat");
        fragmentInput = new FragmentInput();
        fragmentList = new FragmentList();
        userName = getPreferences(MODE_PRIVATE);
        dbHelper = new DBHelper(this);
        //Add first fragment
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.folder, fragmentInput);
        fTrans.commit();
        if(InternetListener.NETWORK){
        DBadd(mListener);}



    }

    private void onCreateNavigationDrawerToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       switch (item.getItemId()){
            case R.id.itemListFragment:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, fragmentList,fragmentInput , R.id.folder);
                fragmentTransactionExtended.addTransition(0);
                fragmentTransactionExtended.commit();
                break;
            case R.id.itemInputFragment:
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
                fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction2, fragmentInput, fragmentList, R.id.folder);

                fragmentTransactionExtended.addTransition(0);
                fragmentTransactionExtended.commit();
                break;
        }

               DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Это реализация интерфейса с фрагмента
    @Override
    public void saveText(String text, long time) {
      if(InternetListener.NETWORK) {
          TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
          ItemObject itemObject = new ItemObject(text, String.valueOf(new Date(time)),
                  userName.getString(MainActivity.SAVED_USER_NAME, "User1"), telephonyManager.getDeviceId());
          myFirebaseRef.push().setValue(itemObject);
      }else{
          db = dbHelper.getWritableDatabase();
          ContentValues cv = new ContentValues();
          cv.put("time",String.valueOf(new Date(time)));
          cv.put("user", userName.getString(MainActivity.SAVED_USER_NAME, "User1"));
          cv.put("text", text);
          db.insert("myMes", null, cv);
      }

        Toast.makeText(MainActivity.context, "Сохранено", Toast.LENGTH_LONG).show();




    }
    private void DBadd(ChildEventListener mListener){
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
