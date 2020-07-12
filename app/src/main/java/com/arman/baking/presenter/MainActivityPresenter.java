package com.arman.baking.presenter;

import android.arch.lifecycle.LiveData;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.arman.baking.db.AppDb;
import com.arman.baking.db.RecipeDao;
import com.arman.baking.model.Recipe;
import com.arman.baking.network.RecipeService;
import com.arman.baking.network.ServiceBuilder;
import com.arman.baking.view.RecipeView;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {


    private RecipeView view;

    private Scheduler mainScheduler;

    private CompositeDisposable disposable = new CompositeDisposable();

    private RecipeService recipeService ;

    private AppDb db;


    public MainActivityPresenter(RecipeView view, Scheduler mainScheduler, AppDb db) {
        this.view = view;
        this.mainScheduler = mainScheduler;
        this.db = db;
        this.recipeService = ServiceBuilder.buildService(RecipeService.class);
    }

    public void fetchAllRecipes(){
        disposable.add(
                recipeService.fetchAll()
                        .subscribeOn(Schedulers.io())
                        .observeOn(mainScheduler)
                        .subscribeWith(new DisposableSingleObserver<List<Recipe>>() {
                            @Override
                            public void onSuccess(List<Recipe> recipeList) {
                               addToDB(recipeList);
                            }

                            @Override
                            public void onError(Throwable e) {
                               view.onFailure(e.getMessage());
                            }
                        }));

    }


    private void addToDB(List<Recipe> recipeList){
        RecipeDao dao = db.recipeDao();
        for (Recipe recipe:recipeList) {
            dao.insert(recipe);
        }
    }


}
