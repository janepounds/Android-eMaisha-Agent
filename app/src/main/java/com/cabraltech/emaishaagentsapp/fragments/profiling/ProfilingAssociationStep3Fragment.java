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
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAssociationStep3Binding;
import com.kofigyan.stateprogressbar.StateProgressBar;

public class ProfilingAssociationStep3Fragment extends Fragment {

    private static final String TAG = "ProfilingAssociationSte";
    private Context context;
    private NavController navController;
    private FragmentProfilingAssociationStep3Binding binding;

    String[] descriptionData = {"Contact\nDetails", "Governance", "Association\nDetails"};
    String livestock_value_chain, crop_value_chain, district, sub_county, village, registration_level, organisation_type;
    CheckBox chkProduction, chkStorage, chkBulking, chkProcessing, chkAgriculturalMarketing;
    CheckBox chkSprayPump, chkTractor, chkShelter, chkWeeder, chkCombinedHarvester, chkNone, chkDryer, chkMillingMachine, chkOxPlough, ChkPlanter, chkWetProcessingMachine;
    CheckBox chkTraders, chkProcessors, chkFinalConsumer;
    CheckBox chkBuyer, chkNgo, chkCallCenter, chkGovernmentExtension, chkFarmerOrganisation, chkRadioTv, chkMedia, chkPrivate;
    CheckBox chkMemberFees, chkSales, chkProcessingFees, chkGrants, chkCredit;
    CheckBox chkCropInsurance, chkMarketIntelligence, chkAgroInputsOnCredit, chkAgroEquipment, chkTrainingOnBusinessDevt, chkTrainingOnInstitutionalDevt, chkCashLoansAgriculturalPurposes, chkNonCashLoansAgriculturalPurposes, chkTrainingOrTechnicalAssistance, chkSubsidizedInput;
    String respondent_position, name, year_of_registration, full_address, telephone, email, chairperson, chairperson_contact, secretary, secretary_contact, respondent, respondent_contact;
    private LinearLayout cropvalueLayout,livestockLayout;
    private  EditText etxtMalesNumber,etxtFemalesNumber;
    private  Spinner spinLivestockValueChain,spinCropValueChain;
    private  LinearLayout mainActivitiesLayout,assetOwnerShipLayout,marketLayout,marketingchannelsLayout,fundingsourceLayout,additionalserviceLayout;
    public ProfilingAssociationStep3Fragment() {
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
        name = getArguments().getString("name");
        year_of_registration = getArguments().getString("year_of_registration");
        email = getArguments().getString("email");
        full_address = getArguments().getString("full_address");
        telephone = getArguments().getString("telephone");
        respondent_position = getArguments().getString("respondent_position");
        chairperson = getArguments().getString("chairperson");
        chairperson_contact = getArguments().getString("chairperson_contact");
        secretary = getArguments().getString("secretary");
        secretary_contact = getArguments().getString("secretary_contact");
        respondent = getArguments().getString("respondent");
        respondent_contact = getArguments().getString("respondent_contact");
        district = getArguments().getString("district");
        sub_county = getArguments().getString("sub_county");
        village = getArguments().getString("village");
        registration_level = getArguments().getString("registration_level");
        organisation_type = getArguments().getString("organisation_type");


        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_association_step3, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Onboarding Association");

        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.associationProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

        etxtMalesNumber = view.findViewById(R.id.etxt_number_of_males);
        etxtFemalesNumber = view.findViewById(R.id.etxt_number_of_females);
        spinLivestockValueChain = view.findViewById(R.id.livestock_value_chain_spinner);
        spinCropValueChain = view.findViewById(R.id.crop_value_chains_spinner);
        chkAgriculturalMarketing = view.findViewById(R.id.main_activities_agricultural_marketing_cb);
        chkBulking = view.findViewById(R.id.main_activities_bulking_cb);
        chkProcessing = view.findViewById(R.id.main_activities_processing_cb);
        chkStorage = view.findViewById(R.id.main_activities_storage_cb);
        chkProduction = view.findViewById(R.id.main_activities_production_cb);
        chkSprayPump = view.findViewById(R.id.asset_ownership_spray_pump_cb);
        chkTractor = view.findViewById(R.id.asset_ownership_tractor_cb);
        chkShelter = view.findViewById(R.id.asset_ownership_sheller_cb);
        chkWeeder = view.findViewById(R.id.asset_ownership_weeder_cb);
        chkCombinedHarvester = view.findViewById(R.id.asset_ownership_combined_harvester_cb);
        chkNone = view.findViewById(R.id.asset_ownership_none_cb);
        chkDryer = view.findViewById(R.id.asset_ownership_dryer_cb);
        chkMillingMachine = view.findViewById(R.id.asset_ownership_milling_machine_cb);
        chkOxPlough = view.findViewById(R.id.asset_ownership_ox_plough_cb);
        ChkPlanter = view.findViewById(R.id.asset_ownership_planter_cb);
        chkWetProcessingMachine = view.findViewById(R.id.asset_ownership_wet_processing_machine_cb);
        chkTraders = view.findViewById(R.id.market_traders_cb);
        chkProcessors = view.findViewById(R.id.market_processors_cb);
        chkFinalConsumer = view.findViewById(R.id.market_final_consumer_cb);
        chkBuyer = view.findViewById(R.id.marketing_channels_buyer_cb);
        chkNgo = view.findViewById(R.id.marketing_channels_ngo_cb);
        chkCallCenter = view.findViewById(R.id.marketing_channels_call_center_cb);
        chkGovernmentExtension = view.findViewById(R.id.marketing_channels_govt_extension_workers_cb);
        chkFarmerOrganisation = view.findViewById(R.id.marketing_channels_farmer_organisation_marketeers_cb);
        chkRadioTv = view.findViewById(R.id.marketing_channels_radio_tv_cb);
        chkMedia = view.findViewById(R.id.marketing_channels_media_online_cb);
        chkPrivate = view.findViewById(R.id.marketing_channels_private_extension_workers_cb);
        chkMemberFees = view.findViewById(R.id.funding_source_membership_fees_cb);
        chkSales = view.findViewById(R.id.funding_source_sales_cb);
        chkProcessingFees = view.findViewById(R.id.funding_source_processing_fees_cb);
        chkGrants = view.findViewById(R.id.funding_source_grants_cb);
        chkCredit = view.findViewById(R.id.funding_source_credit_cb);
        chkCropInsurance = view.findViewById(R.id.additional_services_crop_insurance_cb);
        chkMarketIntelligence = view.findViewById(R.id.additional_services_market_intelligence_cb);
        chkAgroInputsOnCredit = view.findViewById(R.id.additional_services_agricultural_inputs_cb);
        chkTrainingOnBusinessDevt = view.findViewById(R.id.additional_services_training_business_devt_cb);
        chkCashLoansAgriculturalPurposes = view.findViewById(R.id.additional_services_cash_loans_agric_cb);
        chkNonCashLoansAgriculturalPurposes = view.findViewById(R.id.additional_services_cash_loans_non_agric_cb);
        chkTrainingOrTechnicalAssistance = view.findViewById(R.id.additional_services_training_agricultural_practices_cb);
        chkSubsidizedInput = view.findViewById(R.id.additional_services_subsidized_inputs_cb);
        chkAgroEquipment = view.findViewById(R.id.additional_services_agricultural_equipment_cb);
        chkTrainingOnInstitutionalDevt = view.findViewById(R.id.additional_services_training_institutional_devt_cb);
        cropvalueLayout = view.findViewById(R.id.crop_layout);
        livestockLayout = view.findViewById(R.id.livestock_layout);
        mainActivitiesLayout = view.findViewById(R.id.main_activities_layout);
        assetOwnerShipLayout = view.findViewById(R.id.asset_ownership_layout);
        marketLayout = view.findViewById(R.id.market_layout);
        marketingchannelsLayout = view.findViewById(R.id.marketing_channels_layout);
        fundingsourceLayout = view.findViewById(R.id.funding_source_layout);
        additionalserviceLayout = view.findViewById(R.id.additional_services_layout);


        spinCropValueChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                crop_value_chain = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinLivestockValueChain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                livestock_value_chain = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEntries()) {
                    String number_of_males = etxtMalesNumber.getText().toString().trim();
                    String number_of_females = etxtFemalesNumber.getText().toString().trim();
                    String main_activities = "";
                    String asset_ownership = "";
                    String market = "";
                    String marketing_channels = "";
                    String funding_source = "";
                    String additional_services = "";
                    if (chkAgriculturalMarketing.isChecked()) {
                        main_activities += "\nAgricultural Marketing";
                    }
                    if (chkBulking.isChecked()) {
                        main_activities += "\nBulking";
                    }
                    if (chkStorage.isChecked()) {
                        main_activities += "\nStorage";
                    }
                    if (chkProduction.isChecked()) {
                        main_activities += "\nProduction";
                    }
                    if (chkProcessing.isChecked()) {
                        main_activities += "\nProcessing";
                    }
                    if (chkSprayPump.isChecked()) {
                        asset_ownership += "\nSpray Pump";
                    }
                    if (chkTractor.isChecked()) {
                        asset_ownership += "\nTractor";
                    }
                    if (chkShelter.isChecked()) {
                        asset_ownership += "\nShelter";
                    }
                    if (chkWeeder.isChecked()) {
                        asset_ownership += "\nWeeder";
                    }
                    if (chkCombinedHarvester.isChecked()) {
                        asset_ownership += "\nCombined Harvester";
                    }
                    if (chkNone.isChecked()) {
                        asset_ownership += "\nNone";
                    }
                    if (chkDryer.isChecked()) {
                        asset_ownership += "\nDryer";
                    }
                    if (chkMillingMachine.isChecked()) {
                        asset_ownership += "\nMilling Machine";
                    }
                    if (chkOxPlough.isChecked()) {
                        asset_ownership += "\nOx Plough";
                    }
                    if (ChkPlanter.isChecked()) {
                        asset_ownership += "\nPlanter";
                    }
                    if (chkWetProcessingMachine.isChecked()) {
                        asset_ownership += "\nWet Processing Machine";
                    }
                    if (chkTraders.isChecked()) {
                        market += "\nTraders";
                    }
                    if (chkProcessors.isChecked()) {
                        market += "\nProcessors";
                    }
                    if (chkFinalConsumer.isChecked()) {
                        market += "\nFinal Consumers";
                    }
                    if (chkBuyer.isChecked()) {
                        marketing_channels += "\nBuyer";
                    }
                    if (chkNgo.isChecked()) {
                        marketing_channels += "\nNGO";
                    }
                    if (chkCallCenter.isChecked()) {
                        marketing_channels += "\nCall Center";
                    }
                    if (chkGovernmentExtension.isChecked()) {
                        marketing_channels += "\nGovernment Extension Workers";
                    }
                    if (chkFarmerOrganisation.isChecked()) {
                        marketing_channels += "\nFarmer Organisation Marketeers";
                    }
                    if (chkRadioTv.isChecked()) {
                        marketing_channels += "\nRadio/Tv";
                    }
                    if (chkMedia.isChecked()) {
                        marketing_channels += "\nMedia/Online";
                    }
                    if (chkPrivate.isChecked()) {
                        marketing_channels += "\nPrivate Extension Workers";
                    }
                    if (chkMemberFees.isChecked()) {
                        funding_source += "\nMember Fees";
                    }
                    if (chkSales.isChecked()) {
                        funding_source += "\nSales";
                    }
                    if (chkProcessingFees.isChecked()) {
                        funding_source += "\nProcessing Fees";
                    }
                    if (chkGrants.isChecked()) {
                        funding_source += "\nGrants";
                    }
                    if (chkCredit.isChecked()) {
                        funding_source += "\nCredit";
                    }
                    if (chkCropInsurance.isChecked()) {
                        additional_services += "\nCrop Insurance";
                    }
                    if (chkMarketIntelligence.isChecked()) {
                        additional_services += "\nMarket Intelligence";
                    }
                    if (chkAgroInputsOnCredit.isChecked()) {
                        additional_services += "\nAgricultural Inputs on credit";
                    }
                    if (chkAgroEquipment.isChecked()) {
                        additional_services += "\nAccess to Agricultural Equipment";
                    }
                    if (chkTrainingOnBusinessDevt.isChecked()) {
                        additional_services += "\nTraining on business development";
                    }
                    if (chkTrainingOnInstitutionalDevt.isChecked()) {
                        additional_services += "\nTraining on institutional development";
                    }
                    if (chkCashLoansAgriculturalPurposes.isChecked()) {
                        additional_services += "\nCash loans for agricultural Purposes";
                    }
                    if (chkNonCashLoansAgriculturalPurposes.isChecked()) {
                        additional_services += "\nCash loans for non agricultural Purposes";
                    }
                    if (chkTrainingOrTechnicalAssistance.isChecked()) {
                        additional_services += "\nTraining or technical assistance in agricultural practices of technology";
                    }

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    boolean check = databaseAccess.addAssociation(name, year_of_registration, district, sub_county, village, full_address, telephone, email, crop_value_chain, livestock_value_chain, chairperson, chairperson_contact, secretary, secretary_contact, number_of_males, number_of_females, registration_level, respondent, respondent_contact, asset_ownership, organisation_type, main_activities, market, marketing_channels, funding_source, additional_services);
                    if (check) {
                        Toast.makeText(getActivity(), "Association Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });
        binding.previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //popback stack to step 2
                navController.popBackStack();
            }
        });


    }

