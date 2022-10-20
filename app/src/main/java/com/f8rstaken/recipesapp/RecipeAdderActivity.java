package com.f8rstaken.recipesapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecipeAdderActivity extends AppCompatActivity {

    public static ArrayList<Ingredient> ingredients;
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    public static RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeadder);

        mDatabase = FirebaseDatabase.getInstance("https://recipeapp-c9c3a-default-rtdb.europe-west1.firebasedatabase.app").getReference();
        recyclerView = findViewById(R.id.recyclerView);
        ingredients = new ArrayList<>();

        EditText inpRecipe = findViewById(R.id.inputRecipe);
        inpRecipe.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        inpRecipe.setSingleLine(false);

        setAdapter();

    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    public void openIngredientAdder(View view) {
        Intent intent = new Intent(RecipeAdderActivity.this, IngredientAdder.class);
        startActivity(intent);
    }

    public void writeRecipe(View view){

        mDatabase.child("totalRecipes").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    long recipeNumber;
                    try {
                        recipeNumber = (long) task.getResult().getValue();
                    } catch (NullPointerException e) {
                        recipeNumber = 0;
                    }

                    mDatabase.child("totalRecipes").setValue(recipeNumber + 1);
                    mDatabase.child("recipeIds").child(String.valueOf(recipeNumber + 1)).setValue(1);

                    EditText etRecipeName = findViewById(R.id.inputRecipeName);
                    EditText etRecipeGuide = findViewById(R.id.inputRecipe);

                    String recipeName = etRecipeName.getText().toString();
                    String recipeGuide = etRecipeGuide.getText().toString();

                    //String poster = FirebaseAuth.getInstance();

                    int ingredientAmount = ingredients.size();

                    if(recipeName.length() < 3){
                        Toast.makeText(RecipeAdderActivity.this, "Recipe name must be atleast 3 letters long!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(recipeGuide.length() < 16){
                        Toast.makeText(RecipeAdderActivity.this, "Recipe must be at least 16 letters long!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(ingredientAmount < 1){
                        Toast.makeText(RecipeAdderActivity.this, "Recipe must contain at least a single ingredient!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    DatabaseReference cRecipeReference = mDatabase.child("recipes").child(String.valueOf(recipeNumber + 1));

                    cRecipeReference.child("name").setValue(recipeName);
                    cRecipeReference.child("guide").setValue(recipeGuide);
                    cRecipeReference.child("ingredientAmount").setValue(ingredientAmount);
                  //  cRecipeReference.child("submitter").setValue(poster);

                    int i = 0;

                    for (Ingredient ingredient : ingredients){
                        cRecipeReference.child("ingredients").child(String.valueOf(i)).child("name").setValue(ingredient.getName());
                        cRecipeReference.child("ingredients").child(String.valueOf(i)).child("amount").setValue(ingredient.getGrams());
                        i++;
                    }

                }
            }
        });

    }

    private void setAdapter() {
        adapter = new RecyclerAdapter(ingredients, new RecyclerAdapter.OnItemClickListener(){

            @Override
            public void onItemClick(Ingredient item) {
                Intent intent = new Intent(RecipeAdderActivity.this, IngredientEditor.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
