package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.database.User_Info_DB;
import com.cabraltech.emaishaagentsapp.models.authentication.LoginResponse;
import com.cabraltech.emaishaagentsapp.models.authentication.LoginResponseData;
import com.cabraltech.emaishaagentsapp.models.authentication.RegistrationResponse;
import com.cabraltech.emaishaagentsapp.network.APIClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText emailOrPhone, password;
    private TextView createAccountTv, forgotPasswordTv;
    Button nextBtn;

    private ProgressDialog progressDialog;
    User_Info_DB userInfoDB;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        emailOrPhone = findViewById(R.id.email_address_mobile_number_et);
        password = findViewById(R.id.password_et);
        createAccountTv = findViewById(R.id.create_new_account_tv);
        forgotPasswordTv = findViewById(R.id.forgot_password_tv);
        nextBtn = findViewById(R.id.next_button);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.processing));
        progressDialog.setMessage("Logging you in...");
        progressDialog.setCancelable(false);

        createAccountTv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            v.getContext().startActivity(intent);
        });

        forgotPasswordTv.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            v.getContext().startActivity(intent);
        });
        userInfoDB = new User_Info_DB();
        sharedPreferences = getSharedPreferences("UserInfo", MODE_PRIVATE);

        nextBtn.setOnClickListener(v -> {
            if (emailOrPhone.getText().toString().trim().isEmpty()) {
                emailOrPhone.setError("Required");
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Required");
            }else{
                progressDialog.show();
                processLogin();
            }
        });
    }

    private void processLogin() {
        Call<LoginResponse> call = APIClient.getInstance().login(emailOrPhone.getText().toString(), password.getText().toString());

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NotNull Call<LoginResponse> call, @NotNull Response<LoginResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getSuccess());
                    Log.d(TAG, "onResponse: " + response.body().getData().getEmail());
                    loginUser(response.body().getData());
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<LoginResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void loginUser(LoginResponseData userDetails) {
        // Save User Data to Local Databases
        if (userInfoDB.getUserData(userDetails.getId()) != null) {
            // User already exists
            userInfoDB.updateUserData(userDetails);
        } else {
            // Insert Details of New User
            userInfoDB.insertUserData(userDetails);
        }
        Log.e("USERID", userDetails.getId());
        // Save necessary details in SharedPrefs
        editor = sharedPreferences.edit();
        editor.putString(DashboardActivity.PREFERENCES_USER_ID, userDetails.getId());
        editor.putString(DashboardActivity.PREFERENCES_USER_EMAIL, userDetails.getEmail());
        editor.putString(DashboardActivity.PREFERENCES_FIRST_NAME, userDetails.getFirstName());
        editor.putString(DashboardActivity.PREFERENCES_LAST_NAME, userDetails.getLastName());
        editor.putString(DashboardActivity.PREFERENCES_PHONE_NUMBER, userDetails.getPhone_number());

        editor.putString("addressStreet", userDetails.getVillage());
        editor.putString("addressCityOrTown", userDetails.getSubCounty());
        editor.putString("address_district", userDetails.getDistrict());

        editor.putBoolean("isLogged_in", true);
        editor.apply();

        // Set UserLoggedIn in MyAppPrefsManager
//        MyAppPrefsManager myAppPrefsManager = new MyAppPrefsManager(LoginActivity.this);
//        myAppPrefsManager.logInUser();

        // Set isLogged_in of ConstantValues
        //ConstantValues.IS_USER_LOGGED_IN = myAppPrefsManager.isUserLoggedIn();
        //StartAppRequests.RegisterDeviceForFCM(LoginActivity.this);
        // Navigate back to MainActivity
        Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(R.anim.enter_from_right, R.anim.exit_out_right);
    }

}