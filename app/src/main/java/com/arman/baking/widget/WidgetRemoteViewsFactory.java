package com.arman.baking.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.arman.baking.R;
import com.arman.baking.app.Constant;
import com.arman.baking.db.AppDb;
import com.arman.baking.db.IngredientDao;
import com.arman.baking.model.Ingredient;

import java.util.ArrayList;
import java.util.List;


public class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = WidgetRemoteViewsFactory.class.getSimpleName();
    private List<Ingredient> ingredients = new ArrayList<>();
    private Context context;
    private int appWidgetId;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        IngredientDao ingredientDao = AppDb.getDatabase(context).ingredientDao();
        List<Ingredient> ingredients = ingredientDao.getIngredients();
        if (ingredients != null) {
            this.ingredients.clear();
            this.ingredients.addAll(ingredients);
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        IngredientDao ingredientDao = AppDb.getDatabase(context).ingredientDao();
        List<Ingredient> ingredients = ingredientDao.getIngredients();
        if (ingredients != null) {
            this.ingredients.clear();
            this.ingredients.addAll(ingredients);
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.item_widget);

        Ingredient ingredient = ingredients.get(position);
        row.setTextViewText(R.id.ingredient, ingredient.getIngredient());
        row.setTextViewText(R.id.quantity, context.getString(R.string.format_ingredient, ingredient.getQuantity(), ingredient.getMeasure()));

        Intent intent = new Intent();
        Bundle extras = new Bundle();
        extras.putBoolean(Constant.INGREDIENTS, true);
        extras.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.putExtras(extras);

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}