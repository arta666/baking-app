package com.arman.baking.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.arman.baking.R;
import com.arman.baking.databinding.FragmentStepViewBinding;
import com.arman.baking.databinding.StepItemBinding;
import com.arman.baking.model.Recipe;

import moe.feng.common.stepperview.IStepperAdapter;
import moe.feng.common.stepperview.VerticalStepperItemView;

public class StepsAdapter implements IStepperAdapter, View.OnClickListener {

    private static final String TAG = StepsAdapter.class.getSimpleName();

    private Context mContext;

    private Recipe recipe;

    private StepperClickListener listener;

    private int currentPosition;


    public interface StepperClickListener {
        void onNextClick(int position);
        void onBackClick(int position);
    }

    public StepsAdapter(Context mContext, Recipe recipe) {
        this.mContext = mContext;
        this.recipe = recipe;
    }

    public void setListener(StepperClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CharSequence getTitle(int position) {
        if (position == 0){
            return "Welcome";
        }else{
            if(position != size() -1){
                return recipe.getSteps().get(position -1).getShortDescription();
            }else {
                return "Last Step";
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getSummary(int position) {
        if (position == 0){
            return "Welcome to Summury";
        }else{
            if(position != size() -1){
                return recipe.getSteps().get(position -1).getShortDescription();
            }else {
                return "Last Summury";
            }
        }
    }

    @Override
    public int size() {
        if (recipe == null) return 0;
        return (recipe.getSteps().size() + 2);
    }

    @Override
    public View onCreateCustomView(int position, Context context, VerticalStepperItemView parent) {

        LayoutInflater inflater = LayoutInflater.from(context);

        StepItemBinding binding = StepItemBinding.inflate(inflater,parent,false);

        View view = binding.getRoot();

        this.currentPosition = position;

        binding.btBack.setOnClickListener(this);

        binding.btGo.setOnClickListener(this);

        return view;
    }

    @Override
    public void onShow(int i) {

    }

    @Override
    public void onHide(int i) {

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();

        switch (viewId){
            case R.id.btGo:
                handleNextButton(currentPosition);
                break;
            case R.id.btBack:
                handleBackButton(currentPosition);
                break;
        }
    }

    private void handleNextButton(int position){
        if(listener == null){
            Log.i(TAG, "please set listener !");
            return;
        }
        listener.onNextClick(position);
    }

    private void handleBackButton(int position){
        if(listener == null){
            Log.i(TAG, "please set listener !");
            return;
        }
        listener.onBackClick(position);
    }
}
