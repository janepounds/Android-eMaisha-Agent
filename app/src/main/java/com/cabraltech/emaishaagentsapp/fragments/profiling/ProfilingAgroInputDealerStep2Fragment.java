package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerStep2Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingAgroInputDealerStep2Fragment extends Fragment {

    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep2Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};
    String certification_type, registration_status, association_membership,district, sub_county, village, certification,business_name,full_address;
    CheckBox chkMaaif, chkUnada, chkNda;

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

        district = getArguments().getString("district");
        sub_county = getArguments().getString("sub_county");
        village = getArguments().getString("village");
        certification = getArguments().getString("certification");
        business_name = getArguments().getString("business_name");
        full_address = getArguments().getString("full_address");

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_agro_input_dealer_step2, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Enrolling Aqro Input Retailer");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.agroInputDealerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Spinner spinCertificationType = view.findViewById(R.id.certificate_type_spinner);
        Spinner spinRegistrationStatus = view.findViewById(R.id.registration_status_spinner);
        Spinner spinAssociationMember = view.findViewById(R.id.association_membership_spinner);

        EditText etxtRegistrationYear = view.findViewById(R.id.registration_year_et);
        EditText etxtCertificationNumber = view.findViewById(R.id.certificate_number_et);
        EditText etxtAssociationName = view.findViewById(R.id.association_name_et);

        chkMaaif = view.findViewById(R.id.registration_body_maaif_cb);
        chkUnada = view.findViewById(R.id.registration_body_unada_cb);
        chkNda = view.findViewById(R.id.registration_body_nda_cb);


        spinCertificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                certification_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinRegistrationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                registration_status = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinAssociationMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                association_membership = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


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
                String registration_year = etxtRegistrationYear.getText().toString().trim();
                String association_name = etxtAssociationName.getText().toString().trim();
                String certification_number = etxtCertificationNumber.getText().toString().trim();
                String registration_body = "";

                if (chkMaaif.isChecked()) {
                    registration_body += "\nMAAIF";
                }
                if (chkUnada.isChecked()) {
                    registration_body += "\nUNADA";
                }
                if (chkNda.isChecked()) {
                    registration_body += "\nNDA";
                }

                Bundle bundle = new Bundle();
                bundle.putString("business_name", business_name);
                bundle.putString("full_address", full_address);
                bundle.putString("certification", certification);
                bundle.putString("district", district);
                bundle.putString("sub_country", sub_county);
                bundle.putString("village", village);
                bundle.putString("registration_year", registration_year);
                bundle.putString("association_name", association_name);
                bundle.putString("certification_number", certification_number);
                bundle.putString("registration_body", registration_body);
                bundle.putString("association_membership", association_membership);
                bundle.putString("registration_status", registration_status);
                bundle.putString("certification_type", certification_type);

                //navigation to step 2
                navController.navigate(R.id.action_profilingAgroInputDealerStep2Fragment_to_profilingAgroInputDealerStep3Fragment,bundle);

            }
        });


    }
}