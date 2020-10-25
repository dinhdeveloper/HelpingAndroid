package com.dinh.helping.adapter.product;

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
import com.dinh.helping.R;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ProductModel;

import java.util.List;

public class ListProductSearchAdapter extends RecyclerView.Adapter<ListProductSearchAdapter.ViewHolder> {

    private List<ProductModel> list;
    private Context context;
    private ListProductSearchListener listener;

    public interface ListProductSearchListener {
        void onClickItem(ProductModel model);
    }

    public void setListener(ListProductSearchListener listener) {
        this.listener = listener;
    }

    public ListProductSearchAdapter(Context context, List<ProductModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_result_search, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = list.get(position);
        if (!TextUtils.isEmpty(model.getProduct_image())) {
            Glide.with(context).load(Consts.HOST_API + model.getProduct_image()).error(R.drawable.no_image_full).into(holder.imvProduct);
        }
        if (!TextUtils.isEmpty(model.getProduct_name())){
            holder.tvName.setText(model.getProduct_name());
        }
        if (!TextUtils.isEmpty(model.getPrice_sale())){
            holder.tvPrice.setText(Consts.decimalFormat.format(Long.valueOf(model.getPrice_sale()))+" VNĐ");
        }
        if (!TextUtils.isEmpty(model.getLocation())) {
            holder.tvLocation.setText(model.getLocation());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layout_item;
        private ImageView imvProduct;
        private TextView tvName;
        private TextView tvLocation;
        private TextView tvPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_item = itemView.findViewById(R.id.layout_item);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }
}
