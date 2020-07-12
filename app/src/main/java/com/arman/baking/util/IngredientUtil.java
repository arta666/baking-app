package com.arman.baking.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.arman.baking.R;
import com.arman.baking.model.Ingredient;

import java.util.List;
import java.util.Locale;

public class IngredientUtil {

    public static String createIngredientText(Context context ,List<Ingredient> ingredientList) {
        if (ingredientList == null){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();

        for (Ingredient ingredient: ingredientList) {
            String name = ingredient.getIngredient();
            String quantity = String.valueOf(ingredient.getQuantity());
            String measure = ingredient.getMeasure();
            stringBuilder.append(createIngredient(context,name,quantity,measure));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }


    private static String createIngredient(Context context,String name, String quantity, String measure){

        String lint =  context.getString(R.string.recipe_details_ingredient_line);
        return String.format(Locale.getDefault(),lint,name, quantity,measure);
    }
}
