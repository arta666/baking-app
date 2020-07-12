package com.arman.baking.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.arman.baking.app.Constant;
import com.arman.baking.model.Ingredient;
import com.arman.baking.model.Recipe;
import com.arman.baking.model.Step;

@Database(entities = {Recipe.class, Step.class, Ingredient.class}, version = 1, exportSchema = false)
@TypeConverters({IngredientConverters.class, StepConverters.class})
public abstract class AppDb  extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract StepDao stepDao();
    public abstract IngredientDao ingredientDao();

    private static AppDb  INSTANCE = null;

    public static AppDb  getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    AppDb .class, Constant.DB_NAME).allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }
}