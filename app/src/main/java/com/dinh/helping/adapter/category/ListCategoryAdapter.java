package com.dinh.helping.adapter.category;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.dinh.helping.R;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.CategoryModel;

import java.util.List;

public class ListCategoryAdapter extends RecyclerView.Adapter<ListCategoryAdapter.ViewHolder> {
    Context context;
    List<CategoryModel> list;
    private ListCategoryListener listener;

    public interface ListCategoryListener {
        void onClickItem(CategoryModel model);
    }

    public void setListener(ListCategoryListener listener) {
        this.listener = listener;
    }

    public ListCategoryAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_category_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryModel model = list.get(position);
        if (model.getCategory_id().equalsIgnoreCase("9")) {
            holder.tvName.setText("Khác");
            Glide.with(context).load(R.drawable.ic_more_1).error(R.drawable.no_image_full).into(holder.imvItem);
        } else {
            if (!TextUtils.isEmpty(model.getCategory_name())) {
                holder.tvName.setText(model.getCategory_name());
            }
            if (!TextUtils.isEmpty(model.getCategory_image())) {
                Glide.with(context).load(Consts.HOST_API + model.getCategory_image()).error(R.drawable.no_image_full).into(holder.imvItem);
            }
        }
        holder.layout_item.setOnClickListener(view -> {
            if (listener != null)
                listener.onClickItem(model);
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), 8);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imvItem;
        private TextView tvName;
        private LinearLayout layout_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imvItem = itemView.findViewById(R.id.imvItem);
            tvName = itemView.findViewById(R.id.tvName);
            layout_item = itemView.findViewById(R.id.layout_item);
        }
    }
}
