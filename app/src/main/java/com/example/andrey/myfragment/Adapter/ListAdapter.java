package com.example.andrey.myfragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andrey.myfragment.ItemObject;
import com.example.andrey.myfragment.R;

import java.util.ArrayList;

/**
 * Created by andrey on 22.01.16.
 */
public class ListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<ItemObject> itemObjectsArray;


    public ListAdapter(Context ctx, ArrayList<ItemObject> itemObjectsArray){
        this.itemObjectsArray = itemObjectsArray;
        this.ctx = ctx;


        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount() {
        return itemObjectsArray.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View view = lInflater.inflate(R.layout.item, parent, false);
            ((TextView) view.findViewById(R.id.itemText)).setText(itemObjectsArray.get(position).getText());
            ((TextView) view.findViewById(R.id.itemTime)).setText(itemObjectsArray.get(position).getDate());

        return view;
    }
}
