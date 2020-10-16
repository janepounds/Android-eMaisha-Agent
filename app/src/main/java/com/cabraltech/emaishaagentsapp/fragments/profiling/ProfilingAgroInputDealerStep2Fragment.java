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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerBinding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerStep2Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingAgroInputDealerStep2Fragment extends Fragment {

    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep2Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingAgroInputDealerStep2Fragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_agro_input_dealer_step2,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Enrolling Aqro Input Retailer");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.agroInputDealerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 2
                navController.popBackStack();

            }
        });
        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 2
                navController.navigate(R.id.action_profilingAgroInputDealerStep2Fragment_to_profilingAgroInputDealerStep3Fragment);

            }
        });


    }
}