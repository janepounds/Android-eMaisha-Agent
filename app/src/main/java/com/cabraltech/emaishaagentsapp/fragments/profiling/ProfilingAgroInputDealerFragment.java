package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;

import java.util.ArrayList;

public class ProfilingAgroInputDealerFragment extends Fragment {
    private static final String TAG = "ProfilingAgroInputDeale";
    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerBinding binding;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();

    private EditText etxtBusinessName, etxtFull_address, etxtOwner, etxtOwnerContact;
    private AutoCompleteTextView spinDistrict, spinSubCounty, spinVillage;
    private LinearLayout districtLayout, subCountyLayout, villageLayout, ownerContactLayout;

    String certification_type, certification_status;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};

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

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.agroInputDealerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etxtBusinessName = view.findViewById(R.id.business_name_et);
        spinDistrict = view.findViewById(R.id.district_spinner);
        spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        spinVillage = view.findViewById(R.id.village_spinner);
        etxtFull_address = view.findViewById(R.id.full_address_et);
        etxtOwner = view.findViewById(R.id.owner_et);
        etxtOwnerContact = view.findViewById(R.id.owner_contact_et);
        districtLayout = view.findViewById(R.id.district_layout);
        subCountyLayout = view.findViewById(R.id.subcounty_layout);
        villageLayout = view.findViewById(R.id.village_layout);
        ownerContactLayout = view.findViewById(R.id.owner_contact_layout);


        // final EditText etxtProprietorContact = view.findViewById(R.id.proprietor_contact_et);
        Spinner spinCertificationType = view.findViewById(R.id.certificate_type_spinner);
        final EditText etxtCertificationNumber = view.findViewById(R.id.certificate_number_et);

        //load district, subcounty and village data
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
        navController = Navigation.findNavController(view);

        Button nextButton = view.findViewById(R.id.next_button);
        nextButton.setOnClickListener(view1 -> {
            if (validateEntries()) {
                Bundle bundle = new Bundle();
                bundle.putString("business_name", binding.businessNameEt.getText().toString());
                bundle.putString("district", binding.districtSpinner.getText().toString());
                bundle.putString("sub_county", binding.subCountySpinner.getText().toString());
                bundle.putString("village", binding.villageSpinner.getText().toString());
                bundle.putString("full_address", binding.fullAddressEt.getText().toString());
                bundle.putString("owner", binding.ownerEt.getText().toString());
                bundle.putString("owner_contact", binding.ownerContactEt.getText().toString());

                navController.navigate(R.id.action_profilingAgroInputDealersFragment_to_profilingAgroInputDealerStep2Fragment, bundle);
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

    public boolean autoText(AutoCompleteTextView autoCompleteTextView, LinearLayout linearLayout) {

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
            autoCompleteTextView.setError("Please enter a value");
            return false;
        }


        return true;
    }


    public boolean validateEntries() {

        boolean check = true;
        if (etxtBusinessName.getText().toString().length() < 3) {
            check = false;

            etxtBusinessName.setError("Name must have at least 3 characters");
        }
        if (etxtOwner.getText().toString().length() < 3) {
            check = false;

            etxtOwner.setError("Owner name must have at least 3 characters");
        }
        if (etxtOwnerContact.getText().toString().length() < 9) {
            check = false;

            etxtOwnerContact.setError("Contact must have at least 9 digits");
        }

        if (spinDistrict.getText().toString().length() < 3) {
            spinDistrict.setError("District must have at least 3 characters");
            check = false;
        }
        if (spinSubCounty.getText().toString().length() < 3) {
            spinSubCounty.setError("Sub-County must have at least 3 characters");
            check = false;
        }

        if (spinVillage.getText().toString().length() < 3) {
            spinVillage.setError("village must have at least 3 characters");
            check = false;
        }
        if (!hasText(etxtBusinessName)) check = false;
        if (!hasText(etxtOwner)) check = false;
        if (!hasText(etxtOwnerContact)) check = false;
        if (!hasText(etxtFull_address)) check = false;
        if (!autoText(spinDistrict, districtLayout)) check = false;
        if (!autoText(spinSubCounty, subCountyLayout)) check = false;
        if (!autoText(spinVillage, villageLayout)) check = false;
        // Toast.makeText(context, getString(R.string.missing_fields_message), Toast.LENGTH_LONG).show();
        return check;


    }
}