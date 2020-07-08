package com.arman.baking.network;

import com.arman.baking.model.Recipe;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RecipeService {

    @GET("baking.json")
    Single<List<Recipe>> fetchAll();
}
