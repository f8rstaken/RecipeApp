package com.f8rstaken.recipesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button btnRecipeAdder;
    private static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        btnRecipeAdder = (Button) findViewById(R.id.btnRecipeAdder);
        mContext = getApplicationContext();
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){
            btnRecipeAdder.setVisibility(View.INVISIBLE);
        } else {
            btnRecipeAdder.setVisibility(View.VISIBLE);
        }

    }

    public void openUserMenu(View view){

        FirebaseUser currentUser = mAuth.getCurrentUser();
        Intent intent;
        if(currentUser == null){
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }

    public void openBrowsing(View view){

        Intent intent = new Intent(this, RecipeBrowserActivity.class);
        startActivity(intent);

    }

    public void openRecipeAdder(View view){

        Intent intent = new Intent(this, RecipeAdderActivity.class);
        startActivity(intent);

    }

}