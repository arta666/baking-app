package com.arman.baking.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.arman.baking.model.Ingredient;
import com.google.gson.Gson;

import java.util.HashMap;

public class PrefManager {

    private String TAG = PrefManager.class.getSimpleName();

    // Shared Preferences
    private SharedPreferences mPref;

    // Editor for Shared preferences
    private SharedPreferences.Editor mEditor;

    // Context
    private Context mContext;



    private static final String DEFAULT_VALUE = "";

    private final Gson gson;


    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        this.mContext = context;
        this.gson = new Gson();
        mPref = mContext.getSharedPreferences(Constant.PREF_NAME, Context.MODE_PRIVATE);
        mEditor = mPref.edit();
    }

    //--- Storing String -----
    public void saveString(String key, String value){
        mEditor.putString(key,value);
        mEditor.commit();
    }

    //--- Getting String ----
    public String getString(String key){
        return mPref.getString(key,DEFAULT_VALUE);
    }

    public void saveIngredient(String key, Ingredient ingredient){

        String json = gson.toJson(ingredient);
        mEditor.putString(key,json);
        mEditor.commit();
    }

    public Ingredient getIngredient(String key){
        String json = mPref.getString(key,"");
        return gson.fromJson(json,Ingredient.class);
    }


    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

}
