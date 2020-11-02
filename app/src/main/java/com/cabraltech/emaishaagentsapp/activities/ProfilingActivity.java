package com.cabraltech.emaishaagentsapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.fragments.bottomnavbar.AccountFragment;
import com.cabraltech.emaishaagentsapp.fragments.bottomnavbar.HomeFragment;
import com.cabraltech.emaishaagentsapp.fragments.bottomnavbar.OffersFragment;
import com.cabraltech.emaishaagentsapp.fragments.bottomnavbar.TransactionsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfilingActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling);
        getSupportActionBar().setTitle(R.string.label_profiling);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
       
      //  BottomNavigationView navView = findViewById(R.id.nav_view);
       // navView.setItemIconTintList(null);
       // navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }
   /* private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_offers:
                    selectedFragment = new OffersFragment();
                    break;
                case R.id.navigation_transactions:
                    selectedFragment = new TransactionsFragment();
                    break;
                case R.id.navigation_account:
                    selectedFragment = new AccountFragment();
                    break;
            }

            ProfilingActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };*/

//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                //do something here like
//
//                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//                    getSupportFragmentManager().popBackStack();
//                }
//
//                return true;
//        }
//        return false;
//    }
//    @Override
//    public boolean onSupportNavigateUp() {
//        onBackPressed();
//        return true;
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
//            getSupportFragmentManager().popBackStack();
//        } else {
//            super.onBackPressed();
//        }
//    }


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