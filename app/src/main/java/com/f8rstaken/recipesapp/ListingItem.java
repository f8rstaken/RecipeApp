package com.f8rstaken.recipesapp;

public class ListingItem {

    private String name;
    private String description;


    public ListingItem(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ListingItem{" +
                "name='" + name + '\'' +
                ", grams=" + description +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
