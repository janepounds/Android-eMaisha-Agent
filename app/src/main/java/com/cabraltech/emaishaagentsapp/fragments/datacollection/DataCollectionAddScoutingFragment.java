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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

        spinInfestation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                infestation = adapterView.getItemAtPosition(i).toString();
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

    public boolean validateEntries(){
        String message = null;
        if (etxtDate.getText().toString().isEmpty()) {
            etxtDate.setError(getString(R.string.enter_date));
            etxtDate.requestFocus();
            return false;

        }else if (etxtFarmerName.getText().toString().isEmpty()) {
            etxtFarmerName.setError(getString(R.string.enter_farmer_name));
            etxtFarmerName.requestFocus();
            return false;
        } else if (etxtFarmerPhone.getText().toString().isEmpty()) {
            etxtFarmerPhone.setError(getString(R.string.enter_phone_number));
            etxtFarmerPhone.requestFocus();
            return false;
        } else if (etxtRecommendations.getText().toString().isEmpty()) {
            etxtRecommendations.setError(getString(R.string.enter_recommendation));
            etxtRecommendations.requestFocus();
            return false;

        } else if (spinInfested.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_infested);
            spinInfested.requestFocus();
            return false;

        } else if (spinInfestationType.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_infestation_type);
            spinInfestationType.requestFocus();
            return false;

        } else if (spinInfestation.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_infestation);
            spinInfestation.requestFocus();
            return false;

        } else if (spinInfestationLevel.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_infestation_level);
            spinInfestationLevel.requestFocus();
            return false;

        }else if (spinDistrict.getText().toString().isEmpty()) {
            spinDistrict.setError(getString(R.string.enter_district));
            spinDistrict.requestFocus();
            return false;
        } else if (spinSubCounty.getText().toString().isEmpty()) {
            spinSubCounty.setError(getString(R.string.enter_sub_county));
            spinSubCounty.requestFocus();
            return false;
        } else if (spinVillage.getText().toString().isEmpty()) {
            spinVillage.setError(getString(R.string.enter_village));
            spinVillage.requestFocus();
            return false;
        } else if(message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            etxtDate.setError(null);
            etxtFarmerName.setError(null);
            etxtFarmerPhone.setError(null);
            etxtRecommendations.setError(null);
            spinDistrict.setError(null);
            spinSubCounty.setError(null);
            spinVillage.setError(null);

            return true;

        }
    }

}