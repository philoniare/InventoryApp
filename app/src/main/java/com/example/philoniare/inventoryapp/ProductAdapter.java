package com.example.philoniare.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.philoniare.inventoryapp.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
    private List<Product> mProductList;
    private Context mContext;
    private Utils.BtnClickListener mClickListener;

    public ProductAdapter(Context context, List<Product> products, Utils.BtnClickListener listener) {
        this.mProductList = products;
        this.mContext = context;
        this.mClickListener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_list_item, null);
        ProductViewHolder pvh = new ProductViewHolder(layoutView, mClickListener, new Utils.BtnClickListener() {
            @Override
            public void onBtnClick(View view, int position) {
                Product currentProduct = mProductList.get(position);
                Bundle arguments = new Bundle();
                arguments.putString("productName", currentProduct.getName());
                arguments.putInt("productQuantity", currentProduct.getQuantity());
                arguments.putDouble("productPrice", currentProduct.getPrice());
                Intent detailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
                detailIntent.putExtras(arguments);
                view.getContext().startActivity(detailIntent);
            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        // Bind model with list item
        Product currentProduct = mProductList.get(position);
        holder.productName.setText(currentProduct.getName());
        holder.productQuantity.setText(String.format(Locale.ENGLISH,
                mContext.getString(R.string.product_quantity_formatter),
                currentProduct.getQuantity()));
        holder.productPrice.setText(String.format(Locale.ENGLISH,
                mContext.getString(R.string.product_price_formatter), currentProduct.getPrice()));
        Picasso.with(mContext).load("http://manvelsoccer.org/wp-content/uploads/2014/02/soccer.jpg")
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return this.mProductList.size();
    }
}
