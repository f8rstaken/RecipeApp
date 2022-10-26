package com.f8rstaken.recipesapp;

import java.io.Serializable;
import java.util.ArrayList;

public class ListingItem implements Serializable {

    private String submitter;
    private String name;
    private String guide;
    private int ingredientAmount;
    private ArrayList<Ingredient> ingredients;
    private String id;

    public ListingItem(){

    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ListingItem(String name, String guide, String id){
        this.name = name;
        this.guide = guide;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIngredientAmount() {
        return ingredientAmount;
    }

    public void setIngredientAmount(int ingredientAmount) {
        this.ingredientAmount = ingredientAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

}
