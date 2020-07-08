package com.arman.baking.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.arman.baking.R;
import com.arman.baking.databinding.ActivityStepBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.ui.StepViewFragment;

public class StepActivity extends AppCompatActivity {

    private static final String TAG = StepActivity.class.getSimpleName();

    public static final String KEY_RECIPE = "recipe_key";

    private ActivityStepBinding mStepBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStepBinding = ActivityStepBinding.inflate(getLayoutInflater());
        View view = mStepBinding.getRoot();
        setContentView(view);


        Intent intent = getIntent();

        if (!intent.hasExtra(KEY_RECIPE)){
            finish();
        }

        Recipe recipe = intent.getParcelableExtra(KEY_RECIPE);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.container,StepViewFragment.newInstance(recipe))
                .commit();
    }
}
