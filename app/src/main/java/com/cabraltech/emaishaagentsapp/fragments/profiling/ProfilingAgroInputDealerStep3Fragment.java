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
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingAgroInputDealerStep3Binding;
import com.cabraltech.emaishaagentsapp.network.BroadcastService;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;

public class ProfilingAgroInputDealerStep3Fragment extends Fragment {
    private static final String TAG = "ProfilingAgroInputDeale";
    private Context context;
    private NavController navController;
    private FragmentProfilingAgroInputDealerStep3Binding binding;
    String[] descriptionData = {"Contact\nDetails", "Registration\nDetails", "Business\nDetails"};
    String business_type, type_of_sales, registration_year, association_name, certification_number, registration_body, certification_type, registration_status, association_membership, district, sub_county, village, certification, business_name, full_address, owner, owner_contact;
    CheckBox chkSeed, chkPesticide, chkFoodStuff, chkGeneralMerchandise, chkFertilizer, chkFarmEquipment, chkHardware, chkVetDrugs;
    CheckBox chkInternet, chkTelevision, chkCallCenter, chkBuyers, chkRadio, chkFellowTraders, chkExtensionWorkers, chkFarmerToFarmer, chkNone;
    CheckBox chkFriendsOrRelatives, chkPrivateMoneyLender, chkSaccos, chkPrivateEquity, chkCommercialBank, chMicroFinanceInstitution, chkSavings;
    CheckBox chkTraining, chkAdvisory, chkCredit, chkTechnology, chkMarketInformation, chkPrintedMaterial;
    private EditText etxtNumberOfOutlets;
    private Spinner spinBusinessType, spinTypeOfSales;
    private LinearLayout businessTypeLayout, salesTypeLayout;
    private String profiledUser = "agroinput";


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
        businessTypeLayout = view.findViewById(R.id.business_type_layout);
        salesTypeLayout = view.findViewById(R.id.type_of_sales_layout);
        chkFarmerToFarmer = view.findViewById(R.id.marketing_channels_famer_to_farmer_cb);
        chkNone = view.findViewById(R.id.marketing_channels_none_cb);
        chkSavings = view.findViewById(R.id.funding_source_savings_cb);


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
        binding.submitButton.setOnClickListener(v -> {

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
                if (chkNone.isChecked()) {
                    marketing_channels += "\nNone";
                }
                if (chkFarmerToFarmer.isChecked()) {
                    marketing_channels += "\nFarmer to Farmer";
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
                if (chkSavings.isChecked()) {
                    funding_source += "\nSavings";
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

                boolean check = databaseAccess.addDealer(business_name, district, sub_county, village, full_address, owner, owner_contact, certification_type, certification_number, registration_body, registration_year, registration_status, association_membership, association_name, business_type, number_of_outlets, type_of_sales, item_sold, marketing_channels, funding_source, additional_services);
                if (check) {
                    Toast.makeText(getActivity(), "Agro Input Dealer Added Successfully", Toast.LENGTH_SHORT).show();
                    getActivity().startService(new Intent(getActivity(), BroadcastService.class));

                    Bundle bundle = new Bundle();
                    bundle.putString("profiledUser",profiledUser);
                    bundle.putString("agrobiz",business_name);
                    bundle.putString("agro_village",village);

                    navController.navigate(R.id.action_profilingAgroInputDealerStep3Fragment_to_sucessDialogFragment,bundle);
                } else {
                    Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                }

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
            editText.setError("Please enter value");
            return false;
        }


        return true;
    }

    public boolean selectedText(Spinner spinner, LinearLayout layout) {

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
            ((TextView) spinner.getSelectedView()).setError("Please select a value ");
            return false;
        }

        return true;
    }

    public boolean validateEntries() {
        boolean check = true;
        if (!selectedText(spinBusinessType, businessTypeLayout)) check = false;
        if (!hasText(etxtNumberOfOutlets)) check = false;
        if (!selectedText(spinTypeOfSales, salesTypeLayout)) check = false;

        // Item sold
        ArrayList<CheckBox> itemsSold = new ArrayList<>();
        itemsSold.add(binding.itemsSoldSeedCb);
        itemsSold.add(binding.itemsSoldPesticideCb);
        itemsSold.add(binding.itemsSoldFoodStuffCb);
        itemsSold.add(binding.itemsSoldGeneralMerchandiseCb);
        itemsSold.add(binding.itemsSoldFertilizerCb);
        itemsSold.add(binding.itemsSoldFarmEquipCb);
        itemsSold.add(binding.itemsSoldHardwareCb);
        itemsSold.add(binding.itemsSoldVetDrugsCb);

        if (!setCheckBoxError(binding.layoutItemSold, isAnyCheckBoxChecked(itemsSold)))
            check = false;

        // Marketing channels
        ArrayList<CheckBox> marketingChannels = new ArrayList<>();
        marketingChannels.add(binding.marketingChannelsInternetCb);
        marketingChannels.add(binding.marketingChannelsTelevisionCb);
        marketingChannels.add(binding.marketingChannelsCallCenterCb);
        marketingChannels.add(binding.marketingChannelsNoneCb);
        marketingChannels.add(binding.marketingChannelsBuyersCb);
        marketingChannels.add(binding.marketingChannelsRadioCb);
        marketingChannels.add(binding.marketingChannelsExtensionWorkersCb);
        marketingChannels.add(binding.marketingChannelsTradersCb);
        marketingChannels.add(binding.marketingChannelsFamerToFarmerCb);

        if (!setCheckBoxError(binding.layoutMarketingChannels, isAnyCheckBoxChecked(marketingChannels)))
            check = false;

        // Funding source
        ArrayList<CheckBox> fundingSource = new ArrayList<>();
        fundingSource.add(binding.fundingSourceFriendsRelativesCb);
        fundingSource.add(binding.fundingSourcePrivateMoneyLenderCb);
        fundingSource.add(binding.fundingSourceSaccosCb);
        fundingSource.add(binding.fundingSourcePrivateEquityCb);
        fundingSource.add(binding.fundingSourceCommercialBankCb);
        fundingSource.add(binding.fundingSourceMicroFinanceCb);
        fundingSource.add(binding.fundingSourceSavingsCb);

        if (!setCheckBoxError(binding.layoutFundingSource, isAnyCheckBoxChecked(fundingSource)))
            check = false;

        // Additional services
        ArrayList<CheckBox> additionalServices = new ArrayList<>();
        additionalServices.add(binding.additionalServicesTrainingCb);
        additionalServices.add(binding.additionalServicesAdvisoryCb);
        additionalServices.add(binding.additionalServicesCreditInputCb);
        additionalServices.add(binding.additionalServicesTechCb);
        additionalServices.add(binding.additionalServicesMarketInfoCb);
        additionalServices.add(binding.additionalServicesPrintedMaterialCb);

        if (!setCheckBoxError(binding.layoutAdditionalServices, isAnyCheckBoxChecked(additionalServices)))
            check = false;

        return check;
    }

    private boolean isAnyCheckBoxChecked(ArrayList<CheckBox> checkBoxes) {
        boolean isAnyChecked = false;

        for (int i = 0; i < checkBoxes.size(); i++) {
            if (checkBoxes.get(i).isChecked()) {
                isAnyChecked = true;
            }

        }

        return isAnyChecked;
    }

    private boolean setCheckBoxError(LinearLayout layout, boolean anyChecked) {
        boolean checked = false;

        int bottom = layout.getPaddingBottom();
        int top = layout.getPaddingTop();
        int right = layout.getPaddingRight();
        int left = layout.getPaddingLeft();

        if (!anyChecked) {
            layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.edit_text_error_border, null));
        } else {
            checked = true;
            layout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.rounded_rectangle_edit_text, null));
        }

        layout.setPadding(left, top, right, bottom);

        return checked;
    }
}