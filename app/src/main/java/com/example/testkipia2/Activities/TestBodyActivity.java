package com.example.testkipia2.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testkipia2.R;
import com.example.testkipia2.adapters.TestAdapter;
import com.example.testkipia2.classes.Test;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class TestBodyActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTest;


    private TestAdapter testAdapter;

    //    private List<Message> messages;
    private String emailOfUser;


    private FirebaseFirestore db;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_body);


        testAdapter = new TestAdapter();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        takeTestFromCloud();

        recyclerViewTest = findViewById(R.id.recyclerViewTest);
        recyclerViewTest.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTest.setAdapter(testAdapter);


        FirebaseUser currentUser = mAuth.getCurrentUser();//проверяем зареган ли юзер
        if (currentUser == null) {
            Toast.makeText(this, getResources().getString(R.string.To_registration_page), Toast.LENGTH_LONG).show();
            signOut();
        }


        testAdapter.setOnTestClickListener(new TestAdapter.OnTestClickListener() {
            @Override
            public void onTestClick(int position) {
                Intent intent = new Intent(TestBodyActivity.this, InTestActivity.class);
                intent.putExtra("idOfTest", testAdapter.getTestList().get(position).getUniqueId());
                Log.d("123123", "onTestClick: " + testAdapter.getTestList().get(position).getUniqueId());
                startActivity(intent);
            }
        });


    }

    private void sendTestsToCloud(Test test) {
        db.collection("tests").add(test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {//добавляем тест
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(TestBodyActivity.this, "Successfully added to cloud", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(TestBodyActivity.this, "Fail to add to cloud", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//метод для появления справа сверху стрелочки назад
        getMenuInflater().inflate(R.menu.sign_out, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//метод для нажатия на пункт меню
        if (item.getItemId() == R.id.itemSignOut) {
            mAuth.signOut();
            Intent intent = new Intent(TestBodyActivity.this, StartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


    private void signOut() {
        AuthUI.getInstance()//pre-buildUI for registration
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(//pre-buildUI for registration
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()//pre-buildUI for registration
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();
        signInLauncher.launch(signInIntent);


    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(//pre-buildUI for registration
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {//pre-buildUI for registration
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//надо будет из этого юзера достать емэил. Чтобы один эмеил был админским
            if (user != null) {
                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
                emailOfUser = user.getEmail();
            }
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
            if (response != null) {
                Toast.makeText(this, "" + response.getError(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        takeTestFromCloud();

    }

    @Override
    protected void onPause() {
        super.onPause();
        takeTestFromCloud();
    }

    private void takeTestFromCloud() {
        db.collection("tests").orderBy("uniqueId").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда сообщения
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Test> tests = null;
                if (value != null) {
                    tests = value.toObjects(Test.class);
                }
                testAdapter.setTestList(tests);
            }
        });
    }


}