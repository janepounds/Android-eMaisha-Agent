package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DataCollectionActivity;
import com.cabraltech.emaishaagentsapp.activities.MarketAndServicesActivity;
import com.cabraltech.emaishaagentsapp.activities.ProfilingActivity;
import com.cabraltech.emaishaagentsapp.models.HomeViewModel;

public class HomeFragment extends Fragment {

LinearLayout profilingLayout, walletLayout, dataCollectionLayout, marketServicesLayout;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("eMaisha Agent");


        InitializeDashboard(view);
        return view;

    }

    public void  InitializeDashboard(View view){

        profilingLayout = view.findViewById(R.id.layout_dashboard_profiling);
        walletLayout = view.findViewById(R.id.layout_dashboard_wallet);
        dataCollectionLayout = view.findViewById(R.id.layout_dashboard_data_collection);
        marketServicesLayout = view.findViewById(R.id.layout_dashboard_markets_service);

        profilingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfilingActivity.class);
                v.getContext().startActivity(intent);

            }
        });
        walletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dataCollectionLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DataCollectionActivity.class);
                v.getContext().startActivity(intent);
            }
        });
        marketServicesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MarketAndServicesActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}