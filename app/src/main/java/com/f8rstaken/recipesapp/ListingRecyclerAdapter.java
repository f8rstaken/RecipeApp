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
        private TextView listingSubmitter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listingName = itemView.findViewById(R.id.tvListingRecipeName);
            listingSubmitter = itemView.findViewById(R.id.tvListingSubmitter);
        }

        public void bind(final ListingItem item, final OnItemClickListener listener) {
            listingName.setText(item.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        String itemSubmitter = items.get(position).getSubmitter();

        holder.listingName.setText(itemName);
        holder.listingSubmitter.setText(itemSubmitter);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}
