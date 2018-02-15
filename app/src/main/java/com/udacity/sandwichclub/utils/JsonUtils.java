package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject name = jsonObject.getJSONObject("name");
                String mainName = name.getString("mainName");
                JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
                // looping through All results
                List<String> alsoKnownAs = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    String knownAs = alsoKnownAsArray.getString(i);
                    alsoKnownAs.add(knownAs);
                }
                String placeOfOrigin = jsonObject.getString("placeOfOrigin");
                String description = jsonObject.getString("description");
                String image = jsonObject.getString("image");
                JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");
                // looping through All results
                List<String> ingredientsList = new ArrayList<>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String ingredients = ingredientsArray.getString(i);
                    ingredientsList.add(ingredients);
                }
                return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredientsList);
            } catch (final JSONException e) {
                Log.e("JSONException", e.getMessage());
            }
        }
        return null;

    }
}
