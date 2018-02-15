package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mDescriptionTextView;
    TextView mOriginTextView;
    TextView mKnownAsTextView;
    TextView mIngredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mDescriptionTextView = findViewById(R.id.description_tv);
        mOriginTextView = findViewById(R.id.origin_tv);
        mKnownAsTextView = findViewById(R.id.also_known_tv);
        mIngredientsTextView = findViewById(R.id.ingredients_tv);
        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        //Create a String that is a list of names the sandwich is known by separated by commas and ending with a full stop.
        StringBuilder alsoKnownAsSb = new StringBuilder();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        for (int i = 0; i < alsoKnownAs.size(); i++) {
            alsoKnownAsSb.append(alsoKnownAs.get(i));
            if (i == alsoKnownAs.size() - 1) {
                alsoKnownAsSb.append(".");
            } else {
                alsoKnownAsSb.append(", ");
            }
        }
        String knownAs = alsoKnownAsSb.toString();
        //If we have an empty string there is no other names for this sandwich let the user know
        if (knownAs.equals("")) {
            knownAs = getResources().getString(R.string.detail_no_also_known_as);
        }
        //Create a String that is a list of ingredients separated by commas and ending with a full stop.
        StringBuilder ingredientsSb = new StringBuilder();
        List<String> ingredients = sandwich.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            ingredientsSb.append(ingredients.get(i));
            if (i == ingredients.size() - 1) {
                ingredientsSb.append(".");
            } else {
                ingredientsSb.append(", ");
            }
        }
        String ingredientsString = ingredientsSb.toString();
        // If placeOfOrigin is empty set it to unknown
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.equals("")) {
            placeOfOrigin = getResources().getString(R.string.detail_no_place_of_origin);
        }
        // set the text in the textViews
        mDescriptionTextView.setText(sandwich.getDescription());
        mKnownAsTextView.setText(knownAs);
        mIngredientsTextView.setText(ingredientsString);
        mOriginTextView.setText(placeOfOrigin);
    }
}
