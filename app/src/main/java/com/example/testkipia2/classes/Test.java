package com.example.testkipia2.classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;


@Entity(tableName = "tests")//for local DB
public class Test {

    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private String nameOfTest;
    private List<Question> questions = new ArrayList<>();

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getNameOfTest() {
        return nameOfTest;
    }

    public void setNameOfTest(String nameOfTest) {
        this.nameOfTest = nameOfTest;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;

    }

    @Ignore
    public Test() {
    }

    public Test(int uniqueId, String nameOfTest, List<Question> questions) {
        this.uniqueId = uniqueId;
        this.nameOfTest = nameOfTest;
        this.questions = questions;
    }

    private String showAllQuestions(){//show all questions with their id
        StringBuilder allQuestions = new StringBuilder();
        for(int i = 0; i<questions.size();i++){
            allQuestions.append(questions.get(i).getIdOfQuestion()).append(": ").append(questions.get(i).getTextOfQuestion()).append("\n");
        }
        return allQuestions.toString();

    }

    private void addQuestion(Question question){//add question
        questions.add(question);
    }

    private void deleteQuestionById(int id){//delete Question by id

        for(Question q: questions){
            if(q.getIdOfQuestion()==id){
                questions.remove(q.getIdOfQuestion());
            }
        }

    }


}
