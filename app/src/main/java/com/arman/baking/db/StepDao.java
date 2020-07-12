package com.arman.baking.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.arman.baking.model.Step;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepDao {
    @Query("SELECT * FROM step WHERE id=:id")
    Step getStep(int id);

    @Query("SELECT * FROM step WHERE id=:id")
    LiveData<Step> getLiveStep(int id);

    @Query("SELECT COUNT(*) FROM step")
    int getCount();

    @Insert(onConflict = REPLACE)
    void insertAll(List<Step> steps);

    @Query("DELETE FROM step")
    void deleteAll();
}