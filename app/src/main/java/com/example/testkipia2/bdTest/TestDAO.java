package com.example.testkipia2.bdTest;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.testkipia2.classes.Test;

import java.util.List;

@Dao//for local DB
public interface TestDAO {

    @Query("SELECT * FROM tests")
    LiveData<List<Test>> getAllTests();

    @Query("SELECT * FROM tests WHERE uniqueId == :uniqueId")
    Test getTestById(int uniqueId);

    @Query("SELECT * FROM tests WHERE nameOfTest == :nameOfTest")
    Test getTestByName(String nameOfTest);

    @Query("DELETE FROM tests")
    void deleteAllTest();

    @Insert
    void insertTest(Test test);

    @Delete
    void deleteTest(Test test);



}
