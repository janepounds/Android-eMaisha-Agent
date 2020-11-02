package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep1Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;


public class ProfilingFarmerStep1Fragment extends Fragment {

    //Progressbar labels
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    private Context context;
    private NavController navController;
    private FragmentProfilingFarmerStep1Binding binding;
    String gender, marital_status, religion, education_level, language_used, nationality;


    // TODO: Rename parameter arguments, choose names that match


    public ProfilingFarmerStep1Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_farmer_step1, container, false);
        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.farmerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Farmer Profiling");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final EditText etxtFirstName = view.findViewById(R.id.first_name_et);
        final EditText etxtLastName = view.findViewById(R.id.last_name_et);
        final TextView txtDob = view.findViewById(R.id.date_of_birth_tv);
        final EditText etxtAge = view.findViewById(R.id.age_et);
        Spinner spinGender = view.findViewById(R.id.gender_spinner);
        Spinner spinNationality = view.findViewById(R.id.nationality_spinner);
        Spinner spinReligion = view.findViewById(R.id.religion_spinner);
        Spinner spinEducation = view.findViewById(R.id.level_of_education_spinner);
        Spinner spinMarital = view.findViewById(R.id.marital_status_spinner);
        Spinner spinLanguage = view.findViewById(R.id.language_used_spinner);
        final EditText etxtHouseholdSize = view.findViewById(R.id.household_size_et);
        final EditText etxtSourceOfIncome = view.findViewById(R.id.source_of_income_et);
        final EditText etxtHouseholdHead = view.findViewById(R.id.household_head_et);


        txtDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDatePicker(txtDob, getActivity());
            }
        });


        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = adapterView.getItemAtPosition(i).toString();
                Log.d("Gender", gender);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                marital_status = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                education_level = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                language_used = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nationality = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                religion = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        navController = Navigation.findNavController(view);

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first_name = etxtFirstName.getText().toString().trim();
                String last_name = etxtLastName.getText().toString().trim();
                String dob = txtDob.getText().toString().toString().trim();
                String age = etxtAge.getText().toString().trim();
                String household_size = etxtHouseholdSize.getText().toString().trim();
                String household_head = etxtHouseholdHead.getText().toString().trim();
                String source_of_income = etxtSourceOfIncome.getText().toString().trim();

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

                //navigation to step 2
                navController.navigate(R.id.action_profilingFarmerFragment_to_profilingFarmerStep2Fragment, bundle);

            }
        });


    }

    public static void addDatePicker(final TextView ed_, final Context context) {
        ed_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        int month = selectedmonth + 1;
                        NumberFormat formatter = new DecimalFormat("00");
                        ed_.setText(selectedyear + "-" + formatter.format(month) + "-" + formatter.format(selectedday));
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.show();

            }
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }

}