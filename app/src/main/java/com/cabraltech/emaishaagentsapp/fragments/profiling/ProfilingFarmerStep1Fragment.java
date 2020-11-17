package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;


public class ProfilingFarmerStep1Fragment extends Fragment {
    private static final String TAG = "ProfilingFarmerStep1";
    //Progressbar labels
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    private Context context;
    private NavController navController;
    private FragmentProfilingFarmerStep1Binding binding;
    String gender, marital_status, religion, education_level, language_used, nationality;
    private EditText etxtFirstName, etxtLastName, etxtAge, etxtHouseholdSize, ninExt;
    private Spinner spinGender, spinNationality, spinReligion, spinEducation, spinMarital, etxtHouseholdHead, spinSourceOfIncome;
    private TextView txtDob;
    private AutoCompleteTextView language;
    private LinearLayout genderLayout, nationalityLayout, religionLayout, educationLayout, languageLayout, maritalLayout, househeadLayout, incomeLayout,ninLayout;

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        etxtFirstName = view.findViewById(R.id.first_name_et);
        etxtLastName = view.findViewById(R.id.last_name_et);
        txtDob = view.findViewById(R.id.date_of_birth_tv);
        etxtAge = view.findViewById(R.id.age_et);
        spinGender = view.findViewById(R.id.gender_spinner);
        spinNationality = view.findViewById(R.id.nationality_spinner);
        spinReligion = view.findViewById(R.id.religion_spinner);
        spinEducation = view.findViewById(R.id.level_of_education_spinner);
        spinMarital = view.findViewById(R.id.marital_status_spinner);
        language = view.findViewById(R.id.language_used_spinner);
        etxtHouseholdSize = view.findViewById(R.id.household_size_et);
        spinSourceOfIncome = view.findViewById(R.id.source_of_income_et);
        etxtHouseholdHead = view.findViewById(R.id.household_head_et);
        genderLayout = view.findViewById(R.id.gender_layout);
        nationalityLayout = view.findViewById(R.id.nationality_layout);
        religionLayout = view.findViewById(R.id.religion_layout);
        educationLayout = view.findViewById(R.id.education_layout);
        languageLayout = view.findViewById(R.id.language_layout);
        maritalLayout = view.findViewById(R.id.marital_layout);
        househeadLayout = view.findViewById(R.id.head_layout);
        ninExt = view.findViewById(R.id.nin_et);
        incomeLayout = view.findViewById(R.id.income_layout);
        ninLayout = view.findViewById(R.id.nin_layout);


