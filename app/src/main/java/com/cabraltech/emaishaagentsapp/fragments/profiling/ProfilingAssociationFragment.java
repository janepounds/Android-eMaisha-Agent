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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAssociationBinding;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class ProfilingAssociationFragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentProfilingAssociationBinding binding;
    String district, sub_county, village, organisation_type,registration_level;

    String[] descriptionData = {"Contact\nDetails", "Governance", "Association\nDetails"};


    public ProfilingAssociationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_association,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Onboarding Association");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.associationProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText etxtName = view.findViewById(R.id.association_name_et);
        final EditText etxtYear_of_registration = view.findViewById(R.id.year_of_registration_et);
        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        Spinner spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtFull_address = view.findViewById(R.id.full_address_et);
        final EditText etxtTelephone = view.findViewById(R.id.association_telephone_et);
        final EditText etxtEmail = view.findViewById(R.id.association_email_et);
        Spinner spinOrganisationType = view.findViewById(R.id.organisation_type_spinner);
        Spinner spinRegistrationLevel = view.findViewById(R.id.registration_level_spinner);


        Button next_button = view.findViewById(R.id.next_button);

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

        spinOrganisationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                organisation_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinRegistrationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                registration_level = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxtName.getText().toString().trim();
                String year_of_registration = etxtYear_of_registration.toString().trim();
                String full_address = etxtFull_address.getText().toString().trim();
                String telephone = etxtTelephone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("year_of_registration",year_of_registration);
                bundle.putString("full_address",full_address);
                bundle.putString("telephone",telephone);
                bundle.putString("email",email);
                bundle.putString("district",district);
                bundle.putString("sub_county",sub_county);
                bundle.putString("village",village);
                bundle.putString("registration_level",registration_level);
                bundle.putString("organisation_type",organisation_type);
                navController.navigate(R.id.action_profilingAssociationFragment_to_profilingAssociationStep2Fragment,bundle);

            }
        });






    }
}