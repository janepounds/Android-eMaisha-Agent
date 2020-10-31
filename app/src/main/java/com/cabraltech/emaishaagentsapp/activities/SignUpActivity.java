package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cabraltech.emaishaagentsapp.R;

public class SignUpActivity extends AppCompatActivity {

    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        submitBtn = findViewById(R.id.submit_button);

        submitBtn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this,OTPVerificationActivity.class);
            v.getContext().startActivity(intent);
        });
    }
}