//    public boolean TestCheckBox(LinearLayout linearLayout,String message) {
//        boolean result = false;
//        int checked = 0;
//        View v = null;
//        int bottom = linearLayout.getPaddingBottom();
//        int top = linearLayout.getPaddingTop();
//        int right = linearLayout.getPaddingRight();
//        int left = linearLayout.getPaddingLeft();
//        int count = linearLayout.getChildCount();
//
//        for (int n = 0; n < count; ++n) {
//            v = linearLayout.getChildAt(n);
//            Log.d(TAG, "TestCheckBox: " + v + count);
//
//            if (v instanceof LinearLayout) {
//                int count1 = ((LinearLayout) v).getChildCount();
//                for (int i = 0; i < count1; i++) {
//                    v = ((LinearLayout) v).getChildAt(i);
//                    Log.d(TAG, "TestCheckBoxx: " + v + count);
//
//                    if (v instanceof CheckBox) {
//                        String checkboxName = ((CheckBox) v).getText().toString();
//                        ++checked;
//                        Log.d(TAG, "TestCheckBox: checked" + checked + checkboxName);
//
//                        linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
//                        linearLayout.setPadding(left, top, right, bottom);
//                        result = true;
//                    }
//
////                 else {
////                    Log.d(TAG, "TestCheckBox: not checked");
////                    linearLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
////                    linearLayout.setPadding(left, top, right, bottom);
////                    linearLayout.setFocusable(true);
////                    linearLayout.requestFocus();
////                    Toast.makeText(context, message, Toast.LENGTH_LONG).show();
////                    result = false;
////                }
//                }
//            }
//        }
//
//        return result;
//        }






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
    public  boolean selectedText(Spinner spinner, LinearLayout layout) {

        int position = spinner.getSelectedItemPosition();
        int bottom = layout.getPaddingBottom();
        int top = layout.getPaddingTop();
        int right = layout.getPaddingRight();
        int left = layout.getPaddingLeft();
        layout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.rounded_rectangle_edit_text,null));
        layout.setPadding(left,top,right,bottom);

        // length 0 means there is no text
        if (position == 0) {

            layout.setBackground(ResourcesCompat.getDrawable(getResources(),R.drawable.spinner_error_border,null));
            layout.setPadding(left,top,right,bottom);
            layout.setFocusable(true);
            layout.requestFocus();
            return false;
        }

        return true;
    }


    public boolean validateEntries() {
        boolean check = true;
        if (!hasText(etxtMalesNumber)) check = false;
        if (!hasText(etxtFemalesNumber)) check = false;
        if(!selectedText(spinCropValueChain,cropvalueLayout)) check = false;
        if(!selectedText(spinLivestockValueChain,livestockLayout)) check = false;
//        if(!TestCheckBox(mainActivitiesLayout,getString(R.string.select_atleast_one_checkbox))) check = false;
//        if(!TestCheckBox(assetOwnerShipLayout,getString(R.string.select_atleast_one_checkbox))) check = false;
//        if(!TestCheckBox(marketLayout,getString(R.string.select_atleast_one_checkbox))) check = false;
//        if(!TestCheckBox(marketingchannelsLayout,getString(R.string.select_atleast_one_checkbox))) check = false;
//        if(!TestCheckBox(fundingsourceLayout,getString(R.string.select_atleast_one_checkbox))) check = false;
//        if(!TestCheckBox(additionalserviceLayout,getString(R.string.select_atleast_one_checkbox))) check = false;



        return check;


    }


}