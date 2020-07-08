package com.arman.baking.presenter;

import com.arman.baking.model.Recipe;
import com.arman.baking.network.RecipeService;
import com.arman.baking.network.ServiceBuilder;
import com.arman.baking.view.RecipeView;

import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter {


    private RecipeView view;

    private Scheduler mainScheduler;

    private CompositeDisposable disposable = new CompositeDisposable();

    private RecipeService recipeService ;


    public MainActivityPresenter(RecipeView view,Scheduler mainScheduler) {
        this.view = view;
        this.mainScheduler = mainScheduler;
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
                                view.onDisplayRecipes(recipeList);
                            }

                            @Override
                            public void onError(Throwable e) {
                               view.onFailure(e.getMessage());
                            }
                        }));

    }


}
