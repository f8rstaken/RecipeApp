package com.f8rstaken.recipesapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IngredientAdder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientadder);

    }

    public void onAddIngredient(View view){

        String name = ((TextView) findViewById(R.id.inpAddIngName)).getText().toString();
        int weight = Integer.valueOf(((TextView) findViewById(R.id.inpAddIngWeight)).getText().toString());

        RecipeAdderActivity.ingredients.add(new Ingredient(name, weight, RecipeAdderActivity.ingredients.size()));
        RecipeAdderActivity.adapter.notifyDataSetChanged();
        finish();

    }

}
