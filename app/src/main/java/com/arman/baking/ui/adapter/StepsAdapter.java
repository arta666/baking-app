package com.arman.baking.ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arman.baking.R;
import com.arman.baking.databinding.IngredientsCardViewBinding;

import com.arman.baking.databinding.ItemStepBinding;

import com.arman.baking.model.Ingredient;
import com.arman.baking.model.Step;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsViewHolder> {

    private Context mContext;

    private List<Step> stepList;

    private static final int HEADER = 0;

    private static final int ITEMS = 1;

    public StepsAdapter(Context mContext) {
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public StepsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemStepBinding itemBinding = ItemStepBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new StepsViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsViewHolder holder, int position) {
       holder.binder(getItem(position));
    }


    @Override
    public int getItemCount() {
        if (stepList == null) {
            return 0;
        }
        return stepList.size();
    }

    public Step getItem(int position) {
        return stepList.get(position);
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
        notifyDataSetChanged();
    }


    class StepsViewHolder extends RecyclerView.ViewHolder {

        ItemStepBinding itemBinding;

        public StepsViewHolder(@NonNull ItemStepBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }


        private void binder(Step step) {
            if (step != null) {
                itemBinding.frameLayout.setBackground(getFrameDrawable(step));
                itemBinding.stepNumber.setText(String.valueOf(step.getId()));
                itemBinding.stepTitle.setText(step.getShortDescription());
            }
        }

        private Drawable getFrameDrawable(Step step) {
            if (isFirstStep(step)) {
                return ContextCompat.getDrawable(mContext, R.drawable.buble_yellow);
            }
            return ContextCompat.getDrawable(mContext, R.drawable.buble_view);
        }

        private boolean isFirstStep(Step step){
            return step.getId() == 0;
        }

    }
}
