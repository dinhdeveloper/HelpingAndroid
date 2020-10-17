package com.dinh.helping.adapter.product;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.labelview.LabelImageView;
import com.canhdinh.lib.roundview.RoundTextView;
import com.dinh.helping.R;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ProductModel;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> list;

    private ListProductListener listener;

    public interface ListProductListener {
        void onClickItem(ProductModel model);
    }

    public void setListener(ListProductListener listener) {
        this.listener = listener;
    }

    public ListProductAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_product_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = list.get(position);

        if (!TextUtils.isEmpty(model.getProduct_name())){
            holder.tvName.setText(model.getProduct_name());
        }
        if (!TextUtils.isEmpty(model.getPrice_sale())){
            holder.tvPrice.setText(Consts.decimalFormat.format(Integer.valueOf(model.getPrice_sale()))+" VNĐ");
        }
        if (!TextUtils.isEmpty(model.getLocation())){
            holder.tvLocation.setText(model.getLocation());
        }
        if (!TextUtils.isEmpty(model.getProduct_image())){
            Glide.with(context).load(Consts.HOST_API+model.getProduct_image()).error(R.drawable.no_image_full).into(holder.imvCategory);
        }

        holder.layout_item.setOnClickListener(view -> {
            if (listener!=null)
                listener.onClickItem(model);
        });

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout_item;
        ImageView imvCategory;
        TextView tvName, tvPrice, tvLocation;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_item = itemView.findViewById(R.id.layout_item);
            imvCategory = itemView.findViewById(R.id.imvCategory);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }
}
