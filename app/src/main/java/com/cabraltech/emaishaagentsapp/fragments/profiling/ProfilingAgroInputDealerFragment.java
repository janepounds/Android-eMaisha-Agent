package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerBinding;


public class ProfilingAgroInputDealerFragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerBinding binding;
    String district, sub_county, village, certification_type, certification_status;
    private RadioGroup radioGroup;
    private RadioButton certification_radioButton;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingAgroInputDealerFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_agro_input_dealer, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Enrolling Aqro Input Retailer");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText etxtBusinessName = view.findViewById(R.id.business_name_et);
        final EditText etxtProprietorName = view.findViewById(R.id.proprietor_name_et);
        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        Spinner spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtFull_address = view.findViewById(R.id.full_address_et);
        final EditText etxtProprietorContact = view.findViewById(R.id.proprietor_contact_et);
        Spinner spinCertificationType = view.findViewById(R.id.certificate_type_spinner);
        final EditText etxtCertificationNumber = view.findViewById(R.id.certificate_number_et);
        radioGroup = view.findViewById(R.id.certification_radioGroup);
        certification_radioButton = view.findViewById(R.id.certification_yes_radio_button);

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

        spinCertificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                certification_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Button btnSubmit = view.findViewById(R.id.submit_button);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String business_name = etxtBusinessName.getText().toString().trim();
                String proprietor_name = etxtProprietorName.getText().toString().trim();
                String full_address = etxtFull_address.getText().toString().trim();
                String proprietor_contact = etxtProprietorContact.getText().toString().trim();
                String certification_number = etxtCertificationNumber.getText().toString().trim();

                View radioButton = radioGroup.findViewById(selectedId);
                int radioId = radioGroup.indexOfChild(radioButton);
                certification_radioButton = (RadioButton) radioGroup.getChildAt(radioId);;
                certification_status = (String) certification_radioButton.getText();


                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                databaseAccess.open();

                boolean check = databaseAccess.addDealer(business_name,proprietor_name,district,sub_county,village,full_address,proprietor_contact,certification_status,certification_type,certification_number);
                if (check) {
                    Toast.makeText(getActivity(), "Agro Input Dealer Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }
}