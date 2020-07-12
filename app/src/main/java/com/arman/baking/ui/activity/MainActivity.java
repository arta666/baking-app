package com.arman.baking.ui.activity;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.arman.baking.R;
import com.arman.baking.app.Constant;
import com.arman.baking.databinding.ActivityMainBinding;
import com.arman.baking.db.AppDb;
import com.arman.baking.viewModel.MainViewModel;
import com.arman.baking.listeners.RecipeItemListener;
import com.arman.baking.model.Recipe;
import com.arman.baking.presenter.MainActivityPresenter;
import com.arman.baking.ui.adapter.RecipeAdapter;
import com.arman.baking.util.GridItemDecoration;
import com.arman.baking.view.RecipeView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;


public class MainActivity extends AppCompatActivity implements RecipeView, RecipeItemListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding mainBinding;
    private RecipeAdapter mAdapter;
    private MainActivityPresenter presenter;

    private boolean isTwoPane;
    private int currentOrientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mainBinding.getRoot();
        setContentView(view);

        isTwoPane = getResources().getBoolean(R.bool.two_pane_mode);

        currentOrientation = getResources().getConfiguration().orientation;

        initView();
        AppDb appDb = AppDb.getDatabase(this);
        presenter = new MainActivityPresenter(this, AndroidSchedulers.mainThread(), appDb);
        presenter.fetchAllRecipes();

        setupViewModel();

    }

    private void initView() {

        mAdapter = new RecipeAdapter(this);
        mAdapter.setItemListener(this);
        RecyclerView.LayoutManager mLayoutManager = null;
        if (isTwoPane || isLandScape()){
            mLayoutManager = new GridLayoutManager(this, 2);
            mainBinding.recycler.addItemDecoration(new GridItemDecoration(2, dpToPx(), true));
        }else {
            mLayoutManager = new LinearLayoutManager(this);
        }
        mainBinding.recycler.setLayoutManager(mLayoutManager);
        mainBinding.recycler.setItemAnimator(new DefaultItemAnimator());
        mainBinding.recycler.setHasFixedSize(true);
        mainBinding.recycler.setAdapter(mAdapter);

    }

    private boolean isLandScape(){
        return currentOrientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    private int dpToPx() {
        Resources resources = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, resources.getDisplayMetrics()));
    }

    private void setupViewModel() {

        final MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                mAdapter.setRecipeList(recipeList);

            }
        });
    }

    @Override
    public void onClickListener(Recipe recipe) {
        if (recipe != null) {
            Intent intent = new Intent(this, StepListActivity.class);
            intent.putExtra(Constant.key.KEY_RECIPE, recipe);
            startActivity(intent);
        }
    }

    @Override
    public void onDisplayRecipes(List<Recipe> recipeList) {
        if (recipeList != null) {
            mAdapter.setRecipeList(recipeList);
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}