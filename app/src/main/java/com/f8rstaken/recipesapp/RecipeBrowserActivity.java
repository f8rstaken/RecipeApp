package com.f8rstaken.recipesapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import java.util.List;

public class RecipeBrowserActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    private ListingRecyclerAdapter adapter;
    private TextView tvPageNum;
    private TextView tvLoadingText;
    public HashMap<Integer, ListingItem> hlisting;
    public ArrayList<ListingItem> currentlyShowing;
    public ArrayList<String> recipeIds;

    private int currentPage = 1;
    private int maxPage = -1;
    private int recipesPerPage = 2;
    private int totalRecipes = 0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipebrowser);
        mDatabase = FirebaseDatabase.getInstance("https://recipeapp-c9c3a-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        currentlyShowing = new ArrayList<>();
        hlisting = new HashMap<>();
        recipeIds = new ArrayList<>();
        recyclerView = findViewById(R.id.listingRecyclerView);
        tvPageNum = findViewById(R.id.tvPageNum);
        tvLoadingText = findViewById(R.id.tvLoadingText);

        getData();

    }

    public void loadData(){
        currentlyShowing.clear();
        int offset = (currentPage-1)*recipesPerPage;
        for(int i = 1; i <= recipesPerPage; i++){
            currentlyShowing.add(hlisting.get(offset + i));
        }
        Log.e("size", "" + currentlyShowing.size());
        //adapter.notifyDataSetChanged();

    }

    public void getData(){

        mDatabase.child("totalRecipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    totalRecipes = snapshot.getValue(Integer.class);

                    mDatabase.child("recipes").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()) {
                                int i = 1;
                                for (DataSnapshot snap : snapshot.getChildren()) {
                                    ListingItem item = snap.getValue(ListingItem.class);
                                    hlisting.put(i, item);
                                    item.setId(snap.getKey());
                                    recipeIds.add(snap.getKey());
                                    i++;
                                }
                                loadData();
                                setAdapter();
                                maxPage = (int) Math.ceil((double) totalRecipes/recipesPerPage);
                                Log.e("Max page: ", ""+maxPage);
                                tvLoadingText.setVisibility(View.INVISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void prevPage(View view){
        if(currentPage <= 1){
            return;
        }
        currentPage--;
        tvPageNum.setText(String.valueOf(currentPage));
        loadData();
        adapter.notifyDataSetChanged();
    }

    public void nextPage(View view){

        if(currentPage >= maxPage){
            return;
        }
        currentPage++;
        tvPageNum.setText(String.valueOf(currentPage));
        loadData();
        adapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        adapter = new ListingRecyclerAdapter(currentlyShowing, new ListingRecyclerAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(ListingItem item) {
                Log.e("clickName", item.getName());
                Intent intent = new Intent(RecipeBrowserActivity.this, RecipeViewerActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

}