package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cabraltech.emaishaagentsapp.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button sendOtpBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();

        sendOtpBtn = findViewById(R.id.send_otp_button);
        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForgotPasswordActivity.this,OTPVerificationActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}