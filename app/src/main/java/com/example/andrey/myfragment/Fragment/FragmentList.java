package com.example.andrey.myfragment.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.andrey.myfragment.Adapter.ListAdapter;
import com.example.andrey.myfragment.DBHelper;
import com.example.andrey.myfragment.ItemObject;
import com.example.andrey.myfragment.MainActivity;
import com.example.andrey.myfragment.R;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by andrey on 22.01.16.
 */
public class FragmentList  extends android.app.Fragment {

@Override
public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {}
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_list, container, false);
        ListView listView = (ListView) v.findViewById(R.id.listView);
        ListAdapter listAdapter = new ListAdapter(MainActivity.context,readDB());
        listView.setAdapter(listAdapter);
        return v;
        }



    public ArrayList<ItemObject> readDB(){
        DBHelper dbHelper = new DBHelper(MainActivity.context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("mytable", null, null, null, null, null, null);
        ArrayList<ItemObject> arrayList = new ArrayList<>();
        if (c.moveToFirst()) {


            int idColIndex = c.getColumnIndex("id");
            int textColIndex = c.getColumnIndex("text");
            int timeColIndex = c.getColumnIndex("time");

            do {
                ItemObject itemObject = new ItemObject(c.getInt(idColIndex),c.getString(textColIndex), String.valueOf(new Date(c.getLong(timeColIndex))));
                arrayList.add(itemObject);
               } while (c.moveToNext());
        } else Log.d("logo", "0 rows");
        c.close();
        return arrayList;
    }



        }