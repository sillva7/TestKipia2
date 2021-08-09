package com.example.testkipia2.classes;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private int idOfQuestion;
    private String textOfQuestion;
    private List<Answer> answers = new ArrayList<>();

    public int getIdOfQuestion() {
        return idOfQuestion;
    }

    public void setIdOfQuestion(int idOfQuestion) {
        this.idOfQuestion = idOfQuestion;
    }

    public String getTextOfQuestion() {
        return textOfQuestion;
    }

    public void setTextOfQuestion(String textOfQuestion) {
        this.textOfQuestion = textOfQuestion;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Question(int idOfQuestion, String textOfQuestion, List<Answer> answers) {
        this.idOfQuestion = idOfQuestion;
        this.textOfQuestion = textOfQuestion;
        this.answers = answers;
    }

    public Question(int idOfQuestion, String textOfQuestion) {
        this.idOfQuestion = idOfQuestion;
        this.textOfQuestion = textOfQuestion;
    }

    public Question() {
    }
}
