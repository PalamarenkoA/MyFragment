package com.example.andrey.myfragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.desarrollodroide.libraryfragmenttransactionextended.FragmentTransactionExtended;
import com.example.andrey.myfragment.Fragment.FragmentInput;
import com.example.andrey.myfragment.Fragment.FragmentList;

import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,FragmentInput.onSaveText {
    private FragmentTransaction fTrans;
    private  Fragment fragmentInput;
    private Fragment fragmentList;
    public static Context context;
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        onCreateFloatingVButtonNavigationDrawerToolbar();
        context = this;
        fragmentInput = new FragmentInput();
        fragmentList = new FragmentList();
        dbHelper = new DBHelper(this);
        //Add first fragment
        fTrans = getFragmentManager().beginTransaction();
        fTrans.replace(R.id.folder, fragmentInput);
        fTrans.commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void onCreateFloatingVButtonNavigationDrawerToolbar() {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       switch (item.getItemId()){
            case R.id.itemListFragment:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                FragmentTransactionExtended fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction, fragmentList,fragmentInput , R.id.folder);
                fragmentTransactionExtended.addTransition(3);
                fragmentTransactionExtended.commit();




                break;
            case R.id.itemInputFragment:
                FragmentManager fm2 = getFragmentManager();
                FragmentTransaction fragmentTransaction2 = fm2.beginTransaction();
                fragmentTransactionExtended = new FragmentTransactionExtended(this, fragmentTransaction2, fragmentInput, fragmentList, R.id.folder);
                fragmentTransactionExtended.addTransition(3);
                fragmentTransactionExtended.commit();
                break;
        }

               DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void saveText(String text, long time) {
        ContentValues cv = new ContentValues();
        cv.put("time", time);
        cv.put("text", text);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("mytable", null, cv);
        Toast.makeText(MainActivity.context, "Сохранено", Toast.LENGTH_LONG).show();




    }
}
