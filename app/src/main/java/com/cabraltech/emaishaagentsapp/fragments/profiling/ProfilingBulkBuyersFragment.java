package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingBulkBuyersBinding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingBulkBuyersFragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentProfilingBulkBuyersBinding binding;
    String district, sub_county,village, business_type;


    String[] descriptionData = {"Contact\nDetails", "Business\nDetails"};

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingBulkBuyersFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_bulk_buyers,container,false);
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
        super.onViewCreated(view, savedInstanceState);

        Spinner spinBusinessType = view.findViewById(R.id.business_type_spinner);
        final EditText etxtBusinessName = view.findViewById(R.id.business_name_et);
        final EditText etxtOwner = view.findViewById(R.id.proprietor_name_et);
        final EditText etxtPhone = view.findViewById(R.id.phone_number_et);
        final EditText etxtEmail = view.findViewById(R.id.email_address_et);
        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubcounty = view.findViewById(R.id.sub_county_spinner);
        Spinner spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtFullAddress = view.findViewById(R.id.full_address_et);
        Button btnSubmit = view.findViewById(R.id.next_button);

        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                district = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinSubcounty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spinBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                business_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String business_name = etxtBusinessName.getText().toString().trim();
                String phone = etxtPhone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                String full_address = etxtFullAddress.getText().toString().trim();
                String owner = etxtOwner.getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putString("business_name",business_name);
                bundle.putString("phone",phone);
                bundle.putString("owner",owner);
                bundle.putString("full_address",full_address);
                bundle.putString("email",email);
                bundle.putString("district",district);
                bundle.putString("sub_country",sub_county);
                bundle.putString("village",village);
                bundle.putString("business_type",business_type);


                navController.navigate(R.id.action_profilingBulkBuyersFragment_to_profilingBulkBuyersStep2Fragment,bundle);



            }
        });


    }
}