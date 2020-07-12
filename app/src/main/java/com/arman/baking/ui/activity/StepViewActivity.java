package com.arman.baking.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.arman.baking.app.Constant;
import com.arman.baking.databinding.ActivityStepViewBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.model.Step;
import com.arman.baking.ui.adapter.IngredientAdapter;
import com.arman.baking.util.ViewPagerScrollListener;

import java.util.ArrayList;
import java.util.List;

public class StepViewActivity extends AppCompatActivity {

    private static final String TAG = StepViewActivity.class.getSimpleName();


    private ActivityStepViewBinding mBinding;

    private int currentPosition;

    private List<Step> mStepList;

    private int currentOrientation;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityStepViewBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        currentOrientation = getResources().getConfiguration().orientation;

        Intent intent = getIntent();

        if (!intent.hasExtra(Constant.key.KEY_RECIPE)) {
            finish();
        }
        mRecipe = intent.getParcelableExtra(Constant.key.KEY_RECIPE);

        setUpActionBar();

        mStepList = mRecipe.getSteps();

        currentPosition = intent.getIntExtra(Constant.key.KEY_POSITION, 0);

        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(Constant.key.KEY_RECIPE);
            mStepList = savedInstanceState.getParcelableArrayList(Constant.key.KEY_STEPS);
            currentPosition = savedInstanceState.getInt(Constant.key.KEY_POSITION);
        }

        setUpViewPager(mStepList);

    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            if (mRecipe != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(mRecipe.getName());
            }
        }
    }

    private void setUpViewPager(List<Step> stepList) {

        FragmentManager fragmentManager = getSupportFragmentManager();

        IngredientAdapter adapter = new IngredientAdapter(fragmentManager, this, stepList);

        mBinding.viewPager.setAdapter(adapter);

        mBinding.tabView.setupWithViewPager(mBinding.viewPager);

        mBinding.viewPager.setCurrentItem(currentPosition);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
        outState.putParcelable(Constant.key.KEY_RECIPE, mRecipe);
        outState.putParcelableArrayList(Constant.key.KEY_STEPS, (ArrayList<Step>) mStepList);
        outState.putInt(Constant.key.KEY_POSITION, currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState: ");
        mRecipe = savedInstanceState.getParcelable(Constant.key.KEY_RECIPE);
        mStepList = savedInstanceState.getParcelableArrayList(Constant.key.KEY_STEPS);
        currentPosition = savedInstanceState.getInt(Constant.key.KEY_POSITION);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        currentOrientation = newConfig.orientation;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: ");
        if (hasFocus && currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            hideSystemUI();
            mBinding.tabView.setVisibility(View.GONE);
        }

    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

}