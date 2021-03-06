package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
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
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingBulkBuyersBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;

import java.util.ArrayList;


public class ProfilingBulkBuyersFragment extends Fragment {
    private static final String TAG = "ProfilingBulkBuyersFrag";
    private Context context;
    private NavController navController;
    private FragmentProfilingBulkBuyersBinding binding;
    String  business_type;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();

    private EditText etxtBusinessName,etxtOwner,etxtPhone,etxtEmail,etxtFullAddress;
    private Spinner spinBusinessType;
    private AutoCompleteTextView spinDistrict,spinSubcounty,spinVillage;
    private LinearLayout businessLayout,districtLayout,subcountyLayout,villageLayout;

    String[] descriptionData = {"Contact\nDetails", "Business\nDetails"};

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingBulkBuyersFragment() {
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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_bulk_buyers,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Enroll Agro Trader");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.bulkBuyersProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinBusinessType = view.findViewById(R.id.business_type_spinner);
        etxtBusinessName = view.findViewById(R.id.business_name_et);
        etxtOwner = view.findViewById(R.id.proprietor_name_et);
        etxtPhone = view.findViewById(R.id.phone_number_et);
        etxtEmail = view.findViewById(R.id.email_address_et);
        spinDistrict = view.findViewById(R.id.district_spinner);
        spinSubcounty = view.findViewById(R.id.sub_county_spinner);
        spinVillage = view.findViewById(R.id.village_spinner);
        etxtFullAddress = view.findViewById(R.id.full_address_et);
        Button btnSubmit = view.findViewById(R.id.next_button);
        businessLayout = view.findViewById(R.id.business_type_layout);
        districtLayout = view.findViewById(R.id.district_layout);
        subcountyLayout = view.findViewById(R.id.subcounty_layout);
        villageLayout = view.findViewById(R.id.village_layout);

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
        Log.d(TAG, "onCreate: "+ districtList + districtList.size());
        ArrayAdapter<SpinnerItem> districtListAdapter = new ArrayAdapter<SpinnerItem>(context,  android.R.layout.simple_dropdown_item_1line, districtList);
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
                        pickedDistrictId =Integer.parseInt(districtList.get(i).getId());

                        Log.d(TAG, "onCreate: "+ pickedDistrictId);

                        subcountyList.clear();
                        try {
                            for (RegionDetails x : databaseAccess.getSubcountyDetails(String.valueOf(pickedDistrictId),"subcounty")) {
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
                        Log.d(TAG, "onCreate: "+ subcountyList);
                        ArrayAdapter<SpinnerItem> subcountryListAdapter = new ArrayAdapter<SpinnerItem>(context,  android.R.layout.simple_dropdown_item_1line, subcountyList);
                        spinSubcounty.setThreshold(1);
                        spinSubcounty.setAdapter(subcountryListAdapter);
                    }
                }
            }
        });

        spinSubcounty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                spinSubcounty.showDropDown();

                for (int i = 0; i < subcountyList.size(); i++) {

                    if (subcountyList.get(i).toString().equals(spinSubcounty.getText().toString())) {
                        pickedSubcountyId =Integer.parseInt(subcountyList.get(i).getId());

                        Log.d(TAG, "onCreate: "+ pickedSubcountyId);

                        villageList.clear();
                        try {
                            for (RegionDetails x : databaseAccess.getVillageDetails(String.valueOf(pickedSubcountyId),"village")) {
                                villageList.add(x.getRegion());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, "onCreate: "+ villageList);
                        ArrayAdapter<String> villageListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, villageList);
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

        spinBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                business_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);


        btnSubmit.setOnClickListener(view1 -> {
            if (validateEntries()) {
                String business_name = etxtBusinessName.getText().toString().trim();
                String phone = etxtPhone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                String full_address = etxtFullAddress.getText().toString().trim();
                String owner = etxtOwner.getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putString("business_name", business_name);
                bundle.putString("phone", phone);
                bundle.putString("owner", owner);
                bundle.putString("full_address", full_address);
                bundle.putString("email", email);
                bundle.putString("district", spinDistrict.getText().toString());
                bundle.putString("sub_county", spinSubcounty.getText().toString());
                bundle.putString("village", spinVillage.getText().toString());
                bundle.putString("business_type", business_type);


                navController.navigate(R.id.action_profilingBulkBuyersFragment_to_profilingBulkBuyersStep2Fragment, bundle);

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
            editText.setError("Please enter a value");
            return false;
        }


        return true;
    }
    public  boolean autoText(AutoCompleteTextView autoCompleteTextView, LinearLayout linearLayout) {

        String text = autoCompleteTextView.getText().toString().trim();
        int bottom = linearLayout.getPaddingBottom();
        int top = linearLayout.getPaddingTop();
        int right = linearLayout.getPaddingRight();
        int left = linearLayout.getPaddingLeft();
        linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_rectangle_edit_text,null));
        linearLayout.setPadding(left,top,right,bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edit_text_error_border,null));
            linearLayout.setPadding(left,top,right,bottom);
            autoCompleteTextView.setFocusable(true);
            autoCompleteTextView.requestFocus();
            autoCompleteTextView.setError("Please enter a value");
            return false;
        }


        return true;
    }
    public  boolean selectedText(Spinner spinner,LinearLayout layout) {

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
            ((TextView)spinner.getSelectedView()).setError("Please select a value ");
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
        if (etxtPhone.getText().toString().length() < 9) {
            check = false;

            etxtPhone.setError("Phone number must have at least 9 digits");
        }
        if (!binding.emailAddressEt.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")){
            check = false;

            binding.emailAddressEt.setError("Invalid email");
        }
        if(spinDistrict.getText().toString().length()<3) {
            spinDistrict.setError("District must have at least 3 characters");
            check = false;
        }
        if(spinSubcounty.getText().toString().length()<3) {
            spinSubcounty.setError("Sub-County must have at least 3 characters");
            check = false;
        }

        if (spinVillage.getText().toString().length() < 3){
            spinVillage.setError("village must have at least 3 characters");
            check = false;
        }
        if (!hasText(etxtEmail)) check = false;

        if (!hasText(etxtBusinessName)) check = false;
        if (!hasText(etxtOwner)) check = false;
        if (!hasText(etxtPhone)) check = false;
        if (!hasText(etxtFullAddress)) check = false;
        if(!selectedText(spinBusinessType,businessLayout)) check = false;
        if(!autoText(spinDistrict,districtLayout)) check = false;
        if(!autoText(spinSubcounty,subcountyLayout)) check = false;
        if(!autoText(spinVillage,villageLayout)) check = false;

        return check;
    }
}