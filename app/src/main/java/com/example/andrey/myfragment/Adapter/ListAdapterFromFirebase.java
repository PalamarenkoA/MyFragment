package com.example.andrey.myfragment.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.andrey.myfragment.ItemObject;
import com.example.andrey.myfragment.R;
import com.firebase.client.Query;

import java.util.ArrayList;

/**
 * Created by andrey on 22.01.16.
 */
public class ListAdapterFromFirebase extends FirebaseListAdapter<ItemObject> {

    String mUsername;
    public ListAdapterFromFirebase(Query ref, Activity activity, int layout, String mUsername) {
        super(ref, ItemObject.class, layout, activity);
        this.mUsername = mUsername;
    }






    @Override
    protected void populateView(View view, ItemObject model) {
        ((TextView) view.findViewById(R.id.itemText)).setText(model.getText());
        ((TextView) view.findViewById(R.id.itemTime)).setText(model.getDate());
        ((TextView) view.findViewById(R.id.itemUserName)).setText(model.getUserName()+ ":");
    }


}
