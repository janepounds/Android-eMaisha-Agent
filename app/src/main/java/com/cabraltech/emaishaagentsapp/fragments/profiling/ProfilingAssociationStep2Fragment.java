package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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

    private EditText etxtChairperson, etxtChairpersonContact, etxtSecretary, etxtSecretaryContact, etxtRespondent, etxtRespondentContact;
    private Spinner spinRespondentPosition;
    private LinearLayout respondentPositionLayout;

    String[] descriptionData = {"Contact\nDetails", "Governance", "Association\nDetails"};
    String respondent_position, name, year_of_registration, full_address, telephone, email, district, sub_county, village, registration_level, organisation_type;


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
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_association_step2, container, false);

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


        etxtChairperson = view.findViewById(R.id.chairperson_et);
        etxtChairpersonContact = view.findViewById(R.id.chairperson_contact_et);
        etxtSecretary = view.findViewById(R.id.secretary_et);
        etxtSecretaryContact = view.findViewById(R.id.vice_chairperson_contact_et);
        etxtRespondent = view.findViewById(R.id.respondent_et);
        etxtRespondentContact = view.findViewById(R.id.respondent_contact_et);
        spinRespondentPosition = view.findViewById(R.id.respondent_position_held_spinner);
        respondentPositionLayout = view.findViewById(R.id.resp_position_layout);


        spinRespondentPosition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                respondent_position = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.nextButton.setOnClickListener(v -> {
            if (validateEntries()) {
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
                bundle.putString("chairperson_contact", "0" + chairperson_contact);
                bundle.putString("secretary", secretary);
                bundle.putString("secretary_contact", "0" + secretary_contact);
                bundle.putString("respondent", respondent);
                bundle.putString("respondent_contact", "0" + respondent_contact);
                bundle.putString("respondent_position", respondent_position);
                bundle.putString("district", district);
                bundle.putString("sub_county", sub_county);
                bundle.putString("village", village);
                bundle.putString("registration_level", registration_level);
                bundle.putString("organisation_type", organisation_type);

                //navigation to step 3
                navController.navigate(R.id.action_profilingAssociationStep2Fragment_to_profilingAssociationStep3Fragment, bundle);
            }
        });

        binding.previousButton.setOnClickListener(v -> {
            //popback stack to step 1
            navController.popBackStack();
        });
    }

    public boolean hasText(String name, EditText editText, int minCharacters) {

        String text = editText.getText().toString().trim();
        int bottom = editText.getPaddingBottom();
        int top = editText.getPaddingTop();
        int right = editText.getPaddingRight();
        int left = editText.getPaddingLeft();
        editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        editText.setPadding(left, top, right, bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {
            editText.setError("Required");
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        } else if (text.length() < minCharacters) {
            editText.setError("" + name + " Must have at least " + minCharacters + " characters");
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public boolean selectedText(Spinner spinner, LinearLayout layout) {

        int position = spinner.getSelectedItemPosition();
        int bottom = layout.getPaddingBottom();
        int top = layout.getPaddingTop();
        int right = layout.getPaddingRight();
        int left = layout.getPaddingLeft();
        layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        layout.setPadding(left, top, right, bottom);

        // length 0 means there is no text
        if (position == 0) {

            layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_error_border, null));
            layout.setPadding(left, top, right, bottom);
            layout.setFocusable(true);
            layout.requestFocus();
            return false;
        }

        return true;
    }

    public boolean validateEntries() {
        boolean check = true;
        if (!hasText("Name", etxtChairperson, 3)) check = false;
        if (!hasText("Phone", etxtChairpersonContact, 9)) check = false;
        if (!hasText("Name", etxtSecretary, 3)) check = false;
        if (!hasText("Phone", etxtSecretaryContact, 9)) check = false;
        if (!hasText("Name", etxtRespondent, 3)) check = false;
        if (!hasText("Phone", etxtRespondentContact, 9)) check = false;
        if (!selectedText(spinRespondentPosition, respondentPositionLayout)) check = false;

        return check;
    }

    public void focusToValidateNames(EditText value) {
        value.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                if (value.getText().toString().trim().length() < 7)
                    value.setError("Failed");
                else
                    value.setError(null);
            }
        });
    }
}