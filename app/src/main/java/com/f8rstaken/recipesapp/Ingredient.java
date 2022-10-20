package com.f8rstaken.recipesapp;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String name;
    private int grams;
    private int id;

    public Ingredient(String name, int grams) {
        this.name = name;
        this.grams = grams;
    }

    public Ingredient(String name, int grams, int id) {
        this.name = name;
        this.grams = grams;
        this.id = id;
    }

    public Ingredient() {

    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", grams=" + grams +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGrams() {
        return grams;
    }

    public void setGrams(int grams) {
        this.grams = grams;
    }
}
