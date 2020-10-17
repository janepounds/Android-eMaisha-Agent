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
    String district, sub_county, village, crop_value_chain, livestock_value_chain, source_of_funding;

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
<<<<<<< HEAD
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_association, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Onboarding Association");
=======
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_association,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Onboarding Association");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.associationProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
>>>>>>> 7825cece90212e46d2eaabaad99d0848e3b0e215
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
<<<<<<< HEAD
        super.onViewCreated(view, savedInstanceState);


        final EditText etxtName = view.findViewById(R.id.association_name_et);
        final EditText etxtYear_of_registration = view.findViewById(R.id.year_of_registration_et);
        Spinner spinDistrict = view.findViewById(R.id.district_spinner);
        Spinner spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        Spinner spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtFull_address = view.findViewById(R.id.full_address_et);
        final EditText etxtTelephone = view.findViewById(R.id.association_telephone_et);
        final EditText etxtEmail = view.findViewById(R.id.association_email_et);
        final EditText etxtNumberOfMembers = view.findViewById(R.id.number_of_members_et);
        Spinner spinCropValueChain = view.findViewById(R.id.crop_value_chains_spinner);
        Spinner spinLivestockValueChain = view.findViewById(R.id.livestock_value_chain_spinner);
        Spinner spinSourceOfFunding = view.findViewById(R.id.main_source_of_funding_spinner);
        final EditText etxtChairperson = view.findViewById(R.id.chairperson_et);
        final EditText etxtChairpersonContact = view.findViewById(R.id.chairperson_contact_et);
        final EditText etxtViceChairperson = view.findViewById(R.id.vice_chairperson_et);
        EditText etxtViceChairpersonContact = view.findViewById(R.id.vice_chairperson_contact_et);

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

        spinCropValueChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crop_value_chain = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinLivestockValueChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                livestock_value_chain = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinSourceOfFunding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                source_of_funding = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxtName.getText().toString().trim();
                String year_of_registration = etxtYear_of_registration.toString().trim();
                String full_address = etxtFull_address.getText().toString().trim();
                String telephone = etxtTelephone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                String number_of_members = etxtNumberOfMembers.getText().toString().trim();
                String chairperson = etxtChairperson.getText().toString().trim();
                String chairperson_contact = etxtChairpersonContact.getText().toString().trim();
                String vice_chairperson = etxtViceChairperson.getText().toString().trim();
                String vice_chairperson_contact = etxtChairpersonContact.getText().toString().trim();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                databaseAccess.open();

                boolean check = databaseAccess.addAssociation(name,year_of_registration,district,sub_county,village,full_address,telephone,email,number_of_members,crop_value_chain,livestock_value_chain,source_of_funding,chairperson,chairperson_contact,vice_chairperson,vice_chairperson_contact);
                if (check) {
                    Toast.makeText(getActivity(), "Association Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                }


=======
        navController = Navigation.findNavController(view);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navigation to step 2
                navController.navigate(R.id.action_profilingAssociationFragment_to_profilingAssociationStep2Fragment);
>>>>>>> 7825cece90212e46d2eaabaad99d0848e3b0e215

            }
        });


    }
}