package com.cabraltech.emaishaagentsapp;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.cabraltech.emaishaagentsapp.database.DB_Handler;
import com.cabraltech.emaishaagentsapp.database.DB_Manager;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;

public class MyApp extends MultiDexApplication {
    private static final String TAG = "MyApp";
    private static Context context;
    private static DB_Handler db_handler;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

//        Log.d(TAG, "onCreate: MyApp onCreate");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/JosefinSans-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        // initialize DB_Handler and DB_Manager
        db_handler = new DB_Handler();
        DB_Manager.initializeInstance(db_handler);
    }

    public static Context getContext() {
        return context;
    }
}
