package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketBinding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketPriceBinding;
import com.cabraltech.emaishaagentsapp.network.BroadcastService;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DataCollectionAddMarketPriceFragment extends Fragment {
    private static final String TAG = "DataCollectionAddMarket";
    private FragmentDataCollectionAddMarketPriceBinding fragmentDataCollectionAddMarketPriceBinding;
    private Context context;
    private NavController navController;
    String  market_name,commodities,varieties,units;
    private Spinner spinMarket_name,spinUnits,spinCommodities;
    private EditText etxtWholeSale,etxtRetailSale,extVarieties;
    private TextView txtDate,txtWholesaleUnit,txtRetailUnit;
    private  LinearLayout commoditiesLayout,varietyLayout,marketLayout,unitsLayout;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }
    public DataCollectionAddMarketPriceFragment() {
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
        fragmentDataCollectionAddMarketPriceBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_data_collection_add_market_price, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Add Commodity Price");
        return fragmentDataCollectionAddMarketPriceBinding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtDate = view.findViewById(R.id.date_tv);

        spinMarket_name = view.findViewById(R.id.market_spinner);
        spinCommodities = view.findViewById(R.id.commodities_spinner);
        extVarieties = view.findViewById(R.id.variety_spinner);
        spinUnits = view.findViewById(R.id.measurement_units_spinner);


        etxtWholeSale = view.findViewById(R.id.wholesale_price_et);
        etxtRetailSale  = view.findViewById(R.id.retail_price_et);
        commoditiesLayout  = view.findViewById(R.id.commodoties_layout);
        varietyLayout  = view.findViewById(R.id.variety_layout);
        marketLayout  = view.findViewById(R.id.market_layout);
        unitsLayout  = view.findViewById(R.id.units_layout);
        txtRetailUnit  = view.findViewById(R.id.retail_price_per_unit_tv);
        txtWholesaleUnit  = view.findViewById(R.id.wholesale_price_per_unit_tv);

        Button btnSubmit = view.findViewById(R.id.submit_button);



        spinMarket_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                market_name = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                units = adapterView.getItemAtPosition(i).toString();
                if(units.toLowerCase().equals("kg")){
                    txtRetailUnit.setText("Per Kg");
                    txtWholesaleUnit.setText("Per Kg");
                }
                else if(units.toLowerCase().equals("tonnes")){
                    txtRetailUnit.setText("Per Tonne");
                    txtWholesaleUnit.setText("Per Tonne");
                } else if(units.toLowerCase().equals("boxes")){
                    txtRetailUnit.setText("Per Box");
                    txtWholesaleUnit.setText("Per Box");
                } else if(units.toLowerCase().equals("bags")){
                    txtRetailUnit.setText("Per Bag");
                    txtWholesaleUnit.setText("Per Bag");
                } else if(units.toLowerCase().equals("bushels")){
                    txtRetailUnit.setText("Per Bushel");
                    txtWholesaleUnit.setText("Per Bushel");
                } else if(units.toLowerCase().equals("pieces")){
                    txtRetailUnit.setText("Per Piece");
                    txtWholesaleUnit.setText("Per Piece");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEntries()) {
                    String date = txtDate.getText().toString().trim();
                    String wholesale_price = etxtWholeSale.getText().toString().trim();
                    String retail_sale = etxtRetailSale.getText().toString().trim();
                    varieties = extVarieties.getText().toString().trim();

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    Log.d(TAG, "onClick: " + date + spinCommodities.getSelectedItem().toString() + varieties + market_name + units + wholesale_price + retail_sale);

                    boolean check = databaseAccess.addMarketPrice(date, spinCommodities.getSelectedItem().toString(), varieties, market_name, units, wholesale_price, retail_sale);
                    if (check) {
                        Toast.makeText(getActivity(), "Market Price Added Successfully", Toast.LENGTH_SHORT).show();
                        getActivity().startService(new Intent(getActivity(), BroadcastService.class));
                        navController.navigate(R.id.action_dataCollectionAddMarketPriceFragment_to_sucessDialogFragment);

                    } else {
                        Toast.makeText(getActivity(), "An Error Occurred", Toast.LENGTH_SHORT).show();

                    }
                }

            }
        });




        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDatePicker(txtDate, getActivity());
            }
        });

//        navController = Navigation.findNavController(view);
//
//        fragmentDataCollectionAddMarketPriceBinding.submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //navigation to step 1
//
//                navController.navigate(R.id.action_dataCollectionAddMarketPriceFragment_to_dataCollectionConfirmMarketPriceFragment);
//
//            }
//        });

    }

    public static void addDatePicker(final TextView ed_, final Context context) {
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
            autoCompleteTextView.setError("Please enter a value");
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
            ((TextView)spinner.getSelectedView()).setError("Please select a value ");
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
            textView.setError("Please enter a value");
            return false;
        }
        return true;
    }
    public boolean validateEntries(){
        boolean check = true;
        if (!hasTextView(txtDate)) check = false;
        if (!selectedText(spinCommodities,commoditiesLayout)) check = false;
        if(!hasText(extVarieties)) check = false;
        if(extVarieties.getText().toString().length()<3)
            extVarieties.setError("Variety should be 3 characters or more");
        check = false;
        if(!selectedText(spinMarket_name,marketLayout)) check = false;
        if(!selectedText(spinUnits,unitsLayout)) check = false;
        if(!hasText(etxtWholeSale)) check = false;
        if(!hasText(etxtRetailSale)) check = false;

        return check;


    }

}