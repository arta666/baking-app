package com.arman.baking.ui.activity;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.arman.baking.databinding.ActivityMainBinding;
import com.arman.baking.listeners.RecipeItemListener;
import com.arman.baking.model.Recipe;
import com.arman.baking.presenter.MainActivityPresenter;
import com.arman.baking.ui.adapter.RecipeAdapter;
import com.arman.baking.view.RecipeView;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity implements RecipeView,RecipeItemListener {

    private ActivityMainBinding mainBinding;
    private RecipeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        initView();

        MainActivityPresenter presenter = new MainActivityPresenter(this, AndroidSchedulers.mainThread());

        presenter.fetchAllRecipes();

    }

    private void initView(){

        mAdapter = new RecipeAdapter(this);
        mAdapter.setItemListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mainBinding.recycler.setLayoutManager(layoutManager);
        mainBinding.recycler.setHasFixedSize(true);
        mainBinding.recycler.setAdapter(mAdapter);

    }

    @Override
    public void onClickListener(Recipe recipe) {
        if(recipe !=null){
            Toast.makeText(this, recipe.getName(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDisplayRecipes(List<Recipe> recipeList) {
        if(recipeList !=null){
            mAdapter.setRecipeList(recipeList);
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}