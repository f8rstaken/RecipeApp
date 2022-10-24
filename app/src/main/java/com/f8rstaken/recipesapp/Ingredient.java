package com.f8rstaken.recipesapp;

import java.io.Serializable;

public class Ingredient implements Serializable {

    private String name;
    private int amount;
    private int id;

    public Ingredient(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public Ingredient(String name, int amount, int id) {
        this.name = name;
        this.amount = amount;
        this.id = id;
    }

    public Ingredient() {

    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
