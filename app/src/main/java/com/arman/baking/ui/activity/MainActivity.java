package com.arman.baking.ui.activity;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.arman.baking.databinding.ActivityMainBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.ui.adapter.RecipeAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        initView();

        addSample();
    }

    private void initView(){

        mAdapter = new RecipeAdapter(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recycler.setLayoutManager(layoutManager);
        mainBinding.recycler.setHasFixedSize(true);
        mainBinding.recycler.setAdapter(mAdapter);

    }

    private void addSample(){
        List<Recipe> recipeList = new ArrayList<>();

        for (int i = 1; i < 10 ; i++) {
            Recipe recipe = new Recipe();
            recipe.setName("Food " + i);
            recipeList.add(recipe);
        }
        mAdapter.setRecipeList(recipeList);

    }
}