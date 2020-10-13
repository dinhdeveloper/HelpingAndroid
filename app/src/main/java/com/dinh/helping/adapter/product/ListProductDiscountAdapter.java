package com.dinh.helping.adapter.product;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dinh.helping.R;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ProductModel;

import java.util.List;

public class ListProductDiscountAdapter extends RecyclerView.Adapter<ListProductDiscountAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> list;

    private ListProductDiscountListener listener;

    public interface ListProductDiscountListener {
        void onClickItem(ProductModel model);
    }

    public void setListener(ListProductDiscountListener listener) {
        this.listener = listener;
    }

    public ListProductDiscountAdapter(Context context, List<ProductModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_item_product_discount, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ProductModel model = list.get(position);

        if (!TextUtils.isEmpty(model.getProductName())){
            holder.tvName.setText(model.getProductName());
        }
        if (!TextUtils.isEmpty(model.getPriceSale())){
            holder.tvPrice.setText(Consts.decimalFormat.format(Integer.valueOf(model.getPriceSale()))+" VNĐ");
        }
        if (!TextUtils.isEmpty(model.getProductName())){
            holder.tvDiscount.setText("80%");
        }
        if (!TextUtils.isEmpty(model.getProductImage())){
            Glide.with(context).load(R.drawable.giay4).error(R.drawable.no_image_full).into(holder.imvProduct);
        }

    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout_item;
        ImageView imvProduct;
        TextView tvDiscount, tvPrice, tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_item = itemView.findViewById(R.id.layout_item);
            imvProduct = itemView.findViewById(R.id.imvProduct);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);

            layout_item.setOnClickListener(view -> {
                if (listener!=null)
                    listener.onClickItem(list.get(getAdapterPosition()));
            });
        }
    }
}
