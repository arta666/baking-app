package com.arman.baking.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.VisibleForTesting;

import com.arman.baking.model.Recipe;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM recipe")
    LiveData<List<Recipe>> getRecipesLiveData();

    @VisibleForTesting
    @Query("SELECT * FROM recipe")
    List<Recipe> getRecipes();

    @Query("SELECT ingredients FROM recipe WHERE id=:id")
    LiveData<String> getIngredients(int id);

    @Query("SELECT steps FROM recipe WHERE id=:id")
    LiveData<String> getSteps(int id);

    @Insert(onConflict = REPLACE)
    void insert(Recipe recipe);

    @Insert(onConflict = REPLACE)
    void insertAll(List<Recipe> recipes);

    @Query("DELETE FROM recipe")
    void deleteAll();
}