        etxtAge.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                    computedob(etxtAge.getText().toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

                    computedob(etxtAge.getText().toString());

            }
        });
        txtDob.setOnClickListener(v -> addDatePicker(txtDob, getActivity()));

        spinGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinMarital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinEducation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> languageAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.language_used_array));
        language.setThreshold(1);
        language.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                language.showDropDown();

            }
        });
        language.setAdapter(languageAdapter);

        spinNationality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinNationality.getSelectedItem().toString().toLowerCase().equals("refugee")){
                    ninLayout.setVisibility(View.GONE);
                }else{
                    ninLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinReligion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);

        binding.nextButton.setOnClickListener(v -> {
            if (validateEntries()) {
                String first_name = etxtFirstName.getText().toString().trim();
                String last_name = etxtLastName.getText().toString().trim();
                String dob = txtDob.getText().toString().toString().trim();
                String age = etxtAge.getText().toString().trim();
                String household_size = etxtHouseholdSize.getText().toString().trim();
                String household_head = etxtHouseholdHead.getSelectedItem().toString().trim();
                String source_of_income = spinSourceOfIncome.getSelectedItem().toString().trim();
                String nin = ninExt.getText().toString().trim();
                gender = spinGender.getSelectedItem().toString();
                religion = spinReligion.getSelectedItem().toString();
                nationality = spinNationality.getSelectedItem().toString();
                education_level = spinEducation.getSelectedItem().toString();
                marital_status = spinMarital.getSelectedItem().toString();
                language_used = language.getText().toString();

                Bundle bundle = new Bundle();
                bundle.putString("first_name", first_name);
                bundle.putString("last_name", last_name);
                bundle.putString("dob", dob);
                bundle.putString("age", age);
                bundle.putString("gender", gender);
                bundle.putString("religion", religion);
                bundle.putString("level_of_education", education_level);
                bundle.putString("marital_status", marital_status);
                bundle.putString("language_used", language_used);
                bundle.putString("nationality", nationality);
                bundle.putString("household_size", household_size);
                bundle.putString("household_head", household_head);
                bundle.putString("source_of_income", source_of_income);
                bundle.putString("nin", nin);

                //navigation to step 2
                navController.navigate(R.id.action_profilingFarmerFragment_to_profilingFarmerStep2Fragment, bundle);

            }
        });
    }

    public void addDatePicker(final TextView ed_, final Context context) {
        ed_.setOnClickListener(view -> {
            Calendar mcurrentDate = Calendar.getInstance();
            mcurrentDate.add(Calendar.YEAR, -15);
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog mDatePicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {

                    int month = selectedmonth + 1;
                    int year = selectedyear;
                    NumberFormat formatter = new DecimalFormat("00");
                    ed_.setText(selectedyear + "-" + formatter.format(month) + "-" + formatter.format(selectedday));


                    if (validateDob()) {
                        computeAge(ed_.getText().toString());
                    }


                }
            }, mYear, mMonth, mDay);
            mDatePicker.show();
        });
        ed_.setInputType(InputType.TYPE_NULL);
    }

    public boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        int bottom = editText.getPaddingBottom();
        int top = editText.getPaddingTop();
        int right = editText.getPaddingRight();
        int left = editText.getPaddingLeft();
        editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        editText.setPadding(left, top, right, bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            editText.setError("Please enter a value");
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
            ((TextView)spinner.getSelectedView()).setError("Please select a value ");
            layout.requestFocus();
            return false;
        }

        return true;
    }

    public boolean hasTextView(TextView textView) {
        String text = textView.getText().toString().trim();
        int bottom = textView.getPaddingBottom();
        int top = textView.getPaddingTop();
        int right = textView.getPaddingRight();
        int left = textView.getPaddingLeft();
        textView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        textView.setPadding(left, top, right, bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            textView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            textView.setPadding(left, top, right, bottom);
            textView.setFocusable(true);
            textView.requestFocus();
            return false;
        }
        return true;
    }
    public boolean autoTextView(AutoCompleteTextView textView, LinearLayout layout) {
        String text = textView.getText().toString().trim();
        int bottom = layout.getPaddingBottom();
        int top = layout.getPaddingTop();
        int right = layout.getPaddingRight();
        int left = layout.getPaddingLeft();
        layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        layout.setPadding(left, top, right, bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            layout .setPadding(left, top, right, bottom);
            textView.setFocusable(true);
            textView.requestFocus();
            textView.setError("Please enter a value");
            return false;
        }
        return true;
    }

    public boolean validateEntries() {
        boolean check = true;

        if (binding.firstNameEt.getText().toString().length() < 3) {
            check = false;

            binding.firstNameEt.setError("Name should have at least 3 characters");
        }

        if (binding.lastNameEt.getText().toString().length() < 3) {
            check = false;
            binding.lastNameEt.setError("Name should have at least 3 characters");
        }

        if (binding.nationalitySpinner.getSelectedItem().toString().equals("National") && (binding.ninEt.length() < 14)) {
            check = false;

            binding.ninEt.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.spinner_error_border, null));
            binding.ninEt.setPadding(binding.ninEt.getPaddingLeft(), binding.ninEt.getPaddingTop(), binding.ninEt.getPaddingRight(), binding.ninEt.getPaddingBottom());
            binding.ninEt.setFocusable(true);
            binding.ninEt.requestFocus();
            binding.ninEt.setError(getString(R.string.nin_message));
        }

        if (!hasText(etxtFirstName)) check = false;
        if (!hasText(etxtLastName)) check = false;
        if (!hasTextView(txtDob)) check = false;
        if (!hasText(etxtAge)) check = false;
        if (!hasText(etxtHouseholdSize)) check = false;
        if (!selectedText(spinSourceOfIncome, incomeLayout)) check = false;
        if (!autoTextView(language, languageLayout)) check = false;
        if (!selectedText(spinGender, genderLayout)) check = false;
        if (!selectedText(spinNationality, nationalityLayout)) check = false;
        if (!selectedText(spinReligion, religionLayout)) check = false;
        if (!selectedText(spinEducation, educationLayout)) check = false;
        if (!selectedText(spinMarital, maritalLayout)) check = false;
        if (!selectedText(etxtHouseholdHead, househeadLayout)) check = false;

        return check;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String computeAge(String dob) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date convertedDate = new Date();
        try {

            convertedDate = dateFormat.parse(dob);
            Calendar today = Calendar.getInstance();       // get calendar instance
            today.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            today.set(Calendar.MINUTE, 0);                 // set minute in hour
            today.set(Calendar.SECOND, 0);                 // set second in minute
            today.set(Calendar.MILLISECOND, 0);

            long daysBetween = daysBetween(convertedDate, today.getTime());
            int years = (int) (daysBetween / 365);
            int months = (int) ((daysBetween - years * 365) / 30);
            int days = (int) (daysBetween % 30);
            Log.d("DATES", dob + " " + convertedDate.toString() + " - " + today.getTime().toString() + " days = " + daysBetween);
            String age = "";
            if (years > 0) {
                age += years;
                etxtAge.setText(age);

            }


            return age;


        } catch (ParseException e) {
            Log.d("DATe", dob);
            e.printStackTrace();
            String age = "--";
            return age;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String computedob(String age){
        String dateOfBirth = "";
        try {

            int formattedage = Integer.parseInt(age);
            LocalDate now = LocalDate.now();
            LocalDate dob = now.minusYears(formattedage);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dateOfBirth = dob.format(formatter);
            txtDob.setText(dateOfBirth);


        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return dateOfBirth;
    }

    public static long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1);
            //Log.d("Day "+daysBetween,sDate.getTime().toString());
            daysBetween++;
        }
        return daysBetween;
    }

    public static Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second

        return cal;                                  // return the date part
    }

    public boolean validateDob() {
        if (txtDob.getText().toString().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }


}

