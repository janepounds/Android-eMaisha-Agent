package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.models.authentication.LoginResponse;
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

        nextBtn.setOnClickListener(v -> {
            progressDialog.show();
            processLogin();
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
                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent);
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
}