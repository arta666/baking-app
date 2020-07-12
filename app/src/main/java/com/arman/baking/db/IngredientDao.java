package com.arman.baking.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.arman.baking.model.Ingredient;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    List<Ingredient> getIngredients();

    @Insert(onConflict = REPLACE)
    void insertAll(List<Ingredient> ingredients);

    @Query("DELETE FROM ingredient")
    void deleteAll();
}