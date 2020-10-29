package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.cabraltech.emaishaagentsapp.models.Regions;
import com.cabraltech.emaishaagentsapp.network.APIClient;
import com.cabraltech.emaishaagentsapp.network.Api;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";
    Thread t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        t = new Thread(new Runnable() {
            @Override
            public void run() {
                RequestAllRegions();
                try {
                    t.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
        t.start();
    }


    public void RequestAllRegions() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        int regionId = databaseAccess.getMaxRegionId();
        Log.d(TAG, "RequestAllRegions: "+ regionId);

        Call<Regions> call = APIClient.getInstance()
                .getAllRegions(regionId);
        try {
            Response<Regions> response = call.execute();

            if (response.isSuccessful()) {


                Regions regionsData = null;
                regionsData = response.body();
                //Log.e("DataCheck0: ",appSettingsData.getAppDetails().getMaintenance_text());
                String strJson = new Gson().toJson(regionsData);
                List<RegionDetails> regionDetails = regionsData.getData();

                databaseAccess.insertRegionDetails(regionDetails);
//                if (!TextUtils.isEmpty(regionsData.getSuccess()))
//                    cropManagerApp.setAppSettingsDetails(regionsData.getData());
                Log.d(TAG, "RequestAllRegions: " + regionsData);
            }
            else {
                Log.e(TAG, "RequestAllRegions: Response is not successful");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}