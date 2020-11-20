package com.cabraltech.emaishaagentsapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;

import com.cabraltech.emaishaagentsapp.database.DB_Handler;
import com.cabraltech.emaishaagentsapp.database.DB_Manager;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.models.ResponseData;
import com.cabraltech.emaishaagentsapp.network.APIClient;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyApp extends MultiDexApplication {
    private static final String TAG = "MyApp";
    private static Context context;
    private static DB_Handler db_handler;
    private List<HashMap<String, String>> farmersList, pestReport, scoutingReport, marketPrices, marketDetails, association, agroInputDealers, bulkBuyers;
    private String sync_status;

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
