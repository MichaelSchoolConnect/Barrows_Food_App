package com.example.barrowsfoodapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.WorkInfo;

import com.example.barrowsfoodapplication.R;
import com.example.barrowsfoodapplication.pojo.ProductModel;

import java.util.Collections;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static String TAG = CategoryAdapter.class.getSimpleName();

    private Context context;

    private LayoutInflater inflater;
    private List<ProductModel> data = Collections.emptyList();

    public CategoryAdapter(Context context, List<String> productModels){
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }


    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ProductViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ProductViewHolder productViewHolder = (ProductViewHolder) holder;
        final ProductModel productModel = data.get(position);
        productViewHolder.textView.setText(productModel.name);

        //Set the Recyclerview onClick and pass data to an Intent
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Log.i(TAG, "Position: " + position + "House Name: " + houses.name
                        + " House Id: " + houses.id);
                Intent intent = new Intent(context, HousesInfoActivity.class);
                intent.putExtra("id", houses.id);
                context.startActivity(intent);*/
            }
        });
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView textView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
