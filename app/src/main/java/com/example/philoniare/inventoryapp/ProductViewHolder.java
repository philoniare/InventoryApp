package com.example.philoniare.inventoryapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProductViewHolder extends RecyclerView.ViewHolder  {
    public TextView productName;
    public TextView productQuantity;
    public TextView productPrice;
    public ImageView productImage;
    private Button saleBtn;
    private LinearLayout productContainer;
    private Utils.BtnClickListener mSaleClickListener;
    private Utils.BtnClickListener mProductClickListener;


    public ProductViewHolder(View itemView, Utils.BtnClickListener saleListener,
                             Utils.BtnClickListener productListener) {
        super(itemView);
        this.mSaleClickListener = saleListener;
        this.mProductClickListener = productListener;
        productName = (TextView) itemView.findViewById(R.id.product_name);
        productQuantity = (TextView) itemView.findViewById(R.id.product_quantity);
        productPrice = (TextView) itemView.findViewById(R.id.product_price);
        productImage = (ImageView) itemView.findViewById(R.id.product_image);
        productContainer = (LinearLayout) itemView.findViewById(R.id.product_list_container);
        saleBtn = (Button) itemView.findViewById(R.id.btn_sale);
        saleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSaleClickListener != null) {
                    mSaleClickListener.onBtnClick(view, getAdapterPosition());
                }
            }
        });
        productContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mProductClickListener != null) {
                    mProductClickListener.onBtnClick(view, getAdapterPosition());
                }
            }
        });
    }
}
