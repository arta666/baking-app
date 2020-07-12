package com.arman.baking.db;

import android.arch.persistence.room.TypeConverter;

import com.arman.baking.model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StepConverters {
    @TypeConverter
    public static List<Step> fromString(String value) {
        Type listType = new TypeToken<List<Step>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(List<Step> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}