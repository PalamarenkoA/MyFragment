package com.example.andrey.myfragment.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andrey.myfragment.R;

/**
 * Created by andrey on 22.01.16.
 */
public class FragmentList  extends android.app.Fragment {

@Override
public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        }


@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fragment_list, container, false);


        return v;
        }


        }