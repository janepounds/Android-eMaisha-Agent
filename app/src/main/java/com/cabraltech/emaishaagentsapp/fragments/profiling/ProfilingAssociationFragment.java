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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAssociationBinding;
import com.cabraltech.emaishaagentsapp.models.RegionDetails;
import com.kofigyan.stateprogressbar.StateProgressBar;

import org.json.JSONException;

import java.util.ArrayList;

public class ProfilingAssociationFragment extends Fragment {
    private static final String TAG = "ProfilingAssociationFra";
    private Context context;
    private NavController navController;
    private FragmentProfilingAssociationBinding binding;
    String  organisation_type,registration_level;
    private int pickedDistrictId;
    private int pickedSubcountyId;
    private ArrayList<SpinnerItem> subcountyList = new ArrayList<>();
    private ArrayList<String> villageList = new ArrayList<>();


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
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profiling_association,container,false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Onboarding Association");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.associationProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final EditText etxtName = view.findViewById(R.id.association_name_et);
        final EditText etxtYear_of_registration = view.findViewById(R.id.year_of_registration_et);
        AutoCompleteTextView spinDistrict = view.findViewById(R.id.district_spinner);
        AutoCompleteTextView spinSubCounty = view.findViewById(R.id.sub_county_spinner);
        AutoCompleteTextView spinVillage = view.findViewById(R.id.village_spinner);
        final EditText etxtFull_address = view.findViewById(R.id.full_address_et);
        final EditText etxtTelephone = view.findViewById(R.id.association_telephone_et);
        final EditText etxtEmail = view.findViewById(R.id.association_email_et);
        Spinner spinOrganisationType = view.findViewById(R.id.organisation_type_spinner);
        Spinner spinRegistrationLevel = view.findViewById(R.id.registration_level_spinner);


        Button next_button = view.findViewById(R.id.next_button);

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

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etxtName.getText().toString().trim();
                String year_of_registration = etxtYear_of_registration.toString().trim();
                String full_address = etxtFull_address.getText().toString().trim();
                String telephone = etxtTelephone.getText().toString().trim();
                String email = etxtEmail.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("year_of_registration",year_of_registration);
                bundle.putString("full_address",full_address);
                bundle.putString("telephone",telephone);
                bundle.putString("email",email);
                bundle.putString("district",spinDistrict.getText().toString());
                bundle.putString("sub_county",spinSubCounty.getText().toString());
                bundle.putString("village",spinVillage.getText().toString());
                bundle.putString("registration_level",registration_level);
                bundle.putString("organisation_type",organisation_type);
                navController.navigate(R.id.action_profilingAssociationFragment_to_profilingAssociationStep2Fragment,bundle);

            }
        });






    }
}