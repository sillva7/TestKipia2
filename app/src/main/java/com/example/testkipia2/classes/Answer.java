package com.example.testkipia2.classes;

public class Answer {

    private boolean correct;
    private String textOfAnswer;



    public Answer() {
    }

    public Answer(boolean correct, String textOfAnswer) {
        this.correct = correct;
        this.textOfAnswer = textOfAnswer;
    }


    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getTextOfAnswer() {
        return textOfAnswer;
    }

    public void setTextOfAnswer(String textOfAnswer) {
        this.textOfAnswer = textOfAnswer;
    }
}
