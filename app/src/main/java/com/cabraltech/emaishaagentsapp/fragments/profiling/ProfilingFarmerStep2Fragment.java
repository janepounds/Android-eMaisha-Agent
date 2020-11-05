package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep2Binding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep3Binding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;

import java.util.ArrayList;


public class ProfilingFarmerStep2Fragment extends Fragment {
    private static final String TAG = "ProfilingFarmerStep2Fra";
    private Context context;
    private FragmentProfilingFarmerStep2Binding binding;
    private NavController navController;
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    String district,sub_county,village,gender, marital_status, religion, education_level, language_used, nationality,first_name,last_name,dob,age,household_size,household_head,source_of_income;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();
    private AutoCompleteTextView spinDistrict,spinSubCounty,spinVillage;
    private EditText etxtPhone,etxt_next_of_kin,etxt_next_of_kin_relation,etxt_next_of_kin_contact,etxt_next_of_kin_address;
    private LinearLayout districtLayout,subCountyLayout,villageLayout;
    public ProfilingFarmerStep2Fragment() {
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

        first_name = getArguments().getString("first_name");
        last_name = getArguments().getString("last_name");
        gender = getArguments().getString("gender");
        age = getArguments().getString("age");
        marital_status = getArguments().getString("marital_status");
        religion = getArguments().getString("religion");
        education_level = getArguments().getString("education_level");
        language_used = getArguments().getString("language_used");
        nationality = getArguments().getString("nationality");
        dob = getArguments().getString("dob");
        household_size = getArguments().getString("household_size");
        household_head = getArguments().getString("household_head");
        source_of_income = getArguments().getString("source_of_income");

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_farmer_step2,container,false);
        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.farmerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Farmer Profiling");
        return  binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

         spinDistrict = view.findViewById(R.id.district_spinner);
         spinSubCounty = view.findViewById(R.id.sub_county_spinner);
         spinVillage = view.findViewById(R.id.village_spinner);
         etxtPhone = view.findViewById(R.id.phone_number_et);
         etxt_next_of_kin = view.findViewById(R.id.next_of_kin_et);
         etxt_next_of_kin_relation = view.findViewById(R.id.next_of_kin_relation_et);
         etxt_next_of_kin_contact = view.findViewById(R.id.next_of_kin_contact_et);
         etxt_next_of_kin_address = view.findViewById(R.id.next_of_kin_address_et);
        districtLayout = view.findViewById(R.id.district_layout);
        subCountyLayout = view.findViewById(R.id.subcounty_layout);
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

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {

                    String phone_number = etxtPhone.getText().toString().trim();
                    String next_of_kin = etxt_next_of_kin.getText().toString().trim();
                    String next_of_kin_relation = etxt_next_of_kin_relation.getText().toString().trim();
                    String next_of_kin_contact = etxt_next_of_kin_contact.getText().toString().trim();
                    String next_of_kin_address = etxt_next_of_kin_address.getText().toString().trim();
                    String district = spinDistrict.getText().toString();
                    String subcounty = spinSubCounty.getText().toString();
                    String village = spinVillage.getText().toString();


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
                    bundle.putString("phone_number", phone_number);
                    bundle.putString("next_of_kin", next_of_kin);
                    bundle.putString("next_of_kin_relation", next_of_kin_relation);
                    bundle.putString("next_of_kin_contact", next_of_kin_contact);
                    bundle.putString("next_of_kin_address", next_of_kin_address);
                    bundle.putString("district", district);
                    bundle.putString("subcounty", subcounty);
                    bundle.putString("village", village);
                    //navigate to step 3
                    navController.navigate(R.id.action_profilingFarmerStep2Fragment_to_profilingFarmerStep3Fragment, bundle);

                }
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
            linearLayout.setFocusable(true);
            linearLayout.requestFocus();
            return false;
        }


        return true;
    }


    public boolean validateEntries() {

        boolean check = true;
        if (!hasText(etxtPhone)|| etxtPhone.getText().toString().trim().length() < 9) check = false;
        if (!hasText(etxt_next_of_kin)) check = false;
        if (!hasText(etxt_next_of_kin_contact) || etxt_next_of_kin_contact.getText().toString().trim().length() < 9) check = false;
        if (!hasText(etxt_next_of_kin_address)) check = false;
        if (!hasText(etxt_next_of_kin_relation)) check = false;
        if (!autoText(spinDistrict,districtLayout)) check = false;
        if (!autoText(spinSubCounty,subCountyLayout)) check = false;
        if (!autoText(spinVillage,villageLayout)) check = false;
        // Toast.makeText(context, getString(R.string.missing_fields_message), Toast.LENGTH_LONG).show();
        return check;


    }


}