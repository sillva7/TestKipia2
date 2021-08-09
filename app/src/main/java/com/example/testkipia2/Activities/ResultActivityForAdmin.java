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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.testkipia2.R;
import com.example.testkipia2.adapters.ResultAdapter;
import com.example.testkipia2.classes.Answer;
import com.example.testkipia2.classes.Question;
import com.example.testkipia2.classes.Result;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResultActivityForAdmin extends AppCompatActivity {

    private RecyclerView recyclerViewResult;
    private ResultAdapter resultAdapter;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_for_admin);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        recyclerViewResult = findViewById(R.id.recyclerViewResult);
        resultAdapter = new ResultAdapter();

        takeResultsFromCloud();


        recyclerViewResult.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewResult.setAdapter(resultAdapter);


        FirebaseUser currentUser = mAuth.getCurrentUser();//проверяем зареган ли юзер
        if (currentUser == null) {
            Toast.makeText(this, getResources().getString(R.string.To_registration_page), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ResultActivityForAdmin.this, StartActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//метод для появления справа сверху стрелочки назад
        getMenuInflater().inflate(R.menu.sign_out_for_admin, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//метод для нажатия на пункт меню
        if (item.getItemId() == R.id.itemSignOut) {
            mAuth.signOut();
            Intent intent = new Intent(ResultActivityForAdmin.this, StartActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.itemMakeTest) {
            makeTestAndSendToCloud();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        takeResultsFromCloud();

    }

    @Override
    protected void onPause() {
        super.onPause();
        takeResultsFromCloud();
    }

    private void takeResultsFromCloud() {
        db.collection("Result").orderBy("time").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда список тестов
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Result> results = null;
                if (value != null) {
                    results = value.toObjects(Result.class);
                }
                if (results==null){
                    Toast.makeText(ResultActivityForAdmin.this, getResources().getString(R.string.wait_until_results_will_made), Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(ResultActivityForAdmin.this, "" + results.size(), Toast.LENGTH_SHORT).show();
                resultAdapter.setResults(results);
            }
        });
    }

//    private void signOut() {
//        AuthUI.getInstance()//pre-buildUI for registration
//                .signOut(this)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    public void onComplete(@NonNull Task<Void> task) {
//                        // ...
//                    }
//                });
//
//        // Choose authentication providers
//        List<AuthUI.IdpConfig> providers = Arrays.asList(//pre-buildUI for registration
//                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.GoogleBuilder().build());
//
//        // Create and launch sign-in intent
//        Intent signInIntent = AuthUI.getInstance()//pre-buildUI for registration
//                .createSignInIntentBuilder()
//                .setAvailableProviders(providers)
//                .build();
//        signInLauncher.launch(signInIntent);
//    }
//
//    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {//pre-buildUI for registration
//        IdpResponse response = result.getIdpResponse();
//        if (result.getResultCode() == RESULT_OK) {
//            // Successfully signed in
//            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();//надо будет из этого юзера достать емэил. Чтобы один эмеил был админским
//            if (user != null) {
//                Toast.makeText(this, "" + user.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//            // ...
//        } else {
//            // Sign in failed. If response is null the user canceled the
//            // sign-in flow using the back button. Otherwise check
//            // response.getError().getErrorCode() and handle the error.
//            // ...
//            if (response != null) {
//                Toast.makeText(this, "" + response.getError(), Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(//pre-buildUI for registration
//            new FirebaseAuthUIActivityResultContract(),
//            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
//                @Override
//                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
//                    onSignInResult(result);
//                }
//            }
//    );

    public void makeTestAndSendToCloud() {

        Test testKipia = makeTest();
        sendTestsToCloud(testKipia);

    }

    private Test makeTest() {//Создание теста
        Test testKipia = new Test();
        testKipia.setNameOfTest("General test");
        testKipia.setUniqueId(0);
        {
            Question question1 = new Question(1, "Напряжение в цепи питания преобразователся давления Метран-55:");
            List<Answer> answers1 = new ArrayList<>();
            answers1.add(new Answer(false, "12 B"));
            answers1.add(new Answer(false, "220 B"));
            answers1.add(new Answer(true, "24 B"));
            answers1.add(new Answer(false, "380 B"));
            question1.setAnswers(answers1);

            Question question2 = new Question(2, "Кол-во отводов в Автоматической Групповой Замерной Установке (АГЗУ) Спутникм40-10-400:");
            List<Answer> answers2 = new ArrayList<>();
            answers2.add(new Answer(false, "8"));
            answers2.add(new Answer(true, "10"));
            answers2.add(new Answer(false, "12"));
            answers2.add(new Answer(false, "14"));
            question2.setAnswers(answers2);

            Question question3 = new Question(3, "Скольки проводная схема используется для подключения СВУ ЭМИС-Вихрь-200:");
            List<Answer> answers3 = new ArrayList<>();
            answers3.add(new Answer(false, "2-x проводная"));
            answers3.add(new Answer(false, "3-x проводная"));
            answers3.add(new Answer(true, "4-x проводная"));
            answers3.add(new Answer(false, "8-x проводная"));
            question3.setAnswers(answers3);

            Question question4 = new Question(4, "Периодичность проведения ТО на системах автоматизации с уровнем технического обслуживания SL-2, составляет:");
            List<Answer> answers4 = new ArrayList<>();
            answers4.add(new Answer(false, "1 раз в месяц"));
            answers4.add(new Answer(true, "1 раз в два месяца"));
            answers4.add(new Answer(false, "1 раз в квартал"));
            answers4.add(new Answer(false, "1 раз в полгода"));
            question4.setAnswers(answers4);

            Question question5 = new Question(5, "Что из перечисленного не входит в состав системы автоматизации АГЗУ:");
            List<Answer> answers5 = new ArrayList<>();
            answers5.add(new Answer(false, "Преобразователь давления"));
            answers5.add(new Answer(true, "Манометр технический"));
            answers5.add(new Answer(false, "Гидропривод"));
            answers5.add(new Answer(false, "Регулятор расхода жидкости"));
            question5.setAnswers(answers5);

            Question question6 = new Question(6, "Время предоставления услуг оперативного обслуживания на системах автоматизации с уровнем оперативного обслуживания SL-1 составляет:");
            List<Answer> answers6 = new ArrayList<>();
            answers6.add(new Answer(false, "2 часа"));
            answers6.add(new Answer(true, "4 часа"));
            answers6.add(new Answer(false, "20 часов"));
            answers6.add(new Answer(false, "24 часа"));
            question6.setAnswers(answers6);

            Question question7 = new Question(7, "Какая периодичность проверки знаний по ОТ для рабочего персонала:");
            List<Answer> answers7 = new ArrayList<>();
            answers7.add(new Answer(false, "Не реже 1 раза в месяц"));
            answers7.add(new Answer(false, "Не реже 1 раза в два месяца"));
            answers7.add(new Answer(false, "Не реже 1 раза в квартал"));
            answers7.add(new Answer(true, "не реже 1 раза в год"));
            question7.setAnswers(answers7);

            Question question8 = new Question(8, "В каких электроустановках разрешается работать персоналу с 3 группа по электробезопасности:");
            List<Answer> answers8 = new ArrayList<>();
            answers8.add(new Answer(false, "до 1000 В"));
            answers8.add(new Answer(false, "до 220 В"));
            answers8.add(new Answer(false, "До 380 В"));
            answers8.add(new Answer(true, "Все вышеперечисленное"));
            question8.setAnswers(answers8);

            Question question9 = new Question(9, "После отсутствия на рабочем месте более 30 дней работник должен пройти:");
            List<Answer> answers9 = new ArrayList<>();
            answers9.add(new Answer(false, "Вводный инструктаж"));
            answers9.add(new Answer(false, "Повторный инструктаж"));
            answers9.add(new Answer(false, "Целевой инструктаж"));
            answers9.add(new Answer(true, "Внеплановый инструктаж"));
            question9.setAnswers(answers9);

            Question question10 = new Question(10, "Какое оборудование входит в состав системы контроля тока (напряжения):");
            List<Answer> answers10 = new ArrayList<>();
            answers10.add(new Answer(false, "СВУ ДРС-50"));
            answers10.add(new Answer(false, "ТОР-1-50"));
            answers10.add(new Answer(true, "Омь-3"));
            answers10.add(new Answer(false, "Метран-55"));
            question10.setAnswers(answers10);

            List<Question> questions = new ArrayList<>();
            questions.add(question1);
            questions.add(question2);
            questions.add(question3);
            questions.add(question4);
            questions.add(question5);
            questions.add(question6);
            questions.add(question7);
            questions.add(question8);
            questions.add(question9);
            questions.add(question10);

            testKipia.setQuestions(questions);

        }
        return testKipia;
    }

    private void sendTestsToCloud(Test test) {
        db.collection("tests").add(test).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {//добавляем тест
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(ResultActivityForAdmin.this, "Successfully added to cloud", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ResultActivityForAdmin.this, "Fail to add to cloud", Toast.LENGTH_LONG).show();
            }
        });
    }
}