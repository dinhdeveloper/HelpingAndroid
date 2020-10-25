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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

        if (!TextUtils.isEmpty(model.getDate_create())) {
            tinhThoiGian(holder.tvTimes, model.getDate_create());
        }

        if (!TextUtils.isEmpty(model.getProduct_name())) {
            holder.tvName.setText(model.getProduct_name());
        }
        if (!TextUtils.isEmpty(model.getPrice_sale())) {
            holder.tvPrice.setText(Consts.decimalFormat.format(Integer.valueOf(model.getPrice_sale())) + " VNĐ");
        }
        if (!TextUtils.isEmpty(model.getLocation())) {
            holder.tvLocation.setText(model.getLocation());
        }
        if (!TextUtils.isEmpty(model.getProduct_image())) {
            Glide.with(context).load(Consts.HOST_API + model.getProduct_image()).error(R.drawable.no_image_full).into(holder.imvCategory);
            holder.progressBar.setVisibility(View.GONE);
        }

        holder.layout_item.setOnClickListener(view -> {
            if (listener != null)
                listener.onClickItem(model);
        });

    }

    private void tinhThoiGian(TextView tvTimes, String date_create) {
        Date dateTime = null;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(date_create);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuffer times = new StringBuffer();
        Date current = Calendar.getInstance().getTime();
        long diffInSeconds = (current.getTime() - dateTime.getTime()) / 1000;

    /*long diff[] = new long[]{0, 0, 0, 0};
    /* sec *  diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
    /* min *  diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
    /* hours *  diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
    /* days * diff[0] = (diffInSeconds = (diffInSeconds / 24));
     */
        long sec = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
        long min = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
        long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
        long days = (diffInSeconds = (diffInSeconds / 24)) >= 30 ? diffInSeconds % 30 : diffInSeconds;
        long months = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12 : diffInSeconds;
        long years = (diffInSeconds = (diffInSeconds / 12));

        if (years > 0) {
            if (years == 1) {
                times.append("1 năm");
            } else {
                times.append(years + " năm");
            }
            if (years <= 6 && months > 0) {
                if (months == 1) {
                    times.append(" ,một tháng");
                } else {
                    times.append(", " + months + " tháng");
                }
            }
        } else if (months > 0) {
            if (months == 1) {
                times.append("1 tháng");
            } else {
                times.append(months + " tháng");
            }
            if (months <= 6 && days > 0) {
                if (days == 1) {
                    times.append(" ,một ngày");
                } else {
                    times.append(", " + days + " ngày");
                }
            }
        } else if (days > 0) {
            if (days == 1) {
                times.append("1 ngày");
            } else {
                times.append(days + " ngày");
            }
            if (days <= 3 && hrs > 0) {
                if (hrs == 1) {
                    times.append(",một giờ");
                } else {
                    times.append(", " + hrs + " giờ");
                }
            }
        } else if (hrs > 0) {
            if (hrs == 1) {
                times.append("1 giờ");
            } else {
                times.append(hrs + " giờ");
            }
            if (min > 1) {
                times.append(", " + min + " phút");
            }
        } else if (min > 0) {
            if (min == 1) {
                times.append("1 phút");
            } else {
                times.append(min + " phút");
            }
            if (sec > 1) {
                times.append(", " + sec + " giây");
            }
        } else {
            if (sec <= 1) {
                times.append("khoảng một giây");
            } else {
                times.append(" khoảng " + sec + " giây");
            }
        }

        times.append(" trước");

        tvTimes.setText(times.toString());
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layout_item;
        ImageView imvCategory;
        TextView tvName, tvPrice, tvLocation, tvTimes;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            layout_item = itemView.findViewById(R.id.layout_item);
            imvCategory = itemView.findViewById(R.id.imvCategory);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTimes = itemView.findViewById(R.id.tvTimes);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            progressBar = itemView.findViewById(R.id.progressBar);

        }
    }

}
