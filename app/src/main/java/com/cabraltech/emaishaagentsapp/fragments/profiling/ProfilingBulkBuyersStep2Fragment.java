package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingBulkBuyersStep2Binding;
import com.cabraltech.emaishaagentsapp.network.BroadcastService;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingBulkBuyersStep2Fragment extends Fragment {
    private Context context;
    private NavController navController;
    private FragmentProfilingBulkBuyersStep2Binding binding;

    String[] descriptionData = {"Contact\nDetails", "Business\nDetails"};
    String district, sub_county,village, business_type,commodities,business_name,phone,email,full_address,owner;
    CheckBox chkIndividualFarmer, chkRuralTraders, chkFarmerOrganisation;
    CheckBox chkWithInDistrict, chkOutsideDistrict, chkOutsideCountry;
    CheckBox chkFriendsOrRelatives, chkPrivateMoneyLender, chkSaccos, chkVillageSavings, chkPrivateEquity, chkCommercialBank, chMicroFinanceInstitution,chkSavings;
    CheckBox chkInternet, chkTelevision, chkCallCenter, chkNgo, chkBuyers, chkRadio, chkExtensionWorkers, chkFellowTraders, chkGovernmentAgency,chkFarmerTofarmer;
    private LinearLayout addNewcommodity,moreCommodities;
    AutoCompleteTextView actCommodities;

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        this.context = context;
    }

    public ProfilingBulkBuyersStep2Fragment() {
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
        business_type = getArguments().getString("business_type");
        business_name = getArguments().getString("business_name");
        full_address = getArguments().getString("full_address");
        phone = getArguments().getString("phone");
        owner = getArguments().getString("owner");
        email = getArguments().getString("email");
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_bulk_buyers_step2, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Enroll Agro Trader");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.bulkBuyersProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      actCommodities = view.findViewById(R.id.commodities_spinner);


        chkIndividualFarmer = view.findViewById(R.id.supply_source_individual_farmer_cb);
        chkRuralTraders = view.findViewById(R.id.supply_source_rural_traders_cb);
        chkFarmerOrganisation = view.findViewById(R.id.supply_source_farmer_organizations_cb);
        chkWithInDistrict = view.findViewById(R.id.supplier_location_within_district_cb);
        chkOutsideDistrict = view.findViewById(R.id.supplier_location_outside_district_cb);
        chkOutsideCountry = view.findViewById(R.id.supplier_location_outside_country_cb);
        chkFriendsOrRelatives = view.findViewById(R.id.funding_source_friends_relatives_cb);
        chkPrivateMoneyLender = view.findViewById(R.id.funding_source_private_money_lender_cb);
        chkSaccos = view.findViewById(R.id.funding_source_saccos_cb);
        chkVillageSavings = view.findViewById(R.id.funding_source_village_savings_cb);
        chkPrivateEquity = view.findViewById(R.id.funding_source_private_equity_cb);
        chkCommercialBank = view.findViewById(R.id.funding_source_commercial_bank_cb);
        chMicroFinanceInstitution = view.findViewById(R.id.funding_source_micro_finance_cb);
        chkInternet = view.findViewById(R.id.marketing_channels_internet_cb);
        chkTelevision = view.findViewById(R.id.marketing_channels_television_cb);
        chkCallCenter = view.findViewById(R.id.marketing_channels_call_center_cb);
        chkNgo = view.findViewById(R.id.marketing_channels_ngo_cb);
        chkBuyers = view.findViewById(R.id.marketing_channels_buyers_cb);
        chkRadio = view.findViewById(R.id.marketing_channels_radio_cb);
        chkExtensionWorkers = view.findViewById(R.id.marketing_channels_extension_workers_cb);
        chkFellowTraders = view.findViewById(R.id.marketing_channels_traders_cb);
        chkGovernmentAgency = view.findViewById(R.id.marketing_channels_govt_agency_cb);
        chkSavings = view.findViewById(R.id.funding_source_savings_cb);
        chkFarmerTofarmer = view.findViewById(R.id.marketing_channels_famer_to_farmer_cb);
        addNewcommodity = view.findViewById(R.id.bulk_buyers_add_new_commodity);
        moreCommodities = view.findViewById(R.id.bulk_buyers_more_commodities);


        ArrayAdapter<String> commodityListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.crop_array));
        actCommodities.setThreshold(1);
        actCommodities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                actCommodities.showDropDown();

            }
        });
        actCommodities.setAdapter(commodityListAdapter);
        actCommodities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        navController = Navigation.findNavController(view);
        addNewcommodity.setOnClickListener(new View.OnClickListener() {
            int index =0;
            @Override
            public void onClick(View v) {

                AutoCompleteTextView t = new AutoCompleteTextView(context);
                t.setPadding(8,8,8,8);
                t.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle_edit_text));
                t.setThreshold(1);
                t.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        t.showDropDown();
                    }
                });
                t.setAdapter(commodityListAdapter);
                moreCommodities.addView(t);
                index++;
            }
        });
        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit
                if (validateEntries()) {
                    String supply_source = "";
                    String supplier_location = "";
                    String funding_source = "";
                    String marketing_channels = "";
                    String commodities = "";

                    if (chkFarmerOrganisation.isChecked()) {
                        supply_source += "\nFarmer Organisations";
                    }
                    if (chkIndividualFarmer.isChecked()) {
                        supply_source += "\nIndividual Farmer";
                    }
                    if (chkFarmerOrganisation.isChecked()) {
                        supply_source += "\nRural Traders";
                    }
                    if (chkWithInDistrict.isChecked()) {
                        supplier_location += "\nWitIn The District";
                    }
                    if (chkOutsideDistrict.isChecked()) {
                        supplier_location += "\nOutside The District";
                    }
                    if (chkOutsideCountry.isChecked()) {
                        supplier_location += "\nOutside The Country";
                    }
                    if (chkPrivateMoneyLender.isChecked()) {
                        funding_source += "\nPrivate Money Lender";
                    }
                    if (chkFriendsOrRelatives.isChecked()) {
                        funding_source += "\nFriends or Relatives";
                    }
                    if (chkSaccos.isChecked()) {
                        funding_source += "\nSaccos";
                    }
                    if (chkVillageSavings.isChecked()) {
                        funding_source += "\nVillage Savings and Lending Associations";
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
                    if (chkSavings.isChecked()) {
                        funding_source += "\nSavings";
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
                    if (chkNgo.isChecked()) {
                        marketing_channels += "\nNGO";
                    }
                    if (chkBuyers.isChecked()) {
                        marketing_channels += "\nBuyers from bigger markets";
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
                    if (chkGovernmentAgency.isChecked()) {
                        marketing_channels += "\nGovernment Agency";
                    }
                    if (chkFarmerTofarmer.isChecked()) {
                        marketing_channels += "\nFarmer to Farmer";
                    }
                    String firstCommodity = actCommodities.getText().toString();
                    commodities +="\n " + firstCommodity;
                    for (int i = 0; i < moreCommodities.getChildCount(); i++) {
                        v = moreCommodities.getChildAt(i);
                            String etValue = null;
                            if (v instanceof EditText) {
                                etValue = ((EditText) v).getText().toString();
                                commodities += "\n" + etValue;
                            }


                    }

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    boolean check = databaseAccess.addTrader(business_type, business_name, owner, commodities, phone, email, district, sub_county, village, full_address, supplier_location, supply_source, funding_source, marketing_channels);
                    if (check) {
                        Toast.makeText(getActivity(), "Agro Trader Added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().startService(new Intent(getActivity(), BroadcastService.class));
//                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
//                        startActivity(intent);
                        navController.navigate(R.id.action_profilingBulkBuyersStep2Fragment_to_sucessDialogFragment);
                    } else {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submit
                navController.popBackStack();
            }
        });


    }
    public  boolean autoText(AutoCompleteTextView editText) {

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
    public boolean validateEntries() {
        boolean check = true;
       if(!autoText(actCommodities)) check = false;

        return check;
    }
}