package com.example.philoniare.inventoryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private Context mContext;

    public ProductViewHolder(View itemView, Context mContext) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.mContext = mContext;
    }


}
