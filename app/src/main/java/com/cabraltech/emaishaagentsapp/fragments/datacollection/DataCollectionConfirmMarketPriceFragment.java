package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionConfirmMarketPriceBinding;

public class DataCollectionConfirmMarketPriceFragment extends Fragment {
    private NavController navController;
    private FragmentDataCollectionConfirmMarketPriceBinding binding;
    private String date,commodity,variety,market_name,units,wholesale_price,retail_price;
    private String profiledUser = "market_price";
    private TextView dateTv,varietyTv,marketTv,retailTv,wholesaleTv;





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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_collection_confirm_market_price, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Price Details");

        date = getArguments().getString("date");
        commodity = getArguments().getString("commodity");
        variety = getArguments().getString("variety");
        market_name = getArguments().getString("market_name");
        units = getArguments().getString("units");
        wholesale_price = getArguments().getString("wholesale_price");
        retail_price = getArguments().getString("retail_sale");


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        dateTv = view.findViewById(R.id.conform_date);
        varietyTv = view.findViewById(R.id.variety);
        marketTv = view.findViewById(R.id.market);
        retailTv = view.findViewById(R.id.retail);
        wholesaleTv = view.findViewById(R.id.wholesale);



        dateTv.setText(date);
        varietyTv.setText(variety);
        marketTv.setText(market_name);
        retailTv.setText(retail_price +" /"+ units);
        wholesaleTv.setText(wholesale_price +" /"+ units);

        Bundle bundle = new Bundle();
        bundle.putString("profiledUser",profiledUser);
        bundle.putString("market_name",market_name);
        binding.confirmDetailsLayout.setOnClickListener(v -> navController.navigate(R.id.action_dataCollectionConfirmMarketPriceFragment_to_sucessDialogFragment,bundle));



    }
}