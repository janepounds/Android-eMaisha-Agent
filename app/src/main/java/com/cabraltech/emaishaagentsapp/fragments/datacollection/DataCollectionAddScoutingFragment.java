package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddScoutingBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;

import org.json.JSONException;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DataCollectionAddScoutingFragment extends Fragment {
    private static final String TAG = "DataCollectionAddScouti";

    private FragmentDataCollectionAddScoutingBinding binding;
    private Context context;
    private NavController navController;
    String damage, infested, infestation_type, infestation, infestation_level;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();

    private EditText etxtDate,etxtFarmerName,etxtFarmerPhone,etxtRecommendations;
    private Spinner spinInfested,spinInfestationLevel,spinInfestationType,spinInfestation;
    private AutoCompleteTextView spinDistrict,spinSubCounty,spinVillage;
    private LinearLayout districtLayout,subcountyLayout,villageLayout,infestedLayout,infestationTypeLayout,infestationLayout,infestationLevelLayout;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DataCollectionAddScoutingFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_collection_add_scouting, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Scouting");

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etxtDate = view.findViewById(R.id.scouting_date_et);
        etxtFarmerName = view.findViewById(R.id.scouting_farm_name_et);
        etxtFarmerPhone = view.findViewById(R.id.scouting_farmer_phone_number_et);
        etxtRecommendations = view.findViewById(R.id.scouting_recommendation_et);


        spinDistrict = view.findViewById(R.id.scouting_district_spinner);
        spinSubCounty = view.findViewById(R.id.scouting_sub_county_spinner);
        spinVillage = view.findViewById(R.id.scouting_village_spinner);
        spinInfested = view.findViewById(R.id.scouting_infested_spinner);
        spinInfestationLevel = view.findViewById(R.id.scouting_infestation_level_spinner);
        spinInfestationType = view.findViewById(R.id.scouting_infestation_type_spinner);
        spinInfestation = view.findViewById(R.id.scouting_infestation_spinner);

        districtLayout = view.findViewById(R.id.district_layout);
        subcountyLayout = view.findViewById(R.id.subcounty_layout);
        villageLayout = view.findViewById(R.id.village_layout);
        infestedLayout = view.findViewById(R.id.infested_layout);
        infestationTypeLayout = view.findViewById(R.id.infestation_type_layout);
        infestationLayout = view.findViewById(R.id.infestation_layout);
        infestationLevelLayout = view.findViewById(R.id.infestation_level_layout);

        Button btnSubmit = view.findViewById(R.id.submit_button);

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

 

        spinInfested.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infested = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        spinInfestationLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infestation_level = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinInfestationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infestation_type = adapterView.getItemAtPosition(i).toString();
                if(infestation_type.equalsIgnoreCase("Disease")){
                    ArrayAdapter<String> diseases = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_crop_scouting_infestation_disease));
                    spinInfestation.setAdapter(diseases);

                }else if(infestation_type.equalsIgnoreCase("Nematode")){
                    ArrayAdapter<String> nematodes = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_crop_scouting_infestation_nematode));
                    spinInfestation.setAdapter(nematodes);

                }else if(infestation_type.equalsIgnoreCase("Pest")){
                    ArrayAdapter<String> pests = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_crop_scouting_infestation_pest));
                    spinInfestation.setAdapter(pests);

                }else if(infestation_type.equalsIgnoreCase("Rodent")){
                    ArrayAdapter<String> rodents = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_crop_scouting_infestation_rodent));
                    spinInfestation.setAdapter(rodents);

                }else if(infestation_type.equalsIgnoreCase("Weed")){
                    ArrayAdapter<String> weeds = new ArrayAdapter<String>(context,  android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.sp_crop_scouting_infestation_weed));
                    spinInfestation.setAdapter(weeds);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinInfestation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infestation = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        etxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDatePicker(etxtDate, getActivity());
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEntries()) {
                    String date = etxtDate.getText().toString().trim();
                    String farmer_name = etxtFarmerName.getText().toString().trim();
                    String farmer_phone = etxtFarmerPhone.getText().toString().trim();
                    String recommendation = etxtRecommendations.getText().toString().trim();

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    boolean check = databaseAccess.addScoutingReport(date, farmer_name, spinDistrict.getText().toString(), spinSubCounty.getText().toString(), spinVillage.getText().toString(), farmer_phone, infested, infestation_type, infestation, infestation_level, recommendation);
                    if (check) {
                        Toast.makeText(getActivity(), "Scouting Report Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getContext(), DataCollectionMarketDataFragment.class);
                startActivity(intent);
                getActivity().finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static void addDatePicker(final EditText ed_, final Context context) {
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
            return false;
        }

        return true;
    }
    public boolean hasTextView(TextView textView){
        String text = textView.getText().toString().trim();
        int bottom = textView.getPaddingBottom();
        int top = textView.getPaddingTop();
        int right = textView.getPaddingRight();
        int left = textView.getPaddingLeft();
        textView.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_rectangle_edit_text,null));
        textView.setPadding(left,top,right,bottom);
        // length 0 means there is no text
        if (text.isEmpty()) {

            textView.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.edit_text_error_border,null));
            textView.setPadding(left,top,right,bottom);
            textView.setFocusable(true);
            textView.requestFocus();
            return false;
        }
        return true;
    }
    public boolean validateEntries(){
        boolean check = true;
        if (!hasText(etxtDate)) check = false;
        if (!hasText(etxtFarmerName)) check = false;
        if(!autoText(spinDistrict,districtLayout)) check = false;
        if(!autoText(spinSubCounty,subcountyLayout)) check = false;
        if(!autoText(spinVillage,villageLayout)) check = false;
        if (!hasText(etxtFarmerPhone)) check = false;
        if(!selectedText(spinInfested,infestedLayout)) check = false;
        if(!selectedText(spinInfestationType,infestationTypeLayout)) check = false;
        if(!selectedText(spinInfestation,infestationLayout)) check = false;
        if(!selectedText(spinInfestationLevel,infestationLevelLayout)) check = false;
        if (!hasText(etxtRecommendations)) check = false;


        return check;

    }

}