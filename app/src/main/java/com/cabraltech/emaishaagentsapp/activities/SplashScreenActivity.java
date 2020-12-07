package com.cabraltech.emaishaagentsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.cabraltech.emaishaagentsapp.models.Regions;
import com.cabraltech.emaishaagentsapp.network.APIClient;
import com.cabraltech.emaishaagentsapp.network.BroadcastService;
import com.cabraltech.emaishaagentsapp.network.NetworkStateChecker;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {
    private static final String TAG = "SplashScreenActivity";

    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        t = new Thread(() -> {
            RequestAllRegions();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//              startForegroundService(new Intent(getBaseContext(),BroadcastService.class));
                Intent serviceIntent = new Intent(getBaseContext(), BroadcastService.class);
                startService(serviceIntent);
                bindService(serviceIntent, new ServiceConnection() {
                    @Override
                    public void onServiceConnected(ComponentName name, IBinder service) {
                        //retrieve an instance of the service here from the IBinder returned
                        //from the onBind method to communicate with
                    }

                    @Override
                    public void onServiceDisconnected(ComponentName name) {
                    }
                }, getBaseContext().BIND_AUTO_CREATE);
            } else {
                startService(new Intent(getBaseContext(), BroadcastService.class));
            }

            try {
                t.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (sharedPreferences.getBoolean("isLogged_in", false)) {
                    startActivity(new Intent(this, DashboardActivity.class));
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                }
                finish();
            }
        });
        t.start();
    }

    public void RequestAllRegions() {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getApplicationContext());
        databaseAccess.open();
        int regionId = databaseAccess.getMaxRegionId();
        Log.d(TAG, "RequestAllRegions: " + regionId);

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
            } else {
                Log.e(TAG, "RequestAllRegions: Response is not successful");

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}