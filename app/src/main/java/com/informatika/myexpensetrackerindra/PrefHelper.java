package com.informatika.myexpensetrackerindra;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class PrefHelper {

    SharedPreferences pref;
    Gson gson = new Gson();

    public PrefHelper(Context ctx){
        pref = ctx.getSharedPreferences("expense_data_indra", Context.MODE_PRIVATE);
    }

    public void saveList(ArrayList<ExpenseModel> list){
        String json = gson.toJson(list);
        pref.edit().putString("expenses", json).apply();
    }

    public ArrayList<ExpenseModel> getList(){
        String json = pref.getString("expenses", null);
        return gson.fromJson(json, new TypeToken<ArrayList<ExpenseModel>>(){}.getType());
    }
}
