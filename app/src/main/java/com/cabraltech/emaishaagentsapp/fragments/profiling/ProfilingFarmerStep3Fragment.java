package com.cabraltech.emaishaagentsapp.fragments.profiling;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentProfilingFarmerStep3Binding;
import com.cabraltech.emaishaagentsapp.network.NetworkStateChecker;
import com.kofigyan.stateprogressbar.StateProgressBar;


public class ProfilingFarmerStep3Fragment extends Fragment {

    private Context context;
    private FragmentProfilingFarmerStep3Binding binding;
    private NavController navController;
    PopupWindow popupWindow;
    private ConstraintLayout constraintLayout;
    String[] descriptionData = {"Personal\nDetails", "Contact\nDetails", "Farming\nDetails"};
    String farming_size, second_livestock, main_crop, second_crop, third_crop, main_livestock, district, sub_county, village, gender, marital_status, religion, education_level, language_used, nationality, first_name, last_name, dob, age, household_size, household_head, source_of_income, phone_number, next_of_kin, next_of_kin_relation, next_of_kin_contact, next_of_kin_address;
    private EditText etxtFarmingSize;
    private Spinner spinMainCrop,spinSecondCrop,spinThirdCrop,spinMainLivestock,etxtSecondLivestock;
    private LinearLayout moreCropsLayout, moreLivestockLayout;
    public ProfilingFarmerStep3Fragment() {
        // Required empty public constructor
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
        phone_number = getArguments().getString("phone_number");
        next_of_kin = getArguments().getString("next_of_kin");
        next_of_kin_address = getArguments().getString("next_of_kin_address");
        next_of_kin_contact = getArguments().getString("next_of_kin_contact");
        next_of_kin_relation = getArguments().getString("next_of_kin_relation");
        district = getArguments().getString("district");
        sub_county = getArguments().getString("subcounty");
        village = getArguments().getString("village");

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profiling_farmer_step3, container, false);
        //setting the state progress bar labels
        StateProgressBar stateProgressBar = (StateProgressBar) binding.farmerProfilingStateProgressBar;
        stateProgressBar.setStateDescriptionData(descriptionData);
        stateProgressBar.setStateDescriptionTypeface("fonts/JosefinSans-Bold.ttf");
        //inflating pop up
        // ConstraintLayout constraintLayout = (ConstraintLayout) binding.
        // ((AppCompatActivity)getActivity()).getLayoutInflater().  getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Farmer Profiling");
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        navController = Navigation.findNavController(view);

         etxtFarmingSize = view.findViewById(R.id.farming_land_size_et);
         etxtSecondLivestock = view.findViewById(R.id.second_livestock_et);
         spinMainCrop = view.findViewById(R.id.main_crop_spinner);
         spinSecondCrop = view.findViewById(R.id.second_crop_spinner);
         spinThirdCrop = view.findViewById(R.id.third_crop_spinner);
         spinMainLivestock = view.findViewById(R.id.main_livestock_spinner);
         moreCropsLayout = view.findViewById(R.id.second_third_crop_layout);
         moreLivestockLayout = view.findViewById(R.id.second_livestock_layout);


        spinMainCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                main_crop = adapterView.getItemAtPosition(i).toString();
                if (main_crop.toLowerCase().equals("none")) {
                    moreCropsLayout.setVisibility(View.GONE);

                } else {
                    moreCropsLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinMainLivestock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                main_livestock = adapterView.getItemAtPosition(i).toString();

                if (main_livestock.toLowerCase().equals("none")) {
                    moreLivestockLayout.setVisibility(View.GONE);

                } else {
                    moreLivestockLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        etxtSecondLivestock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                second_crop = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinThirdCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                third_crop = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinSecondCrop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                second_livestock = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateEntries()) {
                    farming_size = etxtFarmingSize.getText().toString().trim();
                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    boolean check = databaseAccess.addFarmer(first_name, last_name, dob, age, gender, nationality, religion, education_level, marital_status, household_size, language_used, source_of_income, household_head, district, sub_county, village, phone_number, next_of_kin, next_of_kin_relation, next_of_kin_contact, next_of_kin_address, farming_size, main_crop, second_crop, third_crop, main_livestock, second_livestock);
                    if (check) {

                        Toast.makeText(getActivity(), "Farmer Profiled Successfully", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), "An Error Occur red", Toast.LENGTH_SHORT).show();

                    }


//                //pop up window
//                showPopup();
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

    public void showPopup() {

        View popupView = getLayoutInflater().inflate(R.layout.pop_up, null);

        PopupWindow popupWindow = new PopupWindow(popupView,
                ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);

        // Example: If you have a TextView inside `popup_layout.xml`
        TextView thankYouTv = (TextView) popupView.findViewById(R.id.thank_you_tv);
        TextView contentTv = (TextView) popupView.findViewById(R.id.pop_up_content);
        ImageView popUpImg = (ImageView) popupView.findViewById(R.id.pop_up_img);


        // Initialize more widgets from `popup_layout.xml`

        // If the PopupWindow should be focusable
        popupWindow.setFocusable(true);

        // If you need the PopupWindow to dismiss when when touched outside
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        int location[] = new int[2];

        // Get the View's(the one that was clicked in the Fragment) location
        //  anchorView.getLocationOnScreen(location);

        // Using location, the PopupWindow will be displayed right under anchorView
        // popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY,
        //        location[0], location[1] + anchorView.getHeight());

    }

    public boolean validateEntries() {
        String message = null;
        if (etxtFarmingSize.getText().toString().isEmpty()) {
            etxtFarmingSize.setError(getString(R.string.enter_farm_size));
            return false;
        } else if (spinMainCrop.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_main_crop);
            spinMainCrop.requestFocus();
            return false;
        } else if (spinMainLivestock.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_main_livestock);
            spinMainLivestock.requestFocus();
            return false;
        } else if (message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            etxtFarmingSize.setError(null);

            return true;
        }
    }


}