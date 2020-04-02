package com.udacity.sandwichclub;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    // Declare variables for Views
    TextView akaLabelTextView;
    TextView akaTextView;
    TextView placeLabelTextView;
    TextView placeTextView;
    TextView descriptionLabelTextView;
    TextView descriptionTextView;
    TextView ingredientsLabelTextView;
    TextView ingredientsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Set variables to their Views
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        akaLabelTextView = findViewById(R.id.also_known_label_tv);
        akaTextView = findViewById(R.id.also_known_tv);
        placeLabelTextView = findViewById(R.id.origin_label_tv);
        placeTextView = findViewById(R.id.origin_tv);
        descriptionLabelTextView = findViewById(R.id.description_label_tv);
        descriptionTextView = findViewById(R.id.description_tv);
        ingredientsLabelTextView = findViewById(R.id.ingredients_label_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = DEFAULT_POSITION;
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        }
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];

        Sandwich sandwich = JsonUtils.parseSandwichJson(json);

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
        // Declare Variables
        String temp;

        // populate Also Known As
        if (sandwich.getAlsoKnownAs() == null) {
            // hide Views if no data
            akaLabelTextView.setVisibility(View.GONE);
            akaTextView.setVisibility(View.GONE);
        }
        else {
            // show Views if there is data
            akaLabelTextView.setVisibility(View.VISIBLE);
            akaTextView.setVisibility(View.VISIBLE);
            // create text from List<String> and put in View
            boolean isFirst = true;
            for (String item : sandwich.getAlsoKnownAs()) {
                if (isFirst) {
                    akaTextView.setText(item);
                    isFirst = false;
                } else {
                    akaTextView.append("\n" + item);
                }
            }
        }

        // populate Place Of Origin
        temp = sandwich.getPlaceOfOrigin();
        if (temp.equals("")) {
            // hide Views if no data
            placeLabelTextView.setVisibility(View.GONE);
            placeTextView.setVisibility(View.GONE);
        } else {
            // show Views if there is data
            placeLabelTextView.setVisibility(View.VISIBLE);
            placeTextView.setVisibility(View.VISIBLE);
            // put text in View
            placeTextView.setText(sandwich.getPlaceOfOrigin());
        }

        // populate Description
        temp = sandwich.getDescription();
        if (temp.equals("")) {
            // hide Views if no data
            descriptionLabelTextView.setVisibility(View.GONE);
            descriptionTextView.setVisibility(View.GONE);
        } else {
            // show Views if there is data
            descriptionLabelTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setVisibility(View.VISIBLE);
            // put text in View
            descriptionTextView.setText(sandwich.getDescription());
        }

        // populate Ingredients
        if (sandwich.getIngredients() == null) {
            // hide Views if no data
            ingredientsLabelTextView.setVisibility(View.GONE);
            ingredientsTextView.setVisibility(View.GONE);
        }
        else {
            // show Views if there is data
            ingredientsLabelTextView.setVisibility(View.VISIBLE);
            ingredientsTextView.setVisibility(View.VISIBLE);
            // create text from List<String> and put in View
            boolean isFirst = true;
            for (String item : sandwich.getIngredients()) {
                if (isFirst) {
                    ingredientsTextView.setText(String.format("- %s", item));
                    isFirst = false;
                } else {
                    ingredientsTextView.append("\n" + String.format("- %s", item));
                }
            }
        }
    }

}
