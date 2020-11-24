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

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;


public class ProfilingAgroInputDealerStep2Fragment extends Fragment {

    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep2Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};
    String certification_type, registration_status, association_membership;
    CheckBox chkMaaif, chkUnada, chkNda;
    private Spinner spinCertificationType, spinRegistrationStatus, spinAssociationMember;
    private EditText etxtRegistrationYear, etxtCertificationNumber, etxtAssociationName;
    private LinearLayout assMembershipLayout, certTypeLayout, regBodyLayout, regStatusLayout, associationNameLayout, regGenLayout, regYearLayout;

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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                if (registration_status.toLowerCase().equals("yes")) {
                    regGenLayout.setVisibility(View.VISIBLE);
                    regYearLayout.setVisibility(View.VISIBLE);
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

                } else {
                    regGenLayout.setVisibility(View.GONE);
                    regYearLayout.setVisibility(View.GONE);
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
                if (association_membership.toLowerCase().equals("yes")) {
                    associationNameLayout.setVisibility(View.VISIBLE);
                } else {
                    associationNameLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);
        binding.previousButton.setOnClickListener(v -> {
            //navigation to step 2
            navController.popBackStack();
        });

        binding.nextButton.setOnClickListener(v -> {
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
                bundle.putString("business_name", getArguments().getString("business_name"));
                bundle.putString("district", getArguments().getString("district"));
                bundle.putString("sub_county", getArguments().getString("sub_county"));
                bundle.putString("village", getArguments().getString("village"));
                bundle.putString("full_address", getArguments().getString("full_address"));
                bundle.putString("owner", getArguments().getString("owner"));
                bundle.putString("owner_contact", getArguments().getString("owner_contact"));

                bundle.putString("registration_status", binding.registrationStatusSpinner.getSelectedItem().toString());

                if (binding.registrationYearEt.getText().toString().trim().isEmpty() || (binding.registrationYearEt.getText().toString() == null)) {
                    bundle.putString("registration_year", "None");
                } else {
                    bundle.putString("registration_year", binding.registrationYearEt.getText().toString().trim());
                }

                if (registration_body.trim().isEmpty() || (registration_body == null)) {
                    bundle.putString("registration_body", "None");
                } else {
                    bundle.putString("registration_body", registration_body);
                }

                bundle.putString("certification_type", binding.certificateTypeSpinner.getSelectedItem().toString());
                bundle.putString("certification_number", binding.certificateNumberEt.getText().toString().trim());
                bundle.putString("association_membership", binding.associationMembershipSpinner.getSelectedItem().toString());

                if (binding.associationNameEt.getText().toString().trim().isEmpty() || (binding.associationNameEt.getText().toString() == null)) {
                    bundle.putString("association_name", "None");
                } else {
                    bundle.putString("association_name", binding.associationNameEt.getText().toString());
                }

                //navigation to step 2
                navController.navigate(R.id.action_profilingAgroInputDealerStep2Fragment_to_profilingAgroInputDealerStep3Fragment, bundle);
            }
        });
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
            layout.requestFocus();
            ((TextView) spinner.getSelectedView()).setError("Please select a value ");
            return false;
        }

        return true;
    }

    public boolean validateEntries() {
        boolean check = true;
        if (!selectedText(spinRegistrationStatus, regStatusLayout)) check = false;
        if (!selectedText(spinCertificationType, certTypeLayout)) check = false;
        if (!hasText(etxtCertificationNumber)) check = false;
        if (!selectedText(spinAssociationMember, assMembershipLayout)) check = false;
        if (regYearLayout.getVisibility() == View.VISIBLE && !hasText(etxtRegistrationYear))
            check = false;
        if (associationNameLayout.getVisibility() == View.VISIBLE && !hasText(etxtAssociationName))
            check = false;

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