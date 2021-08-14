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

public class LoginActivity extends AppCompatActivity {

    private EditText editTextLoginLogin, editTextLoginPassword;
    private Button buttonLogin;
    private TextView textViewToRegistration;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextLoginLogin = findViewById(R.id.editTextLoginLogin);
        editTextLoginPassword = findViewById(R.id.editTextLoginPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
//        textViewToRegistration = findViewById(R.id.textViewToRegistration);

        mAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(new View.OnClickListener() {//кнопка логина
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
//        textViewToRegistration.setOnClickListener(new View.OnClickListener() {//текстовое поле при нажатии на которое переходим на страницу регистрации
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
//                startActivity(intent);
//            }
//        });

    }

    private void signIn(){
        String email = editTextLoginLogin.getText().toString().trim();
        String password = editTextLoginPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent;
                            if (email.equals("twinborder@gmail.com")) {
                                intent = new Intent(LoginActivity.this, ResultActivityForAdmin.class);
                            } else {
                                intent = new Intent(LoginActivity.this, TestBodyActivity.class);
                            }
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.Error), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}