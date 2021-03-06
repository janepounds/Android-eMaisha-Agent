package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.activities.LoginActivity;
import com.cabraltech.emaishaagentsapp.database.User_Info_DB;
import com.cabraltech.emaishaagentsapp.databinding.FragmentAccountBinding;
import com.cabraltech.emaishaagentsapp.models.AccountViewModel;
import com.cabraltech.emaishaagentsapp.utils.*;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {
    private static final String TAG = "AccountFragment";

    private FragmentAccountBinding binding;

    private AccountViewModel accountViewModel;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("My Account");

        sharedPreferences = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);

        // Edit UserID in SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);

        Log.d(TAG, "onCreateView: Login Status = " + sharedPreferences.getBoolean("isLogged_in", true));

        String name = sharedPreferences.getString(DashboardActivity.PREFERENCES_FIRST_NAME, "") + " " + sharedPreferences.getString(DashboardActivity.PREFERENCES_LAST_NAME, "");
        binding.userNameTv.setText(name);
        binding.userPhoneNumberTv.setText(sharedPreferences.getString(DashboardActivity.PREFERENCES_USER_EMAIL, null));

        String address = sharedPreferences.getString("addressStreet", "") + ", " + sharedPreferences.getString("addressCityOrTown", "") + ", " + sharedPreferences.getString("address_district", "") + ", ";
        binding.defaultUserLocation1Tv.setText(address);

        binding.layoutLogout.setOnClickListener(view -> {
            editor = sharedPreferences.edit();
            editor.putBoolean("isLogged_in", false);
            editor.apply();
            requireContext().startActivity(new Intent(requireActivity(), LoginActivity.class));
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);

        // TODO: Use the ViewModel
    }

    public void shareApp() {
        // Share App with the help of static method of Utilities class
        Utilities.shareMyApp(requireContext());
    }

    public void rateApp() {
        // Rate App with the help of static method of Utilities class
        Utilities.rateMyApp(getContext());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
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