package com.arman.baking.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.arman.baking.R;
import com.arman.baking.app.Constant;
import com.arman.baking.databinding.ActivityStepBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.model.Step;
import com.arman.baking.ui.adapter.IngredientAdapter;
import com.arman.baking.ui.fragment.StepListFragment;

import java.util.List;

public class StepListActivity extends AppCompatActivity implements StepListFragment.OnStepClickListener {

    private static final String TAG = StepListActivity.class.getSimpleName();


    private ActivityStepBinding mStepBinding;

    private Recipe mRecipe;

    private boolean isTablet;

    private IngredientAdapter mViewPagerAdapter;

    private int currentPosition;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStepBinding = ActivityStepBinding.inflate(getLayoutInflater());
        View view = mStepBinding.getRoot();
        setContentView(view);

        isTablet = getResources().getBoolean(R.bool.two_pane_mode);

        Intent intent = getIntent();

        if (!intent.hasExtra(Constant.key.KEY_RECIPE)) {
            finish();
        }

        mRecipe = intent.getParcelableExtra(Constant.key.KEY_RECIPE);


        if (savedInstanceState == null) {

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.container, StepListFragment.newInstance(mRecipe))
                    .commit();

            if (isTablet) {
                setUpViewPager(mRecipe.getSteps());
            }
        }
        setUpActionBar();

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

        mViewPagerAdapter = new IngredientAdapter(fragmentManager, this, stepList);

        if (mStepBinding.viewPager != null && mStepBinding.tabView != null) {
            mStepBinding.viewPager.setAdapter(mViewPagerAdapter);
            mStepBinding.tabView.setupWithViewPager(mStepBinding.viewPager);
            mStepBinding.viewPager.setCurrentItem(currentPosition);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.step_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_send_to_widget:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constant.key.KEY_RECIPE, mRecipe);
        outState.putInt(Constant.key.KEY_POSITION, currentPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipe = savedInstanceState.getParcelable(Constant.key.KEY_RECIPE);
        currentPosition = savedInstanceState.getInt(Constant.key.KEY_POSITION);
    }

    @Override
    public void onStepClicked(int position) {
        if (mStepBinding.viewPager != null) {
            mStepBinding.viewPager.setCurrentItem(position);
        }
    }
}
