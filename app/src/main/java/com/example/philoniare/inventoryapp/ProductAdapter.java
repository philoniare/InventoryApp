package com.example.philoniare.inventoryapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import butterknife.internal.Utils;

public class ProductAdapter extends RecyclerView.Adapter<HabitViewHolder> {

    public HabitAdapter(Context context, List<Habit> mHabitList, Utils.BtnClickListener listener) {
        this.mHabitList = mHabitList;
        this.mContext = context;
        this.mClickListener = listener;
    }
}
