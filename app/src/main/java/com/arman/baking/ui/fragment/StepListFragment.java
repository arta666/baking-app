package com.arman.baking.ui.fragment;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arman.baking.app.Constant;
import com.arman.baking.widget.AppWidget;
import com.arman.baking.R;
import com.arman.baking.app.BakingApplication;
import com.arman.baking.app.PrefManager;
import com.arman.baking.databinding.FragmentStepListBinding;
import com.arman.baking.db.AppDb;
import com.arman.baking.db.IngredientDao;
import com.arman.baking.listeners.RecyclerTouchListener;

import com.arman.baking.model.Recipe;
import com.arman.baking.ui.activity.StepViewActivity;
import com.arman.baking.ui.adapter.StepsAdapter;
import com.arman.baking.util.IngredientUtil;
import java.util.Objects;

public class StepListFragment extends Fragment {



    private FragmentStepListBinding mViewBinding;

    private StepsAdapter mAdapter;

    private Recipe mRecipe;

    private PrefManager pref;

    private boolean isTablet;

    private OnStepClickListener mCallBack;

    public interface OnStepClickListener {
        void onStepClicked(int position);
    }

    public static StepListFragment newInstance(Recipe recipe) {

        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.key.KEY_RECIPE, recipe);

        StepListFragment fragment = new StepListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = BakingApplication.getInstance().getPrefManager();
        isTablet = getResources().getBoolean(R.bool.two_pane_mode);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentStepListBinding.inflate(inflater, container, false);
        View view = mViewBinding.getRoot();
        setHasOptionsMenu(true);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(Constant.key.KEY_RECIPE);
            if (mRecipe != null) {
                String ingredient = IngredientUtil.createIngredientText(getContext(), mRecipe.getIngredients());
                mViewBinding.ingredientView.tvIngredient.setText(ingredient);
                mAdapter.setStepList(mRecipe.getSteps());
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    private void initView() {
        mAdapter = new StepsAdapter(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mViewBinding.recycler.setLayoutManager(layoutManager);
        mViewBinding.recycler.setHasFixedSize(true);
        mViewBinding.recycler.setAdapter(mAdapter);

        mViewBinding.recycler.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                mViewBinding.recycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (isTablet) {
                    mCallBack.onStepClicked(position);
                } else {
                    openVideo(position);

                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

    }

    private void showIngredientsInWidget() {

        AppDb db = AppDb.getDatabase(getContext());

        IngredientDao ingredientDao = db.ingredientDao();

        ingredientDao.deleteAll();
        ingredientDao.insertAll(mRecipe.getIngredients());
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intent.setComponent(new ComponentName(Objects.requireNonNull(getContext()), AppWidget.class));
        Objects.requireNonNull(getActivity()).sendBroadcast(intent);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_send_to_widget){
            showIngredientsInWidget();
            Toast.makeText(getContext(), R.string.msg_widget_sent, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding = null;
    }

    private void openVideo(int position) {
        Intent intent = new Intent(getContext(), StepViewActivity.class);
        intent.putExtra(Constant.key.KEY_RECIPE, mRecipe);
        intent.putExtra(Constant.key.KEY_POSITION, position);
        startActivity(intent);
    }
}
