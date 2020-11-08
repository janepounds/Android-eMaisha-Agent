package com.cabraltech.emaishaagentsapp.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class BroadcastService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
       new NetworkStateChecker().checkConnectivity(getApplicationContext());
    }
}
