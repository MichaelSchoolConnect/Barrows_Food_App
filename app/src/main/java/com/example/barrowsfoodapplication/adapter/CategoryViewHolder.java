package com.example.barrowsfoodapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.barrowsfoodapplication.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    private final TextView productItemView;

    private CategoryViewHolder(View itemView) {
        super(itemView);
        productItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        productItemView.setText(text);
    }

    static CategoryViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new CategoryViewHolder(view);
    }

}
