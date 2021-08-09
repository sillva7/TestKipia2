package com.example.testkipia2.classes;

import java.util.Date;

public class Result {
    private String nameOfTester;
    private int result;
    private long time;

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNameOfTester() {
        return nameOfTester;
    }

    public void setNameOfTester(String nameOfTester) {
        this.nameOfTester = nameOfTester;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public Result(String nameOfTester, int result, long time) {
        this.nameOfTester = nameOfTester;
        this.result = result;
        this.time = time;
    }

    public Result() {
    }
}
