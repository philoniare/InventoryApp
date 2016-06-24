package com.example.philoniare.inventoryapp;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.philoniare.inventoryapp.model.Product;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class ProductViewHolder extends RecyclerView.ViewHolder  {
    private Product mCurrentProduct;
    private Realm realm;
    @BindView(R.id.product_name) TextView productName;
    @BindView(R.id.product_quantity) TextView productQuantity;
    @BindView(R.id.product_price) TextView productPrice;
    @BindView(R.id.product_image) ImageView productImage;

    public ProductViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }


    @OnClick(R.id.product_list_container)
    public void onProductClick(View view) {
        // OnClick for the list_item
        Intent detailIntent = new Intent(view.getContext(), ProductDetailActivity.class);
        view.getContext().startActivity(detailIntent);
    }
}
