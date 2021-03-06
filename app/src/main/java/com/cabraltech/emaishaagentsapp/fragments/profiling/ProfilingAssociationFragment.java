package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAssociationBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ProfilingAssociationFragment extends Fragment {
    private static final String TAG = "ProfilingAssociationFra";
    private Context context;
    private NavController navController;
    private FragmentProfilingAssociationBinding binding;
    String organisation_type, registration_level;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();

    private EditText etxtName, etxtYear_of_registration, etxtFull_address, etxtTelephone, etxtEmail;
    private AutoCompleteTextView spinDistrict, spinSubCounty, spinVillage;
    private Spinner spinOrganisationType, spinRegistrationLevel;
    private LinearLayout organTypeLayout, regLevelLayout, districtLayout, subcountyLayout, villageLayout;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_association, container, false);
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
        super.onViewCreated(view, savedInstanceState);


        etxtName = view.findViewById(R.id.association_name_et);
        etxtYear_of_registration = view.findViewById(R.id.year_of_registration_et);

        spinDistrict = view.findViewById(R.id.district_spinner);
        spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        spinVillage = view.findViewById(R.id.village_spinner);
        etxtFull_address = view.findViewById(R.id.full_address_et);
        etxtTelephone = view.findViewById(R.id.association_telephone_et);
        etxtEmail = view.findViewById(R.id.association_email_et);
        spinOrganisationType = view.findViewById(R.id.organisation_type_spinner);
        spinRegistrationLevel = view.findViewById(R.id.registration_level_spinner);
        regLevelLayout = view.findViewById(R.id.reg_level_layout);
        organTypeLayout = view.findViewById(R.id.organ_type_layout);
        districtLayout = view.findViewById(R.id.district_layout);
        subcountyLayout = view.findViewById(R.id.subcounty_layout);
        villageLayout = view.findViewById(R.id.village_layout);


        etxtYear_of_registration.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etxtYear_of_registration.setFilters(new InputFilter[]{new InputFilterMinMax(0, Calendar.getInstance().get(Calendar.YEAR))});

            }
        });

        Button next_button = view.findViewById(R.id.next_button);

        // load district, sub county and village data
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();
        ArrayList<SpinnerItem> districtList = new ArrayList<>();

        try {
            for (RegionDetails x : databaseAccess.getRegionDetails("district")) {
                districtList.add(new SpinnerItem() {
                    @Override
                    public String getId() {
                        return String.valueOf(x.getId());
                    }

                    @NonNull
                    @Override
                    public String toString() {
                        return x.getRegion();
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreate: " + districtList + districtList.size());
        ArrayAdapter<SpinnerItem> districtListAdapter = new ArrayAdapter<SpinnerItem>(context, android.R.layout.simple_dropdown_item_1line, districtList);
        spinDistrict.setThreshold(1);
        spinDistrict.setAdapter(districtListAdapter);

        spinDistrict.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spinDistrict.showDropDown();
                for (int i = 0; i < districtList.size(); i++) {

                    if (districtList.get(i).toString().equals(spinDistrict.getText().toString())) {
                        pickedDistrictId = Integer.parseInt(districtList.get(i).getId());

                        Log.d(TAG, "onCreate: " + pickedDistrictId);

                        subcountyList.clear();
                        try {
                            for (RegionDetails x : databaseAccess.getSubcountyDetails(String.valueOf(pickedDistrictId), "subcounty")) {
                                subcountyList.add(new SpinnerItem() {
                                    @Override
                                    public String getId() {
                                        return String.valueOf(x.getId());
                                    }


                                    @NonNull
                                    @Override
                                    public String toString() {
                                        return x.getRegion();
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onCreate: " + subcountyList);
                        ArrayAdapter<SpinnerItem> subcountryListAdapter = new ArrayAdapter<SpinnerItem>(context, android.R.layout.simple_dropdown_item_1line, subcountyList);
                        spinSubCounty.setThreshold(1);
                        spinSubCounty.setAdapter(subcountryListAdapter);
                    }


                }
            }
        });

        spinSubCounty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spinSubCounty.showDropDown();

                for (int i = 0; i < subcountyList.size(); i++) {

                    if (subcountyList.get(i).toString().equals(spinSubCounty.getText().toString())) {
                        pickedSubcountyId = Integer.parseInt(subcountyList.get(i).getId());

                        Log.d(TAG, "onCreate: " + pickedSubcountyId);

                        villageList.clear();
                        try {
                            for (RegionDetails x : databaseAccess.getVillageDetails(String.valueOf(pickedSubcountyId), "village")) {
                                villageList.add(x.getRegion());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onCreate: " + villageList);
                        ArrayAdapter<String> villageListAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, villageList);
                        spinVillage.setThreshold(1);
                        spinVillage.setAdapter(villageListAdapter);
                    }


                }
            }
        });

        spinVillage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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

        next_button.setOnClickListener(view1 -> {
            if (validateEntries()) {
                String name = etxtName.getText().toString().trim();

                String year_of_registration = etxtYear_of_registration.toString().trim();
                String full_address = etxtFull_address.getText().toString().trim();
                String telephone = etxtTelephone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("name", name);
                bundle.putString("year_of_registration", year_of_registration);
                bundle.putString("full_address", full_address);
                bundle.putString("telephone", telephone);
                bundle.putString("email", email);
                bundle.putString("district", spinDistrict.getText().toString());
                bundle.putString("sub_county", spinSubCounty.getText().toString());
                bundle.putString("village", spinVillage.getText().toString());
                bundle.putString("registration_level", registration_level);
                bundle.putString("organisation_type", organisation_type);
                navController.navigate(R.id.action_profilingAssociationFragment_to_profilingAssociationStep2Fragment, bundle);
            }
        });
    }

    public void addDatePicker(final TextView ed_, final Context context) {
        ed_.setOnClickListener(view -> {
            Calendar mCurrentDate = Calendar.getInstance();
            int mYear = mCurrentDate.get(Calendar.YEAR);
            int mMonth = mCurrentDate.get(Calendar.MONTH);
            int mDay = mCurrentDate.get(Calendar.DAY_OF_MONTH);

            final DatePickerDialog mDatePicker = new DatePickerDialog(context, (datePicker, selectedYear, selectedMonth, selectedDay) -> {

                int month = selectedMonth + 1;
                int year = selectedYear;
                NumberFormat formatter = new DecimalFormat("00");
                ed_.setText(String.valueOf(selectedYear));

            }, mYear, mMonth, mDay);
            mDatePicker.show();

        });
        ed_.setInputType(InputType.TYPE_NULL);
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
            editText.setError("Please enter a value ");
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        } else if (text.length() < minCharacters) {
            editText.setError("" + name + " must have at least " + minCharacters + " characters");
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        }

        return true;
    }

    public boolean hasAutoText(AutoCompleteTextView autoCompleteTextView, LinearLayout linearLayout) {

        String text = autoCompleteTextView.getText().toString().trim();
        int bottom = linearLayout.getPaddingBottom();
        int top = linearLayout.getPaddingTop();
        int right = linearLayout.getPaddingRight();
        int left = linearLayout.getPaddingLeft();
        linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        linearLayout.setPadding(left, top, right, bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            linearLayout.setPadding(left, top, right, bottom);
            linearLayout.setFocusable(true);
            linearLayout.requestFocus();
            autoCompleteTextView.setError("Please enter a value ");
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
            ((TextView) spinner.getSelectedView()).setError("Please select a value ");
            return false;
        }

        return true;
    }

    public boolean hasValidEmail(EditText editText, String message) {
        String text = editText.getText().toString().trim();
        int bottom = editText.getPaddingBottom();
        int top = editText.getPaddingTop();
        int right = editText.getPaddingRight();
        int left = editText.getPaddingLeft();
        editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        editText.setPadding(left, top, right, bottom);

        if (!text.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            editText.setError(message);
            editText.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
            editText.setPadding(left, top, right, bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    public boolean validateEntries() {
        boolean check = true;

        if (!hasText("Name", etxtName, 3)) check = false;
        if (!hasText("Year", etxtYear_of_registration, 4)) check = false;
        if (!hasText("Address", etxtFull_address, 3)) check = false;
        if (!hasText("Phone", etxtTelephone, 9)) check = false;
        if (!hasText("Email", etxtEmail, 11)) check = false;
        if (!hasValidEmail(etxtEmail, "Invalid Email")) check = false;

        if (!hasAutoText(spinDistrict, districtLayout)) check = false;
        if (!hasAutoText(spinSubCounty, subcountyLayout)) check = false;
        if (!hasAutoText(spinVillage, villageLayout)) check = false;
        if (!selectedText(spinOrganisationType, organTypeLayout)) check = false;
        if (!selectedText(spinRegistrationLevel, regLevelLayout)) check = false;

        return check;
    }

    class InputFilterMinMax implements InputFilter {

        private int min, max;

        public InputFilterMinMax(int min, int max) {
            this.min = min;
            this.max = max;
        }

        public InputFilterMinMax(String min, String max) {
            this.min = Integer.parseInt(min);
            this.max = Integer.parseInt(max);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(min, max, input))
                    return null;
            } catch (NumberFormatException nfe) {
                Log.e("FilterError", nfe.getMessage());
            }
            etxtYear_of_registration.setError("Invalid Year");
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}