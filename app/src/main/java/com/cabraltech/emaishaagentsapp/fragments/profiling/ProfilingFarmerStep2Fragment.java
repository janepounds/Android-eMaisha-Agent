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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep2Binding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep3Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingFarmerStep2Fragment extends Fragment {
    private Context context;
    private FragmentProfilingFarmerStep2Binding binding;
    private NavController navController;
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    String district,sub_county,village,gender, marital_status, religion, education_level, language_used, nationality,first_name,last_name,dob,age,household_size,household_head,source_of_income;

    public ProfilingFarmerStep2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        first_name = getArguments().getString("first_name");
        last_name = getArguments().getString("last_name");
        gender = getArguments().getString("gender");
        age = getArguments().getString("age");
        marital_status = getArguments().getString("marital_status");
        religion = getArguments().getString("religion");
        education_level = getArguments().getString("education_level");
        language_used = getArguments().getString("language_used");
        nationality = getArguments().getString("nationality");
        dob = getArguments().getString("dob");
        household_size = getArguments().getString("household_size");
        household_head = getArguments().getString("household_head");
        source_of_income = getArguments().getString("source_of_income");

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_farmer_step2,container,false);
        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.farmerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Regular.ttf");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Farmer Profiling");
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        Spinner spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtPhone = view.findViewById(R.id.phone_number_et);
        final EditText etxt_next_of_kin = view.findViewById(R.id.next_of_kin_et);
        final EditText etxt_next_of_kin_relation = view.findViewById(R.id.next_of_kin_relation_et);
        final EditText etxt_next_of_kin_contact = view.findViewById(R.id.next_of_kin_contact_et);
        final EditText etxt_next_of_kin_address = view.findViewById(R.id.next_of_kin_address_et);


        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinSubCounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sub_county = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinVillage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                village = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone_number = etxtPhone.getText().toString().trim();
                String next_of_kin = etxt_next_of_kin.getText().toString().trim();
                String next_of_kin_relation = etxt_next_of_kin_relation.getText().toString().trim();
                String next_of_kin_contact = etxt_next_of_kin_contact.getText().toString().trim();
                String next_of_kin_address = etxt_next_of_kin_address.getText().toString().trim();


                Bundle bundle = new Bundle();
                bundle.putString("first_name", first_name);
                bundle.putString("last_name", last_name);
                bundle.putString("dob", dob);
                bundle.putString("age", age);
                bundle.putString("gender", gender);
                bundle.putString("religion", religion);
                bundle.putString("nationality", nationality);
                bundle.putString("level_of_education", education_level);
                bundle.putString("marital_status", marital_status);
                bundle.putString("language_used", language_used);
                bundle.putString("nationality", nationality);
                bundle.putString("household_size", household_size);
                bundle.putString("household_head", household_head);
                bundle.putString("source_of_income", source_of_income);
                bundle.putString("phone_number", phone_number);
                bundle.putString("next_of_kin", next_of_kin);
                bundle.putString("next_of_kin_relation",next_of_kin_relation);
                bundle.putString("next_of_kin_contact",next_of_kin_contact);
                bundle.putString("next_of_kin_address",next_of_kin_address);
                //navigate to step 3
                navController.navigate(R.id.action_profilingFarmerStep2Fragment_to_profilingFarmerStep3Fragment,bundle);

            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //popback stack to step 1
                navController.popBackStack();
            }
        });



    }
}