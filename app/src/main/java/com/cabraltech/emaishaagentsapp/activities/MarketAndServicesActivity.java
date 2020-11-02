package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cabraltech.emaishaagentsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MarketAndServicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_and_services);
        getSupportActionBar().setTitle(R.string.label_market_services);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        // navView = findViewById(R.id.nav_view);
        //navView.setItemIconTintList(null);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}