package com.cabraltech.emaishaagentsapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
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
import com.cabraltech.emaishaagentsapp.utils.CheckPermissions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

    // Verification id that will be sent to the user
    private String mVerificationId;

    // Firebase auth object
    private FirebaseAuth mAuth;

    //Custom Dialog Vies
    private Dialog dialogOTP;
    private EditText ed_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

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
        progressDialog.setMessage("Signing you up ...");
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
            String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$*";
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
            } else if (!email.getText().toString().trim().matches(regex)) {
                email.setError(getString(R.string.enter_valid_email));
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
                sendVerificationCode(getResources().getString(R.string.ugandan_code) + phoneNumber.getText().toString().trim());
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
        progressDialog.show();
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
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    if (response.code() == 400) {
                        Snackbar.make(findViewById(android.R.id.content), "User already exists", Snackbar.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<RegistrationResponse> call, @NotNull Throwable t) {
                progressDialog.dismiss();
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    /// Custom dialog for OTP
    public void showOTPDialog(Activity activity, String msg) {
        dialogOTP = new Dialog(activity);
        dialogOTP.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogOTP.setCancelable(false);
        dialogOTP.setContentView(R.layout.dialog_otp);

        ed_otp = dialogOTP.findViewById(R.id.ed_otp);
        AppCompatButton btn_resend, btn_submit;
        btn_resend = dialogOTP.findViewById(R.id.btn_resend);
        btn_submit = dialogOTP.findViewById(R.id.btn_submit);

        btn_resend.setOnClickListener(view -> sendVerificationCode(phoneNumber.getText().toString().trim()));

        btn_submit.setOnClickListener(view -> {
            if (!ed_otp.getText().toString().trim().isEmpty()) {
                verifyVerificationCode(ed_otp.getText().toString().trim());
            } else {
                ed_otp.setError("Required");
            }
        });

        dialogOTP.show();
    }

    //the method is sending verification code
    //the country id is concatenated
    //you can take the country id as user input as well
    private void sendVerificationCode(String mobile) {

        showOTPDialog(SignUpActivity.this, "");

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)                        // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS)      // Timeout and unit
                        .setActivity(this)                             // Activity (for callback binding)
                        .setCallbacks(mCallbacks)                      // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

//        PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                mobile,
//                60,
//                TimeUnit.SECONDS,
//                this,
//                mCallbacks);
    }

    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            Log.d(TAG, "onVerificationCompleted: Code : " + code);

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                ed_otp.setText(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(SignUpActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            dialogOTP.dismiss();
        }

        @Override
        public void onCodeSent(@NotNull String s, @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(SignUpActivity.this, task -> {
                    if (task.isSuccessful()) {
                        //verification successful we will start the profile activity
                        dialogOTP.dismiss();

                        Log.d(TAG, "onVerificationCompleted: Verification Completed");
                        //Final Registration Call to API
                        processRegistration();
                    } else {
                        //verification unsuccessful.. display an error message
                        String message = "Somthing is wrong, we will fix it soon...";
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            message = "Invalid code entered...";
                        }
                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
                        snackbar.setAction("Dismiss", v -> snackbar.dismiss());
                        snackbar.show();
                    }
                });
    }
}