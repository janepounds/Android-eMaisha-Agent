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
    String district, sub_county, category, commodities;


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

//        Spinner spinCategory = view.findViewById(R.id.category_spinner);
        final EditText etxtBusinessName = view.findViewById(R.id.business_name_et);
        final EditText etxtProprietorName = view.findViewById(R.id.proprietor_name_et);
        Spinner spinCommodities = view.findViewById(R.id.commodities_spinner);
        final EditText etxtPhone = view.findViewById(R.id.phone_number_et);
        final EditText etxtEmail = view.findViewById(R.id.email_address_et);
        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubcounty = view.findViewById(R.id.sub_county_spinner);
//        final EditText etxtActualAddress = view.findViewById(R.id.actual_address_et);
        Button btnSubmit = view.findViewById(R.id.submit_button);

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

//        spinCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                category = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        spinCommodities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                commodities = adapterView.getItemAtPosition(i).toString();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String business_name = etxtBusinessName.getText().toString().trim();
//                String proprietor_name = etxtProprietorName.getText().toString().trim();
//                String phone = etxtPhone.getText().toString().trim();
//                String email = etxtEmail.getText().toString().trim();
////                String actual_address = etxtActualAddress.getText().toString().trim();
//
//
//                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
//                databaseAccess.open();
//
//                boolean check = databaseAccess.addTrader(category, business_name, proprietor_name, commodities, phone, email, district, sub_county, "actual_address");
//                if (check) {
//                    Toast.makeText(getActivity(), "Agro Trader Added Successfully", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();
//
//                }
//
//
//            }
//        });


        navController = Navigation.findNavController(view);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 2
                navController.navigate(R.id.action_profilingBulkBuyersFragment_to_profilingBulkBuyersStep2Fragment);

            }
        });


    }
}