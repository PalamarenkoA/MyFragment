package com.example.andrey.myfragment.Fragment;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.andrey.myfragment.Adapter.ListAdapterFromDatabase;
import com.example.andrey.myfragment.Adapter.ListAdapterFromFirebase;
import com.example.andrey.myfragment.SQLite.DBHelper;
import com.example.andrey.myfragment.BroadcastReceiver.InternetListener;
import com.example.andrey.myfragment.Object.ItemObject;
import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.R;
import com.firebase.client.Firebase;
import java.util.ArrayList;



public class FragmentList  extends android.app.Fragment {
    SharedPreferences userName;
    Firebase myFirebaseRef;
    ListView listView;
    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    @Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        userName = getActivity().getPreferences(MainActivity.context.MODE_PRIVATE);
        View v = inflater.inflate(R.layout.fragment_fragment_list, container, false);
        listView = (ListView) v.findViewById(R.id.listView);
        setAdapter();



        return v;
        }


    private void setAdapter(){
        if(InternetListener.NETWORK) {
            myFirebaseRef = new Firebase("https://myfragment.firebaseio.com/").child("chat");
            ListAdapterFromFirebase listAdapter = new ListAdapterFromFirebase(myFirebaseRef.limit(50), getActivity(), R.layout.item, userName.getString(MainActivity.SAVED_USER_NAME, "User1"));
            listView.setAdapter(listAdapter);
        }else{
            ListAdapterFromDatabase listAdapter = new ListAdapterFromDatabase(MainActivity.context,readDB());
            listView.setAdapter(listAdapter);
        }
    }
    private ArrayList<ItemObject> readDB(){
        DBHelper dbHelper = new DBHelper(MainActivity.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("myMes", null, null, null, null, null, null);
        ArrayList<ItemObject> arrayList = new ArrayList<>();
        if (c.moveToFirst()) {
            int textColIndex = c.getColumnIndex("text");
            int timeColIndex = c.getColumnIndex("time");
            int userColIndex = c.getColumnIndex("user");
            do {
                ItemObject itemObject = new ItemObject(
                        c.getString(textColIndex),
                        c.getString(timeColIndex), c.getString(userColIndex));
                arrayList.add(itemObject);
               } while (c.moveToNext());
        } else Log.d("logo", "0 rows");
        c.close();
        return arrayList;
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
    }


        }