package com.example.testkipia2.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.testkipia2.R;
import com.example.testkipia2.adapters.QuestionAdapter;
import com.example.testkipia2.classes.Answer;
import com.example.testkipia2.classes.Result;
import com.example.testkipia2.classes.Test;
import com.example.testkipia2.utilities.MapOfAnswers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InTestActivity extends AppCompatActivity {

    private RecyclerView recyclerViewOfTest;
    private Test test;
    private FirebaseFirestore db;
    private int idOfTest;
    private QuestionAdapter questionAdapter;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_test);

        Intent intent = getIntent();
        recyclerViewOfTest = findViewById(R.id.recyclerViewOfTest);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        idOfTest = intent.getIntExtra("idOfTest", 0);
        questionAdapter = new QuestionAdapter();


        getTest();
        Log.d("123123", "onCreate: " + idOfTest);//проверка что получили

        recyclerViewOfTest.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOfTest.setAdapter(questionAdapter);


        recyclerViewOfTest.getRecycledViewPool().setMaxRecycledViews(0,11);//эти 2 строчки, чтобы ресайклервью не переробатывал прошедшие вопросы
        recyclerViewOfTest.setItemViewCacheSize(11);


    }

    private void getTest() {

        db.collection("tests").orderBy("uniqueId").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда список тестов
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                List<Test> tests = null;
                if (value != null) {
                    tests = value.toObjects(Test.class);
                }
                Log.d("123123", "onEvent: " + idOfTest);
                test = tests.get(idOfTest);
                questionAdapter.setQuestionList(test.getQuestions());
                Log.d("123123", "onEvent22: " + tests.get(idOfTest).getQuestions());


            }
        });

    }

    public void seeResult(View view) {

        Map<String, String> correctAnswersMap = new HashMap<>();
        for (int i = 0; i < test.getQuestions().size(); i++) {//наполнение словаря с правильными ответами
            for (Answer a : test.getQuestions().get(i).getAnswers()) {
                if (a.isCorrect()) {
                    correctAnswersMap.put(test.getQuestions().get(i).getTextOfQuestion(), a.getTextOfAnswer());
                }
            }
        }

        int countOfCorrectAnswers = 0;//счетчик правильных ответов
        for (String valueOfCorrectMap : correctAnswersMap.values()) {//сравнение словаря с правильными ответами и словаря с получившимися ответами и подсчёт правильных ответов
            Log.d("123123", "onEvent999111: " + valueOfCorrectMap);
            for (String valueOfResultMap : MapOfAnswers.answers.values()) {
                Log.d("123123", "onEvent999: " + valueOfResultMap);

                if (valueOfCorrectMap.equals(valueOfResultMap)) {
                    countOfCorrectAnswers++;
                }
            }
        }
        Log.d("123123", "onEvent44: " + countOfCorrectAnswers);

        Intent intent = new Intent(InTestActivity.this, ResultActivityForUser.class);//переход в другое активити и перенос результата туда
        intent.putExtra("countOfAnswers", countOfCorrectAnswers);
        startActivity(intent);
        MapOfAnswers.answers.clear();

        Result result = new Result(mAuth.getCurrentUser().getDisplayName(), countOfCorrectAnswers,System.currentTimeMillis());//создание объекта Результат
        Log.d("123123", "seeResult: " + result.getNameOfTester());

        db.collection("Result").add(result).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {//добавляем результат в клауд
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(InTestActivity.this, "Reult sended to cloud database", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InTestActivity.this, "Reult not sended to cloud database", Toast.LENGTH_LONG).show();

            }
        });


    }
}