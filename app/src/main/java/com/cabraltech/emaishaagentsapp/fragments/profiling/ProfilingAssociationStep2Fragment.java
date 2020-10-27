package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAssociationStep2Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingAssociationStep2Fragment extends Fragment {

    private Context context;
    private NavController navController;
    private FragmentProfilingAssociationStep2Binding binding;

    String[] descriptionData = {"Contact\nDetails", "Governance", "Association\nDetails"};
    String respondent_position, name, year_of_registration, full_address, telephone, email,district,sub_county,village,registration_level,organisation_type;


    public ProfilingAssociationStep2Fragment() {
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
        name = getArguments().getString("name");
        year_of_registration = getArguments().getString("year_of_registration");
        email = getArguments().getString("email");
        full_address = getArguments().getString("full_address");
        telephone = getArguments().getString("telephone");
        district = getArguments().getString("district");
        sub_county = getArguments().getString("sub_county");
        village = getArguments().getString("village");
        registration_level = getArguments().getString("registration_level");
        organisation_type = getArguments().getString("organisation_type");


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_association_step2, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Onboarding Association");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.associationProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);


        final EditText etxtChairperson = view.findViewById(R.id.chairperson_et);
        final EditText etxtChairpersonContact = view.findViewById(R.id.chairperson_contact_et);
        final EditText etxtSecretary = view.findViewById(R.id.secretary_et);
        EditText etxtSecretaryContact = view.findViewById(R.id.vice_chairperson_contact_et);
        final EditText etxtRespondent = view.findViewById(R.id.respondent_et);
        EditText etxtRespondentContact = view.findViewById(R.id.respondent_contact_et);
        Spinner spinRespondentPosition = view.findViewById(R.id.respondent_position_held_spinner);


        spinRespondentPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respondent_position = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chairperson = etxtChairperson.getText().toString().trim();
                String chairperson_contact = etxtChairpersonContact.getText().toString().trim();
                String secretary = etxtSecretary.getText().toString().trim();
                String secretary_contact = etxtSecretaryContact.getText().toString().trim();
                String respondent = etxtRespondent.getText().toString().trim();
                String respondent_contact = etxtRespondentContact.getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("year_of_registration", year_of_registration);
                bundle.putString("full_address", full_address);
                bundle.putString("telephone", telephone);
                bundle.putString("email", email);
                bundle.putString("chairperson", chairperson);
                bundle.putString("chairperson_contact", chairperson_contact);
                bundle.putString("secretary", secretary);
                bundle.putString("secretary_contact", secretary_contact);
                bundle.putString("respondent", respondent);
                bundle.putString("respondent_contact", respondent_contact);
                bundle.putString("respondent_position", respondent_position);
                bundle.putString("district",district);
                bundle.putString("sub_county",sub_county);
                bundle.putString("village",village);
                bundle.putString("registration_level",registration_level);
                bundle.putString("organisation_type",organisation_type);

                //navigation to step 3
                navController.navigate(R.id.action_profilingAssociationStep2Fragment_to_profilingAssociationStep3Fragment,bundle);

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