package com.arman.baking.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.arman.baking.R;
import com.arman.baking.model.Step;
import com.arman.baking.ui.fragment.StepViewFragment;

import java.util.List;
import java.util.Locale;

public class IngredientAdapter extends FragmentStatePagerAdapter {


    private List<Step> steps;
    private Context mContext;


    public IngredientAdapter(FragmentManager fm, Context mContext, List<Step> steps) {
        super(fm);
        this.mContext = mContext;
        this.steps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        return StepViewFragment.newInstance(steps.get(position),position);
    }

    @Override
    public int getCount() {
        return steps.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.title_indroduction);
        }
        return String.format(Locale.getDefault(),mContext.getString(R.string.fomat_step_title), position);
    }
}
