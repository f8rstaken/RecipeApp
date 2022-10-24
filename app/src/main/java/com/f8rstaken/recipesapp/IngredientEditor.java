package com.f8rstaken.recipesapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class IngredientEditor extends AppCompatActivity {

    private Bundle extras = null;
    private Ingredient chosenIngredient = null;
    private TextView tvInpName;
    private TextView tvInpWeight;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredienteditor);

        chosenIngredient = (Ingredient) getIntent().getSerializableExtra("item");


        if(getIntent().getExtras() != null){

            tvInpName = findViewById(R.id.inpAddIngNameEditor);
            tvInpWeight = findViewById(R.id.inpAddIngWeightEditor);

            tvInpName.setText(chosenIngredient.getName());
            tvInpWeight.setText(String.valueOf(chosenIngredient.getAmount()));

            index = chosenIngredient.getId();
        } else {
            Log.e("Error", "Failed to get extras");
            finish();
        }

    }

    public void onEditIngredient(View view){

        RecipeAdderActivity.ingredients.remove(index);
        chosenIngredient.setName(tvInpName.getText().toString());
        chosenIngredient.setAmount(Integer.valueOf(tvInpWeight.getText().toString()));
        Log.e("name", tvInpName.getText().toString());
        RecipeAdderActivity.ingredients.add(index, chosenIngredient);
        RecipeAdderActivity.adapter.notifyDataSetChanged();
        finish();
    }

    public void onRemoveIngredient(View view){

        RecipeAdderActivity.ingredients.remove(index);
        for(int i = index; i < RecipeAdderActivity.ingredients.size(); i++){
            RecipeAdderActivity.ingredients.get(i).setId(RecipeAdderActivity.ingredients.get(i).getId() - 1);
        }
        RecipeAdderActivity.adapter.notifyDataSetChanged();
        finish();
    }

}
