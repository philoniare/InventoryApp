package com.example.philoniare.inventoryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philoniare.inventoryapp.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> mProductList;
    private Context mContext;

    public ProductAdapter(Context context, List<Product> mProducts) {
        this.mProductList = mProductList;
        this.mContext = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, null);
        ProductViewHolder pvh = new ProductViewHolder(layoutView, mContext);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        // Bind model with list item
        Product currentProduct = mProductList.get(position);
        //holder.habitNameTV.setText(currentHabit.getName());
        //holder.habitCountTV.setText("Count: " + Integer.toString(currentHabit.getCompletionCount()));
    }

    @Override
    public int getItemCount() {
        return this.mProductList.size();
    }
}
