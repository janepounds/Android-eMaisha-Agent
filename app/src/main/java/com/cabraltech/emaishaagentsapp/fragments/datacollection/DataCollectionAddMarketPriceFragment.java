package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.content.Context;
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

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketBinding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketPriceBinding;

public class DataCollectionAddMarketPriceFragment extends Fragment {
    private FragmentDataCollectionAddMarketPriceBinding fragmentDataCollectionAddMarketPriceBinding;
    private Context context;
    private NavController navController;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public DataCollectionAddMarketPriceFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDataCollectionAddMarketPriceBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_data_collection_add_market_price, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Commodity Price");
        return fragmentDataCollectionAddMarketPriceBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        fragmentDataCollectionAddMarketPriceBinding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 1

                navController.navigate(R.id.action_dataCollectionAddMarketPriceFragment_to_dataCollectionConfirmMarketPriceFragment);

            }
        });

    }
}