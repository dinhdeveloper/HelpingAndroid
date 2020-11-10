package com.dinh.helping.adapter.product;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinh.helping.R;
import com.dinh.helping.model.PhotoModel;

import java.util.List;

public class ListImageViewAdapter extends RecyclerView.Adapter<ListImageViewAdapter.ViewHolder> {

    private Context context;
    private List<PhotoModel> list;

    public ListImageViewAdapter(Context context, List<PhotoModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_image,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PhotoModel model = list.get(position);
        if (!TextUtils.isEmpty(model.getProduct_photo())){
            Glide.with(context).load(model.getProduct_photo()).into(holder.imvItem);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imvItem = itemView.findViewById(R.id.imvItem);
        }
    }
}
