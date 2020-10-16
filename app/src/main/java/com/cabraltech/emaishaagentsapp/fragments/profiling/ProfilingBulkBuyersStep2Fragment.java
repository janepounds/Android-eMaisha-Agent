package com.cabraltech.emaishaagentsapp.fragments.profiling;

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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingBulkBuyersBinding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingBulkBuyersStep2Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingBulkBuyersStep2Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentProfilingBulkBuyersStep2Binding binding;

    String[] descriptionData = {"Contact\nDetails", "Business\nDetails"};

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingBulkBuyersStep2Fragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_bulk_buyers_step2,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Enroll Agro Trader");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.bulkBuyersProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit

            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit
                navController.popBackStack();
            }
        });


    }
}