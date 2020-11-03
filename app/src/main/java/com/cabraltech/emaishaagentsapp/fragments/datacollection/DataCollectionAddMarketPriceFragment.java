package com.cabraltech.emaishaagentsapp.fragments.datacollection;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.adapters.SpinnerItem;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketBinding;
import com.cabraltech.emaishaagentsapp.databinding.FragmentDataCollectionAddMarketPriceBinding;

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
    private Spinner spinMarket_name,spinVarieties,spinUnits;
    private AutoCompleteTextView spinCommodities;
    private EditText etxtWholeSale,etxtRetailSale;
    private TextView txtDate;

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
        spinVarieties = view.findViewById(R.id.variety_spinner);
        spinUnits = view.findViewById(R.id.measurement_units_spinner);


        etxtWholeSale = view.findViewById(R.id.wholesale_price_et);
        etxtRetailSale  = view.findViewById(R.id.retail_price_et);

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


        spinVarieties.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                varieties = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayList<String> commodities = new ArrayList<>();

        ArrayAdapter<String> commodityListAdapter = new ArrayAdapter<String>(context,  android.R.layout.simple_dropdown_item_1line, commodities);
        spinCommodities.setThreshold(1);
        spinCommodities.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        spinCommodities.setAdapter(commodityListAdapter);

        spinUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                units = adapterView.getItemAtPosition(i).toString();
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

                    DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
                    databaseAccess.open();

                    Log.d(TAG, "onClick: " + date + spinCommodities.getText().toString() + varieties + market_name + units + wholesale_price + retail_sale);

                    boolean check = databaseAccess.addMarketPrice(date, spinCommodities.getText().toString(), varieties, market_name, units, wholesale_price, retail_sale);
                    if (check) {
                        Toast.makeText(getActivity(), "Market Price Added Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), DashboardActivity.class);
                        startActivity(intent);
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

    public boolean validateEntries(){
        String message = null;
        if (spinCommodities.getText().toString().isEmpty()) {
            spinCommodities.setError(getString(R.string.enter_commodities));
            spinCommodities.requestFocus();
            return false;

        }else if (etxtRetailSale.getText().toString().isEmpty()) {
            etxtRetailSale.setError(getString(R.string.enter_retail_price));
            etxtRetailSale.requestFocus();
            return false;

        } else if (spinVarieties.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_varieties);
            spinVarieties.requestFocus();
            return false;

        } else if (spinMarket_name.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_market);
            spinMarket_name.requestFocus();
            return false;

        } else if (etxtWholeSale.getText().toString().isEmpty()) {
            etxtWholeSale.setError(getString(R.string.enter_wholesale_price));
            etxtWholeSale.requestFocus();
            return false;

        } else if (spinUnits.getSelectedItemPosition() == 0) {
            message = getString(R.string.select_association_membership);
            spinUnits.requestFocus();
            return false;

        } else if (txtDate.getText().toString().isEmpty()) {
            txtDate.setError(getString(R.string.enter_date));
            txtDate.requestFocus();
            return false;


        } else if(message != null) {
            Toast.makeText(context, getString(R.string.missing_fields_message) + message, Toast.LENGTH_LONG).show();
            return false;
        } else {
            spinCommodities.setError(null);
            txtDate.setError(null);
            etxtWholeSale.setError(null);
            etxtRetailSale.setError(null);

            return true;

        }

    }

}