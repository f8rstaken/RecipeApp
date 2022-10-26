package com.f8rstaken.recipesapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeViewerActivity extends AppCompatActivity {

    private ListingItem Item = null;
    private TextView tvViewerItemName;
    private TextView tvViewerItemSubmitter;
    private TextView tvViewerItemGuide;
    private RecyclerAdapter adapter;
    private RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeviewer);

        recyclerView = findViewById(R.id.rvRecipeViewerIngredients);

        tvViewerItemName = findViewById(R.id.tvViewerItemName);
        tvViewerItemSubmitter = findViewById(R.id.tvViewerItemSubmitter);
        tvViewerItemGuide = findViewById(R.id.tvViewerItemGuide);

        Item = (ListingItem) getIntent().getSerializableExtra("item");

        if(Item != null){

            tvViewerItemName.setText(Item.getName());
            tvViewerItemSubmitter.setText(Item.getSubmitter());
            tvViewerItemGuide.setText(Item.getGuide());
            setAdapter();

        } else {
            Toast.makeText(this, "Failed to retrieve data for this listing", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(Item.getIngredients(), new RecyclerAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(Ingredient item) {
                return;
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
