package com.arman.baking.db;

import android.arch.persistence.room.TypeConverter;

import com.arman.baking.model.Ingredient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class IngredientConverters {
    @TypeConverter
    public static List<Ingredient> fromString(String value) {
        Type listType = new TypeToken<List<Ingredient>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Ingredient> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}