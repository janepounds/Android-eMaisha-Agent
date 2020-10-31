package com.cabraltech.emaishaagentsapp.fragments.datacollection;

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
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;

import org.json.JSONException;

import java.util.ArrayList;


public class DataCollectionAddMarketFragment extends Fragment {
    private static final String TAG = "DataCollectionAddMarket";
    private FragmentDataCollectionAddMarketBinding fragmentDataCollectionAddMarketBinding;
    private Context context;
    private NavController navController;
    String  market_name;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public DataCollectionAddMarketFragment() {
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
        fragmentDataCollectionAddMarketBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_data_collection_add_market, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Market");
        return fragmentDataCollectionAddMarketBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        navController = Navigation.findNavController(view);

        Spinner spinMarket_name = view.findViewById(R.id.market_name_spinner);
        final EditText etxtStreet_address = view.findViewById(R.id.street_address_et);
        AutoCompleteTextView spinDistrict = view.findViewById(R.id.district_spinner);
        AutoCompleteTextView spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        AutoCompleteTextView spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtContatc = view.findViewById(R.id.contact_person_et);
        final EditText etxtPhone = view.findViewById(R.id.phone_number_et);
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
        spinMarket_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                market_name = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String street_address = etxtStreet_address.getText().toString();
                String contact = etxtContatc.getText().toString().trim();
                String phone = etxtPhone.getText().toString().trim();

                DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                databaseAccess.open();

                boolean check = databaseAccess.addMarket(market_name,street_address,phone,spinDistrict.getText().toString(),spinSubCounty.getText().toString(),spinVillage.getText().toString(),contact);
                if (check) {
                    Toast.makeText(getActivity(), "Market Added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), DashboardActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                }

            }
        });

//        fragmentDataCollectionAddMarketBinding.submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //navigation to step 1
//
//
//            }
//        });

    }
}