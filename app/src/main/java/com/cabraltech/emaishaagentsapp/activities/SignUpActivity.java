package com.cabraltech.emaishaagentsapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cabraltech.emaishaagentsapp.R;
import com.cabraltech.emaishaagentsapp.models.authentication.RegistrationResponse;
import com.cabraltech.emaishaagentsapp.network.APIClient;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private EditText firstName, lastName, phoneNumber, email, password, confirmPassword, nextOfKin, nextOfKinRelation, nextOfKinContact, nin;
    private AutoCompleteTextView district, subCounty, village;
    private Button submitBtn;

    private ProgressDialog progressDialog;

    // image picker code
    private static final int IMAGE_PICK_CODE = 0;
    //permission code
    private static final int PERMISSION_CODE = 1;

    private String agentPhoto = null;
    private String nationalIdPhoto = null;
    private ImageButton userPhotoView;
    private ImageButton userIdPhotoView;
    private ImageButton selectedPhotoView;

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
        userPhotoView = findViewById(R.id.upload_photo_img);
        userIdPhotoView = findViewById(R.id.upload_nid_img);

        submitBtn = findViewById(R.id.submit_button);

        // Initialize ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(getString(R.string.processing));
        progressDialog.setMessage("Signing you up...");
        progressDialog.setCancelable(false);

        userPhotoView.setOnClickListener(v -> {
            selectedPhotoView = userPhotoView;
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission granted
                    chooseImage();
                }
            } else {
                //version is less than marshmallow
                chooseImage();
            }
        });

        userIdPhotoView.setOnClickListener(v -> {
            selectedPhotoView = userIdPhotoView;
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    //permission denied
                    String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                } else {
                    //permission granted
                    chooseImage();
                }
            } else {
                //version is less than marshmallow
                chooseImage();
            }
        });

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
            } else if (agentPhoto == null) {
                Snackbar.make(findViewById(android.R.id.content), "Picture is empty", Snackbar.LENGTH_SHORT).show();
            } else if (nationalIdPhoto == null) {
                Snackbar.make(findViewById(android.R.id.content), "National ID picture is empty", Snackbar.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                processRegistration();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                chooseImage();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            Uri uri = null;

            assert data != null;
            if (data.getData() != null) {
                uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] b = byteArrayOutputStream.toByteArray();

                    if (selectedPhotoView == userPhotoView) {
                        agentPhoto = Base64.encodeToString(b, Base64.DEFAULT);
                        Log.d(TAG, "onActivityResult: Base64 = " + agentPhoto);
                    } else if (selectedPhotoView == userIdPhotoView) {
                        nationalIdPhoto = Base64.encodeToString(b, Base64.DEFAULT);
                        Log.d(TAG, "onActivityResult: Base64 = " + nationalIdPhoto);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (uri != null)
                Glide.with(this).load(uri).into(selectedPhotoView);
        }

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