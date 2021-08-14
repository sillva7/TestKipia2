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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegistrationActivity extends AppCompatActivity {

    private EditText editTextRegistrationLogin, editTextRegistrationPassword, editTextRegistration1stName, editTextRegistration2ndName, editTextRegistration3rdName;
    private Button buttonRegistration;
    private TextView textViewToLogin;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextRegistrationLogin = findViewById(R.id.editTextRegistrationLogin);
        editTextRegistrationPassword = findViewById(R.id.editTextRegistrationPassword);
        editTextRegistration1stName = findViewById(R.id.editTextRegistration1stName);
        editTextRegistration2ndName = findViewById(R.id.editTextRegistration2ndName);
        editTextRegistration3rdName = findViewById(R.id.editTextRegistration3rdName);
        buttonRegistration = findViewById(R.id.buttonRegistration);
//        textViewToLogin = findViewById(R.id.textViewToLogin);

        mAuth = FirebaseAuth.getInstance();

        buttonRegistration.setOnClickListener(new View.OnClickListener() {//регистрация нового пользователя
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


//        textViewToLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
//                startActivity(intent);
//
//            }
//        });


    }

    private void signUp() {
        String email = editTextRegistrationLogin.getText().toString().trim();
        String password = editTextRegistrationPassword.getText().toString().trim();
        String name = editTextRegistration1stName.getText().toString().trim();
        String surname = editTextRegistration2ndName.getText().toString().trim();
        String thirdName = editTextRegistration3rdName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty() || surname.isEmpty()) {
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.Registered), Toast.LENGTH_SHORT).show();
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name + " " + thirdName + " " + surname)
                                    .build();
                            mAuth.getCurrentUser().updateProfile(profileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegistrationActivity.this, "ADDED PROFILE INFO", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegistrationActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            Intent intent;
                            if (email.equals("twinborder@gmail.com")) {
                                intent = new Intent(RegistrationActivity.this, ResultActivityForAdmin.class);
                            } else {
                                intent = new Intent(RegistrationActivity.this, TestBodyActivity.class);
                            }
                            startActivity(intent);


                        } else {
                            Toast.makeText(RegistrationActivity.this, getResources().getString(R.string.Error) + ": " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}