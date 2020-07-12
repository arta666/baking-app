package com.arman.baking.util;


import android.support.v4.view.ViewPager;

import com.arman.baking.listeners.PageChangeListener;

// This Class Help us to get ride of unused methods ! :D
public class ViewPagerScrollListener{

    private PageChangeListener callback;

    public ViewPagerScrollListener setCallback(PageChangeListener callback) {
        this.callback = callback;
        return this;
    }

    public ViewPagerScrollListener registerListener(final ViewPager viewPager) {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                callback.onPageScrolled(i,v,i1);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return this;
    }
}