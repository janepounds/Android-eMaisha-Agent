package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerStep2Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Calendar;


public class ProfilingAgroInputDealerStep2Fragment extends Fragment {

    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep2Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};
    String certification_type, registration_status, association_membership,district, sub_county, village, certification,business_name,full_address,owner,owner_contact;
    CheckBox chkMaaif, chkUnada, chkNda;
    private Spinner spinCertificationType,spinRegistrationStatus,spinAssociationMember;
    private EditText etxtRegistrationYear,etxtCertificationNumber,etxtAssociationName;
    private LinearLayout assMembershipLayout,certTypeLayout,regBodyLayout,regStatusLayout,associationNameLayout,regGenLayout,regYearLayout;

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
        business_name = getArguments().getString("business_name");
        full_address = getArguments().getString("full_address");
        owner = getArguments().getString("owner");
        owner_contact = getArguments().getString("owner_contact");



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

         spinCertificationType = view.findViewById(R.id.certificate_type_spinner);
         spinRegistrationStatus = view.findViewById(R.id.registration_status_spinner);
         spinAssociationMember = view.findViewById(R.id.association_membership_spinner);

         etxtRegistrationYear = view.findViewById(R.id.registration_year_et);
         etxtCertificationNumber = view.findViewById(R.id.certificate_number_et);
         etxtAssociationName = view.findViewById(R.id.association_name_et);

        chkMaaif = view.findViewById(R.id.registration_body_maaif_cb);
        chkUnada = view.findViewById(R.id.registration_body_unada_cb);
        chkNda = view.findViewById(R.id.registration_body_nda_cb);

        assMembershipLayout = view.findViewById(R.id.ass_membership_layout);
        certTypeLayout = view.findViewById(R.id.certificate_type_layout);
        regBodyLayout = view.findViewById(R.id.registration_body_layout);
        regStatusLayout = view.findViewById(R.id.registration_status_layout);
        associationNameLayout = view.findViewById(R.id.association_name_layout);
        regGenLayout = view.findViewById(R.id.registration_body_general_layout);
        regYearLayout = view.findViewById(R.id.registration_year_layout);

        etxtRegistrationYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                etxtRegistrationYear.setFilters(new InputFilter[]{new InputFilterMinMax(0, Calendar.getInstance().get(Calendar.YEAR))});

            }
        });
        etxtRegistrationYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus && Integer.parseInt(etxtRegistrationYear.getText().toString())<1900 ) {
                    // code to execute when EditText loses focus
                    etxtRegistrationYear.setError("Invalid Year");

                }
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

        spinRegistrationStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                registration_status = adapterView.getItemAtPosition(i).toString();
                if(registration_status.toLowerCase().equals("no")){
                    regGenLayout.setVisibility(View.GONE);
                    regYearLayout.setVisibility(View.GONE);
                }else{
                    regGenLayout.setVisibility(View.VISIBLE);
                    regYearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinAssociationMember.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                association_membership = adapterView.getItemAtPosition(i).toString();
                if(association_membership.toLowerCase().equals("yes")){
                    associationNameLayout.setVisibility(View.VISIBLE);
                }else{
                    associationNameLayout.setVisibility(View.GONE);
                }
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
                if (validateEntries()) {
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
                    bundle.putString("owner", owner);
                    bundle.putString("owner_contact", owner_contact);
                    bundle.putString("district", district);
                    bundle.putString("sub_county", sub_county);
                    bundle.putString("village", village);
                    bundle.putString("registration_year", registration_year);
                    bundle.putString("association_name", association_name);
                    bundle.putString("certification_number", certification_number);
                    bundle.putString("registration_body", registration_body);
                    bundle.putString("association_membership", association_membership);
                    bundle.putString("registration_status", registration_status);
                    bundle.putString("certification_type", certification_type);

                    //navigation to step 2
                    navController.navigate(R.id.action_profilingAgroInputDealerStep2Fragment_to_profilingAgroInputDealerStep3Fragment, bundle);

                }
            }
        });


    }

    public  boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        int bottom = editText.getPaddingBottom();
        int top = editText.getPaddingTop();
        int right = editText.getPaddingRight();
        int left = editText.getPaddingLeft();
        editText.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_rectangle_edit_text,null));
        editText.setPadding(left,top,right,bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            editText.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edit_text_error_border,null));
            editText.setPadding(left,top,right,bottom);
            editText.setFocusable(true);
            editText.requestFocus();
            return false;
        }


        return true;
    }
    public  boolean selectedText(Spinner spinner, LinearLayout layout) {

        int position = spinner.getSelectedItemPosition();
        int bottom = layout.getPaddingBottom();
        int top = layout.getPaddingTop();
        int right = layout.getPaddingRight();
        int left = layout.getPaddingLeft();
        layout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_rectangle_edit_text,null));
        layout.setPadding(left,top,right,bottom);

        // length 0 means there is no text
        if (position == 0) {

            layout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.spinner_error_border,null));
            layout.setPadding(left,top,right,bottom);
            layout.setFocusable(true);
            layout.requestFocus();
            return false;
        }

        return true;
    }



    public boolean validateEntries() {
        boolean check = true;
        if (!selectedText(spinRegistrationStatus,regStatusLayout)) check = false;
        if(!selectedText(spinCertificationType,certTypeLayout)) check = false;
        if (!hasText(etxtCertificationNumber)) check = false;
        if(!selectedText(spinAssociationMember,assMembershipLayout)) check = false;

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
            etxtRegistrationYear.setError("Invalid Year");
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
}