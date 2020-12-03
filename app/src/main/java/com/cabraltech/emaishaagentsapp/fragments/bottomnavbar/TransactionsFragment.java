package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.database.DatabaseAccess;
import com.cabraltech.emaishaagentsapp.models.TransactionsViewModel;

import java.util.HashMap;
import java.util.List;

public class TransactionsFragment extends Fragment {
    private Context context;

    private TransactionsViewModel transactionsViewModel;
    private TextView farmers_count,association_count,agro_input_count,bulk_buyer_count,market_count,market_price_count,pest_report_count,scouting_report_count;
    private List<HashMap<String, String>> total_entries;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        transactionsViewModel =
                ViewModelProviders.of(this).get(TransactionsViewModel.class);
        View view = inflater.inflate(R.layout.fragment_transactions, container, false);
//        final TextView textView = view.findViewById(R.id.text_notifications);
////        transactionsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
////            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
////        });
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Transactions");

        initilizeviews(view);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initilizeviews(View view){
        farmers_count = view.findViewById(R.id.farmers_count);
        association_count = view.findViewById(R.id.association_count);
        bulk_buyer_count = view.findViewById(R.id.bulk_buyer_count);
        agro_input_count = view.findViewById(R.id.agro_input_count);
        market_count = view.findViewById(R.id.market_count);
        market_price_count = view.findViewById(R.id.market_price_count);
        pest_report_count = view.findViewById(R.id.pest_report_count);
        scouting_report_count = view.findViewById(R.id.scouting_report_count);


        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(getActivity());
        databaseAccess.open();
        total_entries = databaseAccess.getTotalCount();
        for (int i = 0; i < total_entries.size(); i++) {
            int farmers_countt = Integer.parseInt(total_entries.get(i).get("farmers_count"));
            int association_countt = Integer.parseInt(total_entries.get(i).get("association_count"));
            int agro_input_countt = Integer.parseInt(total_entries.get(i).get("agro_input_count"));
            int bulk_buyer_countt = Integer.parseInt(total_entries.get(i).get("bulk_buyer_count"));
            int market_countt = Integer.parseInt(total_entries.get(i).get("market_count"));
            int market_price_countt = Integer.parseInt(total_entries.get(i).get("market_price_count"));
            int pest_report_countt = Integer.parseInt(total_entries.get(i).get("pest_report_count"));
            int scouting_report_countt = Integer.parseInt(total_entries.get(i).get("scouting_report_count"));


            //set textviews
            farmers_count.setText(farmers_countt);
            association_count.setText(association_countt);
            bulk_buyer_count.setText(bulk_buyer_countt);
            agro_input_count.setText(agro_input_countt);
            market_count.setText(market_countt);
            market_price_count.setText(market_price_countt);
            pest_report_count.setText(pest_report_countt);
            scouting_report_count.setText(scouting_report_countt);


        }

    }
}