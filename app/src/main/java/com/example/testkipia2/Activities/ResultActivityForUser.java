package com.example.testkipia2.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.testkipia2.R;
import com.example.testkipia2.classes.Test;
import com.example.testkipia2.utilities.MapOfAnswers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class ResultActivityForUser extends AppCompatActivity {

    private TextView numberOfCorrectAnswers;
    private Test test;
    private FirebaseFirestore db;
    private int countOfAnswers;
    private Intent intent;
    private FirebaseAuth mAuth;
    private TextView textViewResultPass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_for_user);
        db = FirebaseFirestore.getInstance();
        intent = getIntent();
        countOfAnswers = intent.getIntExtra("countOfAnswers", 0);
        Log.d("123123", "onCreate456: " + MapOfAnswers.answers.toString());
        numberOfCorrectAnswers = findViewById(R.id.numberOfCorrectAnswers);
        numberOfCorrectAnswers.setText(countOfAnswers + "/10");
        mAuth = FirebaseAuth.getInstance();
        textViewResultPass = findViewById(R.id.textViewResultPass);

        showStatusOfResult();







//        Log.d("123123", "onCreate11: " + test.getQuestions().toString());
//        Map<String, String> correctAnswersMap = new HashMap<>();
//        for (int i = 0; i < test.getQuestions().size(); i++) {
//            for (Answer a : test.getQuestions().get(i).getAnswers()) {
//                if (a.isCorrect()) {
//                    correctAnswersMap.put(test.getQuestions().get(i).getTextOfQuestion(), a.getTextOfAnswer());
//                }
//            }
//        }
//        Log.d("123123", "onCreate: " + correctAnswersMap.toString());

    }

    private void showStatusOfResult() {
        if(countOfAnswers>=9){
            textViewResultPass.setText(R.string.passed);
            textViewResultPass.setTextColor(0xFF4CAF50);
        }else{
            textViewResultPass.setText(R.string.failed);
            textViewResultPass.setTextColor(0xFFff3829);
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        db.collection("test1").orderBy("uniqueId").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда список тестов
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Test> tests = null;
//                if (value != null) {
//                    tests = value.toObjects(Test.class);
//                }
//                test = tests.get(idOfTest);
//                Log.d("123123", "onEvent33: " + tests.get(idOfTest).getQuestions().toString());
//                //test.setQuestions(tests.get(idOfTest).getQuestions());
//            }
//        });
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        db.collection("test1").orderBy("uniqueId").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда список тестов
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Test> tests = null;
//                if (value != null) {
//                    tests = value.toObjects(Test.class);
//                }
//                test = tests.get(idOfTest);
//                Log.d("123123", "onEvent33: " + tests.get(idOfTest).getQuestions().toString());
//                //test.setQuestions(tests.get(idOfTest).getQuestions());
//            }
//        });
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//метод для появления справа сверху стрелочки назад
        getMenuInflater().inflate(R.menu.sign_out, menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {//метод для нажатия на пункт меню
        if (item.getItemId() == R.id.itemSignOut) {
            mAuth.signOut();
            Intent intent = new Intent(ResultActivityForUser.this, StartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTest() {

//        db.collection("test1").orderBy("uniqueId").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    List<Test> testList = new ArrayList<>();
//                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
//
//                        Log.d("123123", "onComplete: "+documentSnapshot.getId());
//                        testList.add(documentSnapshot.toObject(Test.class));
//                        Log.d("123123", "onComplete555: " + testList.toString());
//
//                    }
//                    test=testList.get(idOfTest);
//
//                }
//            }
//        });


//        db.collection("test1").orderBy("uniqueId").addSnapshotListener(new EventListener<QuerySnapshot>() {//получение из клауда список тестов
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                List<Test> tests = null;
//                if (value != null) {
//                    tests = value.toObjects(Test.class);
//                }
//
//                test = tests.get(idOfTest);
//                Map<String, String> correctAnswersMap = new HashMap<>();
//                for (int i = 0; i < test.getQuestions().size(); i++) {
//                    for (Answer a : test.getQuestions().get(i).getAnswers()) {
//                        if (a.isCorrect()) {
//                            correctAnswersMap.put(test.getQuestions().get(i).getTextOfQuestion(), a.getTextOfAnswer());
//                        }
//                    }
//                }
//                Log.d("123123", "onEvent456: "+correctAnswersMap.toString());
//                //сравнение 2-ух словарей
//                    int countOfCorrectAnswers = 0;
//                    for (String valueOfCorrectMap : correctAnswersMap.values()) {
//                        Log.d("123123", "onEvent999111: " + valueOfCorrectMap);
//                        for (String valueOfResultMap : MapOfAnswers.answers.values()) {
//                            Log.d("123123", "onEvent999: " + valueOfResultMap);
//
//                            if(valueOfCorrectMap==valueOfResultMap){
//                                countOfCorrectAnswers++;
//                            }
//                        }
//                    }
//                    Log.d("123123", "onEvent44: "+countOfCorrectAnswers);
//
//                numberOfCorrectAnswers.setText(countOfCorrectAnswers+"");
//
//
//
////                Log.d("123123", "onCreate3333: " + correctAnswersMap.toString());
//                Log.d("123123", "onEvent33: " + tests.get(idOfTest).getQuestions().toString());
//
//                //test.setQuestions(tests.get(idOfTest).getQuestions());
//            }
//        });

    }
}