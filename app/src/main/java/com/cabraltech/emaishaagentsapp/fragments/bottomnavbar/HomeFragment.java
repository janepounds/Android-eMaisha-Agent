package com.cabraltech.emaishaagentsapp.fragments.bottomnavbar;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.CarrierConfigManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
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
import com.cabraltech.emaishaagentsapp.models.weather.WeatherResponse;
import com.cabraltech.emaishaagentsapp.network.APIClient;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.LOCATION_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements LocationListener{
    private static final String TAG = "HomeFragment";

    private TextView textCommissions,temp,visibility,humidity,wind_speed,precipitation_type,precipitation,weather_day;
    LinearLayout profilingLayout, walletLayout, dataCollectionLayout, marketServicesLayout;
    private HomeViewModel homeViewModel;
    private double latitude,longitude;

    public static final String WEATHER_API_KEY = "0CHwh88HI3G4GK62bu6glx6K4Nfxv6Uy";

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
        getCooardinates();


        InitializeDashboard(view);
        return view;

    }

    public void getCooardinates() {
        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            if ((ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
                Log.d(TAG, "onCreateView: if");
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

            } else {
                //pick coordinates
                LocationManager locationManager = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isGpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10f, this);

                    if (locationManager != null) {
                        latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                        longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                    }


                }
                Log.d(TAG, "onCreateView: " + "latitude:" + latitude + " longitude:" + longitude);
            }
        }else {

                //pick coordinates
                LocationManager locationManager = (LocationManager) requireContext().getSystemService(LOCATION_SERVICE);
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                if (isGpsEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 10f, this);

                    if (locationManager != null) {
                        latitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
                        longitude = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
                    }



                Log.d(TAG, "onCreateView: " + "latitude:" + latitude + " longitude:" + longitude);
            }
        }
    }

    public void InitializeDashboard(View view) {
        textCommissions = view.findViewById(R.id.text_commissions);
        profilingLayout = view.findViewById(R.id.layout_dashboard_profiling);
        walletLayout = view.findViewById(R.id.layout_dashboard_wallet);
        dataCollectionLayout = view.findViewById(R.id.layout_dashboard_data_collection);
        marketServicesLayout = view.findViewById(R.id.layout_dashboard_markets_service);
        temp = view.findViewById(R.id.weather_temp_max);
        visibility = view.findViewById(R.id.visibility_default);
        humidity = view.findViewById(R.id.humidity_max);
        wind_speed = view.findViewById(R.id.wind_default);
        precipitation_type = view.findViewById(R.id.text_view_rain);
        precipitation = view.findViewById(R.id.rain_status);
        weather_day = view.findViewById(R.id.weather_day);


        updateCommission();
        Date now = new Date();
        SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE");
        weather_day.setText(simpleDateformat.format(now));

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


        ArrayList<String> fieldValues =new ArrayList<>();
        fieldValues.add("temp");
        fieldValues.add("visibility");
        fieldValues.add("humidity");
        fieldValues.add("wind_speed");
        fieldValues.add("precipitation");
        fieldValues.add("precipitation_type");

        //*******************WEATHER API INTEGRATION*************************//


        Call<WeatherResponse> call = APIClient.getWeatherInstance()
                .getRealtimeWeather(WEATHER_API_KEY,(float)latitude,(float)longitude,null,"si",fieldValues);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if(response.isSuccessful()){
                    //set temperature
                    double temperature_value = response.body().getTemp().getValue();
                    String temperature_units = response.body().getTemp().getUnits();
                    temp.setText(temperature_value + " " + "\u2103");


                    //set visibility
                    int visibility_value = response.body().getVisibility().getValue();
                    String visibility_units = response.body().getVisibility().getUnits();
                    visibility.setText(visibility_value+ " " +visibility_units);

                    //set humidity
                    double humidity_value = response.body().getHumidity().getValue();
                    String humidity_units = response.body().getHumidity().getUnits();
                    humidity.setText(humidity_value+ " " +humidity_units);

                    //set wind speed
                    double wind_speed_value = response.body().getWindSpeed().getValue();
                    String wind_speed_units = response.body().getWindSpeed().getUnits();
                    wind_speed.setText(wind_speed_value + " "+wind_speed_units);

                    //set precipitation
                    double precipitation_value = response.body().getPrecipitation().getValue();
                    String precipitation_units = response.body().getPrecipitation().getUnits();
                    precipitation.setText(precipitation_value+ " "+precipitation_units);


                    //set precipitation type
                    String precipitation_type_value = response.body().getPrecipitationType().getValue();
                    String precipitation_type_capitalized = precipitation_type_value.substring(0, 1).toUpperCase() + precipitation_type_value.substring(1);
                    precipitation_type.setText(precipitation_type_capitalized);




                }else{
                    Toast.makeText(getContext(),response.message(),Toast.LENGTH_LONG);
                    Log.d(TAG, "onResponse:  "+response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(),Toast.LENGTH_LONG);

            }
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

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){
            getCooardinates();

        }
    }
}