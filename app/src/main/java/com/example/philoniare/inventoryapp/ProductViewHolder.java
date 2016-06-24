package com.example.philoniare.inventoryapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductViewHolder extends RecyclerView.ViewHolder  {
    private Context mContext;
    @BindView(R.id.product_name) TextView productName;
    @BindView(R.id.product_quantity) TextView productQuantity;
    @BindView(R.id.product_price) TextView productPrice;
    @BindView(R.id.product_image) ImageView productImage;

    public ProductViewHolder(View itemView, Context mContext) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = mContext;
    }

    @OnClick(R.id.product_list_container)
    public void onProductClick(View view) {
        // OnClick for the list_item
        Intent detailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
        view.getContext().startActivity(detailIntent);
    }
}
