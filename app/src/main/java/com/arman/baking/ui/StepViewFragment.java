package com.arman.baking.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.baking.databinding.FragmentStepViewBinding;
import com.arman.baking.model.Recipe;
import com.arman.baking.ui.adapter.StepsAdapter;

public class StepViewFragment extends Fragment implements StepsAdapter.StepperClickListener  {

    public static final String KEY_RECIPE = "recipe_key";

    private FragmentStepViewBinding mViewBinding;

    private StepsAdapter mAdapter;

    private Recipe mRecipe;

    public static StepViewFragment newInstance(Recipe recipe){

        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_RECIPE,recipe);

        StepViewFragment fragment = new StepViewFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentStepViewBinding.inflate(inflater, container, false);
        View view = mViewBinding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() !=null){
            mRecipe = getArguments().getParcelable(KEY_RECIPE);
            mAdapter = new StepsAdapter(getContext(),mRecipe);
            mAdapter.setListener(this);
            mViewBinding.vsStepper.setStepperAdapter(mAdapter);
            mViewBinding.vsStepper.setCurrentStep(0);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }

    @Override
    public void onNextClick(int position) {
        if (mViewBinding.vsStepper.canNext()){
            mViewBinding.vsStepper.nextStep();
        }else {
            mViewBinding.vsStepper.setAnimationEnabled(!mViewBinding.vsStepper.isAnimationEnabled());
        }
    }

    @Override
    public void onBackClick(int position) {
        if (position != 0 && mViewBinding.vsStepper.canNext()){
            mViewBinding.vsStepper.prevStep();
        }else {
            mViewBinding.vsStepper.setAnimationEnabled(!mViewBinding.vsStepper.isAnimationEnabled());
        }
    }
}
