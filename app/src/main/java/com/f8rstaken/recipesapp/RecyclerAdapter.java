package com.f8rstaken.recipesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Ingredient item);
    }
    private ArrayList<Ingredient> ingredients;
    private final OnItemClickListener listener;



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ingredientName;
        private TextView ingredientData;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.tvIngredient);
            ingredientData = itemView.findViewById(R.id.tvIngredientAmount);
        }

        public void bind(final Ingredient item, final OnItemClickListener listener) {
            ingredientName.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag("TEST!");
                    listener.onItemClick(item);


                }
            });
        }
    }

    public RecyclerAdapter(ArrayList<Ingredient> ingredients, OnItemClickListener listener){
        this.ingredients = ingredients;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ingredientView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ingredients, parent, false);

        return new ViewHolder(ingredientView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(ingredients.get(position), listener);
        String ingredientName = ingredients.get(position).getName();
        String ingredientWeight = String.valueOf(ingredients.get(position).getAmount());
        holder.ingredientName.setText(ingredientName);
        holder.ingredientData.setText(ingredientWeight);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
