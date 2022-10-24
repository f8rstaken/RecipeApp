package com.f8rstaken.recipesapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListingRecyclerAdapter extends RecyclerView.Adapter<ListingRecyclerAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(ListingItem item);
    }

    private ArrayList<ListingItem> items;
    private final OnItemClickListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView listingName;
        private TextView listingDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listingName = itemView.findViewById(R.id.tvListingRecipeName);
            listingDesc = itemView.findViewById(R.id.tvListingRecipeDesc);
        }

        public void bind(final ListingItem item, final OnItemClickListener listener) {
            listingName.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.setTag("TEST!");
                    listener.onItemClick(item);


                }
            });
        }

    }

    public ListingRecyclerAdapter(ArrayList<ListingItem> items, OnItemClickListener listener){
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListingRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_recipes, parent, false);
        return new ViewHolder(listingView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListingRecyclerAdapter.ViewHolder holder, int position) {
        holder.bind(items.get(position), listener);
        String itemName = items.get(position).getName();
        String itemDesc = String.valueOf(items.get(position).getGuide());

        if(itemDesc.length() > 32){
            itemDesc = itemDesc.substring(0, 32) + "...";
        }

        holder.listingName.setText(itemName);
        holder.listingDesc.setText(itemDesc);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
