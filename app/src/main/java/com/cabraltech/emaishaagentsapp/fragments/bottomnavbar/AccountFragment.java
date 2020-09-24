package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.databinding.FragmentAccountBinding;
import com.cabraltech.emaishaagentsapp.models.AccountViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;

    private AccountViewModel accountViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_account, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("My Account");

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        // TODO: Use the ViewModel
    }


    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.edit, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_edit:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}