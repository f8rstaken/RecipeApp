package com.f8rstaken.recipesapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeBrowserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ListingRecyclerAdapter adapter;
    private TextView tvPageNum;
    public HashMap<Integer, ListingItem> hlisting;
    public ArrayList<ListingItem> listing;
    public HashMap<Integer, Integer> perpage;
    public ArrayList<String> recipeIds;

    private int currentPage = 1;
    private int maxPage = -1;
    private int recipesPerPage = 2;
    private int totalRecipes = -1;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipebrowser);
        mDatabase = FirebaseDatabase.getInstance("https://recipeapp-c9c3a-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        listing = new ArrayList<>();
        hlisting = new HashMap<>();
        perpage = new HashMap<>();
        recipeIds = new ArrayList<>();
        recyclerView = findViewById(R.id.listingRecyclerView);
        tvPageNum = findViewById(R.id.tvPageNum);
        TextView tvLoadingText = findViewById(R.id.tvLoadingText);

        getRecipeIds();
        getTotalRecipes();
        getLastPageN();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(recipeIds.size() == 0 || totalRecipes == -1 || maxPage == -1){
                    finish();
                    Toast.makeText(MainActivity.getContext(), "Could not get data!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.e("Log", "max Page: " + maxPage);
                tvLoadingText.setVisibility(View.INVISIBLE);
                updateListing();
                setAdapter();
            }
        }, 2500);

        //updateListing();
        //setAdapter();
    }

    public void prevPage(View view){

        if(currentPage <= 1){
            return;
        }
        currentPage--;
        tvPageNum.setText(String.valueOf(currentPage));
        updateListing();
        adapter.notifyDataSetChanged();
    }

    public void nextPage(View view){
  //      getLastPageN();

        if(currentPage >= maxPage-1){
            return;
        }
        currentPage++;
        tvPageNum.setText(String.valueOf(currentPage));
        updateListing();
        adapter.notifyDataSetChanged();
    }

    private void getTotalRecipes(){

        mDatabase.child("totalRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalRecipes = snapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void getRecipeIds(){

        mDatabase.child("recipeIds").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot it : snapshot.getChildren()){
                        recipeIds.add(it.getKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void getLastPageN() {


        mDatabase.child("totalRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    maxPage = snapshot.getValue(Integer.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                maxPage = (int) Math.ceil((double) maxPage/recipesPerPage);
            }
        }, 2000);


    }

    private void updateListing() {

        listing.clear();
        int pageToUpdate = currentPage;
        int startingPage = (pageToUpdate - 1) * recipesPerPage;

        for (int i = 1; i <= recipesPerPage; i++) {
            int id = startingPage + i;

            mDatabase.child("recipes").child(recipeIds.get(id)).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {

                        String name = snapshot.child("name").getValue(String.class);
                        String guide = snapshot.child("guide").getValue(String.class);
                        // String submitter = dataSnapshot.child("submitter").getValue(String.class);
                        int ingredientAmount = snapshot.child("ingredientAmount").getValue(Integer.class);
                        ArrayList<Ingredient> ingredientList = new ArrayList<Ingredient>();

                        for (int i = 0; i < ingredientAmount; i++) {
                            String ingredientName = snapshot.child("ingredients").child(String.valueOf(i)).child("name").getValue(String.class);
                            int ingredientWeight = snapshot.child("ingredients").child(String.valueOf(i)).child("amount").getValue(Integer.class);
                            ingredientList.add(new Ingredient(name, ingredientWeight));
                        }

                        listing.add(new ListingItem(name, "lololol"));
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void setAdapter() {
        adapter = new ListingRecyclerAdapter(listing, new ListingRecyclerAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(ListingItem item) {
                Intent intent = new Intent(RecipeBrowserActivity.this, IngredientEditor.class);
//                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
