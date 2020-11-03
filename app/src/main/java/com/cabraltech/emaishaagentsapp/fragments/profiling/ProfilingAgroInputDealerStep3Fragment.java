package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
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
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerStep3Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class ProfilingAgroInputDealerStep3Fragment extends Fragment {
    private static final String TAG = "ProfilingAgroInputDeale";
    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep3Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};
    String business_type, type_of_sales, registration_year, association_name, certification_number, registration_body, certification_type, registration_status, association_membership, district, sub_county, village, certification, business_name, full_address,owner,owner_contact;
    CheckBox chkSeed, chkPesticide, chkFoodStuff, chkGeneralMerchandise, chkFertilizer, chkFarmEquipment, chkHardware, chkVetDrugs;
    CheckBox chkInternet, chkTelevision, chkCallCenter, chkBuyers, chkRadio, chkFellowTraders, chkExtensionWorkers;
    CheckBox chkFriendsOrRelatives, chkPrivateMoneyLender, chkSaccos, chkPrivateEquity, chkCommercialBank, chMicroFinanceInstitution;
    CheckBox chkTraining, chkAdvisory, chkCredit, chkTechnology, chkMarketInformation, chkPrintedMaterial;
    private EditText etxtNumberOfOutlets;
    private Spinner spinBusinessType,spinTypeOfSales;


    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingAgroInputDealerStep3Fragment() {
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
        owner = getArguments().getString("owner");
        owner_contact = getArguments().getString("owner_contact");
        business_name = getArguments().getString("business_name");
        full_address = getArguments().getString("full_address");
        registration_year = getArguments().getString("registration_year");
        registration_body = getArguments().getString("registration_body");
        certification_number = getArguments().getString("certification_number");
        certification_type = getArguments().getString("certification_type");
        registration_status = getArguments().getString("registration_status");
        association_membership = getArguments().getString("association_membership");
        association_name = getArguments().getString("association_name");
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_agro_input_dealer_step3, container, false);
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
       etxtNumberOfOutlets = view.findViewById(R.id.number_of_outlets_et);
       spinBusinessType = view.findViewById(R.id.type_of_business_spinner);
        spinTypeOfSales = view.findViewById(R.id.type_of_sales_spinner);
        chkSeed = view.findViewById(R.id.items_sold_seed_cb);
        chkPesticide = view.findViewById(R.id.items_sold_pesticide_cb);
        chkFoodStuff = view.findViewById(R.id.items_sold_food_stuff_cb);
        chkGeneralMerchandise = view.findViewById(R.id.items_sold_general_merchandise_cb);
        chkFertilizer = view.findViewById(R.id.items_sold_fertilizer_cb);
        chkFarmEquipment = view.findViewById(R.id.items_sold_farm_equip_cb);
        chkHardware = view.findViewById(R.id.items_sold_hardware_cb);
        chkVetDrugs = view.findViewById(R.id.items_sold_vet_drugs_cb);
        chkInternet = view.findViewById(R.id.marketing_channels_internet_cb);
        chkTelevision = view.findViewById(R.id.marketing_channels_television_cb);
        chkCallCenter = view.findViewById(R.id.marketing_channels_call_center_cb);
        chkBuyers = view.findViewById(R.id.marketing_channels_buyers_cb);
        chkRadio = view.findViewById(R.id.marketing_channels_radio_cb);
        chkFellowTraders = view.findViewById(R.id.marketing_channels_traders_cb);
        chkExtensionWorkers = view.findViewById(R.id.marketing_channels_extension_workers_cb);
        chkFriendsOrRelatives = view.findViewById(R.id.funding_source_friends_relatives_cb);
        chkPrivateMoneyLender = view.findViewById(R.id.funding_source_private_money_lender_cb);
        chkSaccos = view.findViewById(R.id.funding_source_saccos_cb);
        chkPrivateEquity = view.findViewById(R.id.funding_source_private_equity_cb);
        chkCommercialBank = view.findViewById(R.id.funding_source_commercial_bank_cb);
        chMicroFinanceInstitution = view.findViewById(R.id.funding_source_micro_finance_cb);
        chkTraining = view.findViewById(R.id.additional_services_training_cb);
        chkAdvisory = view.findViewById(R.id.additional_services_advisory_cb);
        chkCredit = view.findViewById(R.id.additional_services_credit_input_cb);
        chkTechnology = view.findViewById(R.id.additional_services_tech_cb);
        chkMarketInformation = view.findViewById(R.id.additional_services_market_info_cb);
        chkPrintedMaterial = view.findViewById(R.id.additional_services_printed_material_cb);


        spinBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                business_type = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinTypeOfSales.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_of_sales = adapterView.getItemAtPosition(i).toString();
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
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    String number_of_outlets = etxtNumberOfOutlets.getText().toString().trim();
                    String item_sold = "";
                    String marketing_channels = "";
                    String funding_source = "";
                    String additional_services = "";
                    if (chkSeed.isChecked()) {
                        item_sold += "\nSeed";
                    }
                    if (chkPesticide.isChecked()) {
                        item_sold += "\nPesticide";
                    }
                    if (chkFoodStuff.isChecked()) {
                        item_sold += "\nFood Stuff";
                    }
                    if (chkGeneralMerchandise.isChecked()) {
                        item_sold += "\nGeneral Merchandise";
                    }
                    if (chkFertilizer.isChecked()) {
                        item_sold += "\nFertilizer";
                    }
                    if (chkFarmEquipment.isChecked()) {
                        item_sold += "\nFarm Equipment";
                    }
                    if (chkHardware.isChecked()) {
                        item_sold += "\nHardware";
                    }
                    if (chkVetDrugs.isChecked()) {
                        item_sold += "\nVet Drugs";
                    }
                    if (chkInternet.isChecked()) {
                        marketing_channels += "\nInternet";
                    }
                    if (chkTelevision.isChecked()) {
                        marketing_channels += "\nTelevision";
                    }
                    if (chkCallCenter.isChecked()) {
                        marketing_channels += "\nCall Center";
                    }
                    if (chkBuyers.isChecked()) {
                        marketing_channels += "\nBuyers from bigger Markets";
                    }
                    if (chkRadio.isChecked()) {
                        marketing_channels += "\nRadio";
                    }
                    if (chkExtensionWorkers.isChecked()) {
                        marketing_channels += "\nExtension Workers";
                    }
                    if (chkFellowTraders.isChecked()) {
                        marketing_channels += "\nFellow Traders";
                    }
                    if (chkFriendsOrRelatives.isChecked()) {
                        funding_source += "\nFriends or Relatives";
                    }
                    if (chkSaccos.isChecked()) {
                        funding_source += "\nSaccos";
                    }
                    if (chkPrivateEquity.isChecked()) {
                        funding_source += "\nPrivate Equity";
                    }
                    if (chkCommercialBank.isChecked()) {
                        funding_source += "\nCommercial Bank";
                    }
                    if (chMicroFinanceInstitution.isChecked()) {
                        funding_source += "\nMicro-Finance Institutions";
                    }
                    if (chkTraining.isChecked()) {
                        additional_services += "\nTraning";
                    }
                    if (chkAdvisory.isChecked()) {
                        additional_services += "\nAdvisory";
                    }
                    if (chkCredit.isChecked()) {
                        additional_services += "\nCredit(Input)";
                    }
                    if (chkTechnology.isChecked()) {
                        additional_services += "\nTechnology Demonstration";
                    }
                    if (chkMarketInformation.isChecked()) {
                        additional_services += "\nProvision of market Information";
                    }
                    if (chkPrintedMaterial.isChecked()) {
                        additional_services += "\nDissemination of Printed Material";
                    }


                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();
                    Log.d(TAG, "onClick: " + association_name + certification);

                    boolean check = databaseAccess.addDealer(business_name, district, sub_county, village, full_address, owner, owner_contact , certification_type, certification_number, registration_body, registration_year, registration_status, association_membership, association_name, business_type, number_of_outlets, type_of_sales, item_sold, marketing_channels, funding_source, additional_services);
                    if (check) {
                        Toast.makeText(getActivity(), "Agro Input Dealer Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });


    }

    public boolean validateEntries() {
        String message = null;

        if (etxtNumberOfOutlets.getText().toString().isEmpty()) {
            etxtNumberOfOutlets.setError(getString(R.string.enter_number_outlets));
            etxtNumberOfOutlets.requestFocus();
            return false;

        } else if (spinBusinessType.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_business_type);
            spinBusinessType.requestFocus();
            return false;

        } else if (spinTypeOfSales.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_sales_type);
            spinTypeOfSales.requestFocus();
            return false;

        } else if(message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;

        } else {
            etxtNumberOfOutlets.setError(null);

            return true;

        }

    }
}