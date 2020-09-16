package com.cabraltech.emaishaagentsapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cabraltech.emaishaagentsapp.R;

public class LoginActivity extends AppCompatActivity {
TextView createAccountTv, forgotPasswordTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        createAccountTv = findViewById(R.id.create_new_account_tv);
        forgotPasswordTv = findViewById(R.id.forgot_password_tv);

        createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                v.getContext().startActivity(intent);
            }
        });

        forgotPasswordTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                v.getContext().startActivity(intent);
            }
        });
    }
}