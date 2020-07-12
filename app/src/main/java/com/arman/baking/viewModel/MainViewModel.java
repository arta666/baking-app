package com.arman.baking.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.arman.baking.db.AppDb;
import com.arman.baking.model.Recipe;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public AppDb db;
    private LiveData<List<Recipe>> recipes;

    public MainViewModel(@NonNull Application application) {
        super(application);
        db = AppDb.getDatabase(this.getApplication());
        recipes = db.recipeDao().getRecipesLiveData();
    }


    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

}
