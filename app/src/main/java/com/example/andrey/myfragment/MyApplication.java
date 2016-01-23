package com.example.andrey.myfragment;

import android.app.Application;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;

/**
 * Created by andrey on 23.01.16.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
      }}