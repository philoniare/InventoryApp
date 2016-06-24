package com.example.philoniare.inventoryapp;

import android.view.View;

public class Utils {
    public interface BtnClickListener {
        public abstract void onBtnClick(View view, int position);
    }
}