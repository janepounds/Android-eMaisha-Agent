package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cabraltech.emaishaagentsapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DataCollectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_collection);
        getSupportActionBar().setTitle(R.string.data_collection);
       // BottomNavigationView navView = findViewById(R.id.nav_view);
      //  navView.setItemIconTintList(null);
    }
}