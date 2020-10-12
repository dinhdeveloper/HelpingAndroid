package com.dinh.helping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ViewsSliderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    int[] layouts;

    public ViewsSliderAdapter(Context context, int[] layouts) {
        this.context = context;
        this.layouts = layouts;
    }

    public ViewsSliderAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewType, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return layouts[position];
    }

    @Override
    public int getItemCount() {
        return layouts.length;
    }

    public class SliderViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public SliderViewHolder(View view) {
            super(view);
        }
    }
}