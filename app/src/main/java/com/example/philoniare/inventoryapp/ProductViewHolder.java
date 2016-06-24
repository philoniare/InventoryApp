package com.example.philoniare.inventoryapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductViewHolder extends RecyclerView.ViewHolder  {
    @BindView(R.id.product_name) TextView productName;
    @BindView(R.id.product_quantity) TextView productQuantity;
    @BindView(R.id.product_price) TextView productPrice;
    @BindView(R.id.product_image) ImageView productImage;
    private Utils.BtnClickListener mSaleClickListener;
    private Utils.BtnClickListener mProductClickListener;


    public ProductViewHolder(View itemView, Utils.BtnClickListener saleListener,
                             Utils.BtnClickListener productListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mSaleClickListener = saleListener;
        this.mProductClickListener = productListener;
    }

    @OnClick(R.id.btn_sale)
    public void onSaleClick(View view) {
        if(mSaleClickListener != null) {
            mSaleClickListener.onBtnClick(view, getAdapterPosition());
        }
    }

    @OnClick(R.id.product_list_container)
    public void onProductClick(View view) {
        if(mProductClickListener != null) {
            mProductClickListener.onBtnClick(view, getAdapterPosition());
        }
    }
}
