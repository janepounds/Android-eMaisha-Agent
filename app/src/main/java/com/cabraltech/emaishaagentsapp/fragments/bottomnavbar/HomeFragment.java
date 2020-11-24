package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.activities.DashboardActivity;
import com.cabraltech.emaishaagentsapp.activities.DataCollectionActivity;
import com.cabraltech.emaishaagentsapp.activities.MarketAndServicesActivity;
import com.cabraltech.emaishaagentsapp.activities.ProfilingActivity;
import com.cabraltech.emaishaagentsapp.models.CommissionResponse;
import com.cabraltech.emaishaagentsapp.models.HomeViewModel;
import com.cabraltech.emaishaagentsapp.models.authentication.LoginResponse;
import com.cabraltech.emaishaagentsapp.network.APIClient;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";

    private TextView textCommissions;
    LinearLayout profilingLayout, walletLayout, dataCollectionLayout, marketServicesLayout;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("eMaisha Agent");
        // ((AppCompatActivity)getActivity()).getSupportActionBar().getTitle().

        InitializeDashboard(view);
        return view;

    }

    public void InitializeDashboard(View view) {
        textCommissions = view.findViewById(R.id.text_commissions);
        profilingLayout = view.findViewById(R.id.layout_dashboard_profiling);
        walletLayout = view.findViewById(R.id.layout_dashboard_wallet);
        dataCollectionLayout = view.findViewById(R.id.layout_dashboard_data_collection);
        marketServicesLayout = view.findViewById(R.id.layout_dashboard_markets_service);

        updateCommission();

        profilingLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ProfilingActivity.class);
            v.getContext().startActivity(intent);
        });

        walletLayout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
        });

        dataCollectionLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DataCollectionActivity.class);
            v.getContext().startActivity(intent);
        });

        marketServicesLayout.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Coming Soon", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getContext(), MarketAndServicesActivity.class);
//            v.getContext().startActivity(intent);
        });
    }

    private void updateCommission() {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);

        Call<CommissionResponse> call = APIClient.getInstance().getCommission(Integer.parseInt(sharedPreferences.getString(DashboardActivity.PREFERENCES_USER_ID, "")));

        call.enqueue(new Callback<CommissionResponse>() {
            @Override
            public void onResponse(@NotNull Call<CommissionResponse> call, @NotNull Response<CommissionResponse> response) {

                if (response.isSuccessful()) {
                    assert response.body() != null;
                    final String data = "UGX " + NumberFormat.getNumberInstance(Locale.US).format(Double.parseDouble(response.body().getData()));
                    textCommissions.setText(data);
                    Log.d(TAG, "onResponse: Status = " + response.body().getStatus());
                    Log.d(TAG, "onResponse: Message = " + response.body().getMessage());
                    Log.d(TAG, "onResponse: Data = " + response.body().getData());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                    Log.d(TAG, "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NotNull Call<CommissionResponse> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}