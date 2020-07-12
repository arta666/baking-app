package com.arman.baking.network;

import android.arch.lifecycle.MutableLiveData;

import com.arman.baking.model.Recipe;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Single<List<Recipe>> fetchAll();
}
