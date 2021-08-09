package com.example.testkipia2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testkipia2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextRegistrationLogin, editTextRegistrationPassword;
    private Button buttonRegistration;
    private TextView textViewToLogin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextRegistrationLogin = findViewById(R.id.editTextRegistrationLogin);
        editTextRegistrationPassword = findViewById(R.id.editTextRegistrationPassword);
        buttonRegistration = findViewById(R.id.buttonRegistration);
        textViewToLogin = findViewById(R.id.textViewToLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {//регистрация нового пользователя
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        textViewToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }

    private void signUp() {
        String email = editTextRegistrationLogin.getText().toString().trim();
        String password = editTextRegistrationPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.Registered), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationActivity.this, TestBodyActivity.class);
                            startActivity(intent);

                        } else {
                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.Error)+ ": " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}