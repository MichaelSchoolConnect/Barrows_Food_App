package com.example.barrowsfoodapplication.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.barrowsfoodapplication.pojo.ProductModel;

public class CategoryListAdapter extends ListAdapter<ProductModel, CategoryViewHolder> {

    public CategoryListAdapter(@NonNull DiffUtil.ItemCallback<ProductModel> diffCallback){
        super(diffCallback);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        return CategoryViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {
        ProductModel current = getItem(position);
        holder.bind(current.name);
    }

    public static class ProductDiff extends DiffUtil.ItemCallback<ProductModel>{
        @Override
        public boolean areItemsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProductModel oldItem, @NonNull ProductModel newItem) {
            return oldItem.name.equals(newItem.name);
        }

    }

}
