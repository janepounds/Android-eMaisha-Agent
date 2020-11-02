package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.models.authentication.RegistrationResponse;
import com.cabraltech.emaishaagentsapp.network.APIClient;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private EditText firstName, lastName, phoneNumber, email, password, confirmPassword, nextOfKin, nextOfKinRelation, nextOfKinContact, nin;
    private AutoCompleteTextView district, subCounty, village;
    private Button submitBtn;

    private String agentPhoto = "agentPhoto";
    private String nationalIdPhoto = "nationalIdPhoto";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        firstName = findViewById(R.id.first_name_et);
        lastName = findViewById(R.id.last_name_et);
        district = findViewById(R.id.district_spinner);
        subCounty = findViewById(R.id.sub_county_spinner);
        village = findViewById(R.id.village_spinner);
        phoneNumber = findViewById(R.id.phone_number_et);
        email = findViewById(R.id.email_address_et);
        password = findViewById(R.id.password_et);
        confirmPassword = findViewById(R.id.confirm_password_et);
        nextOfKin = findViewById(R.id.next_of_kin_et);
        nextOfKinRelation = findViewById(R.id.next_of_kin_relation_et);
        nextOfKinContact = findViewById(R.id.next_of_kin_contact_et);
        nin = findViewById(R.id.nin_et);

        submitBtn = findViewById(R.id.submit_button);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.processing));
        progressDialog.setMessage("Signing you up...");
        progressDialog.setCancelable(false);

        submitBtn.setOnClickListener(v -> {
            if (firstName.getText().toString().trim().isEmpty()) {
                firstName.setError("Required");
            } else if (lastName.getText().toString().trim().isEmpty()) {
                lastName.setError("Required");
            } else if (district.getText().toString().trim().isEmpty()) {
                district.setError("Required");
            } else if (subCounty.getText().toString().trim().isEmpty()) {
                subCounty.setError("Required");
            } else if (village.getText().toString().trim().isEmpty()) {
                village.setError("Required");
            } else if (phoneNumber.getText().toString().trim().isEmpty()) {
                phoneNumber.setError("Required");
            } else if (email.getText().toString().trim().isEmpty()) {
                email.setError("Required");
            } else if (password.getText().toString().trim().isEmpty()) {
                password.setError("Required");
            } else if (confirmPassword.getText().toString().trim().isEmpty()) {
                confirmPassword.setError("Required");
            } else if (!confirmPassword.getText().toString().equals(password.getText().toString())) {
                password.setError("Passwords do not match");
                confirmPassword.setError("Passwords do not match");
            } else if (nextOfKin.getText().toString().trim().isEmpty()) {
                nextOfKin.setError("Required");
            } else if (nextOfKinRelation.getText().toString().trim().isEmpty()) {
                nextOfKinRelation.setError("Required");
            } else if (nextOfKinContact.getText().toString().trim().isEmpty()) {
                nextOfKinContact.setError("Required");
            } else if (nin.getText().toString().trim().isEmpty()) {
                nin.setError("Required");
            } else {
                progressDialog.show();
                processRegistration();
            }
        });
    }

    private void processRegistration() {
        Call<RegistrationResponse> call = APIClient.getInstance().register(firstName.getText().toString(), lastName.getText().toString(),
                district.getText().toString(), subCounty.getText().toString(), village.getText().toString(),
                phoneNumber.getText().toString(), email.getText().toString(), password.getText().toString(), nextOfKin.getText().toString(),
                nextOfKinRelation.getText().toString(), nextOfKinContact.getText().toString(), nin.getText().toString(), agentPhoto, nationalIdPhoto);

        call.enqueue(new Callback<RegistrationResponse>() {
            @Override
            public void onResponse(@NotNull Call<RegistrationResponse> call, @NotNull Response<RegistrationResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Log.d(TAG, "onResponse: " + response.body().getStatus());
                    Log.d(TAG, "onResponse: " + response.body().getData().getEmail());
                    Intent intent = new Intent(SignUpActivity.this, OTPVerificationActivity.class);
                    startActivity(intent);
                } else {
                    Log.d(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}