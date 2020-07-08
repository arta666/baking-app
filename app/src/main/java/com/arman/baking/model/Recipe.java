package com.arman.baking.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Recipe {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private List<Ingredient> ingredients;
    @Expose
    private List<Step> steps;
    @Expose
    private int serving;
    @Expose
    private String image;

    public Recipe() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public int getServing() {
        return serving;
    }

    public String getImage() {
        return image;
    }
}
