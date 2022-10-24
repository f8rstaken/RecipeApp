package com.f8rstaken.recipesapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeViewerActivity extends AppCompatActivity {

    private ListingItem Item = null;
    private TextView tvViewerItemName;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeviewer);

        tvViewerItemName = findViewById(R.id.tvViewerItemName);

        Item = (ListingItem) getIntent().getSerializableExtra("item");

        if(Item != null){

            tvViewerItemName.setText(Item.getName());

        } else {
            Toast.makeText(this, "Failed to get item", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}
