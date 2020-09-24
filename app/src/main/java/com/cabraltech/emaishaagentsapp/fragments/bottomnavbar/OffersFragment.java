package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

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
import com.cabraltech.emaishaagentsapp.models.OffersViewModel;

public class OffersFragment extends Fragment {

    private OffersViewModel offersViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        offersViewModel =
                ViewModelProviders.of(this).get(OffersViewModel.class);
        View root = inflater.inflate(R.layout.fragment_offers, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
       /* offersViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Offers");


        return root;
    }
}