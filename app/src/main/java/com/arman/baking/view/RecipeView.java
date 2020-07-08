package com.arman.baking.view;

import com.arman.baking.model.Recipe;

import java.util.List;

public interface RecipeView {

    void onDisplayRecipes(List<Recipe> recipeList);

    void onFailure(String message);
}
