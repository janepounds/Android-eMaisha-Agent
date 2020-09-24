package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cabraltech.emaishaagentsapp.R;

public class DataCollectionConfirmMarketPriceFragment extends Fragment {


    public DataCollectionConfirmMarketPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Price Details");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_collection_confirm_market_price, container, false);
    }
}