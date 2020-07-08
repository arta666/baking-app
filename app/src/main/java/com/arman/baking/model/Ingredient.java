package com.arman.baking.model;

import com.google.gson.annotations.Expose;

public class Ingredient {

    @Expose
    private float quantity;
    @Expose
    private String measure;
    @Expose
    private String ingredient;


    public Ingredient() {
    }

    public float getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
