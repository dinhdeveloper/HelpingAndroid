package com.dinh.helping.adapter.customer;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dinh.helping.R;
import com.dinh.helping.model.SexModel;

import java.util.List;

public class ListSexAdapter extends RecyclerView.Adapter<ListSexAdapter.ViewHolder> {
    Context context;
    List<SexModel> list;

    public ListSexAdapter(Context context, List<SexModel> list) {
        this.context = context;
        this.list = list;
    }

    private ListSexListener listener;

    // if checkedPosition = -1, there is no default selection
    // if checkedPosition = 0, 1st item is selected by default
    private int checkedPosition = -1;

    public void setListener(ListSexListener listener) {
        this.listener = listener;
    }

    public interface ListSexListener {
        void onItemClickListener(SexModel model);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.choose_item_sex,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        SexModel model = list.get(i);
        if (!model.getName().isEmpty()){
            viewHolder.tvName.setText(model.getName());
        }
        viewHolder.layoutName.setOnClickListener(view -> {
            if (listener!=null)
                listener.onItemClickListener(model);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        LinearLayout layoutName;
        ImageView imgChooseName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            layoutName = itemView.findViewById(R.id.layoutName);
            imgChooseName = itemView.findViewById(R.id.imgChooseName);
        }

//        void bind(final SexModel item){
//            try{
//                if (checkedPosition == -1) {
//                    imgChooseName.setVisibility(View.GONE);
//                } else {
//                    if (checkedPosition == getAdapterPosition()) {
//                        tvName.setTextColor(Color.parseColor("#F8B24F"));
//                        viewName.setBackgroundColor(Color.parseColor("#F8B24F"));
//                        imgChooseName.setVisibility(View.VISIBLE);
//                    } else {
//                        imgChooseName.setVisibility(View.GONE);
//                    }
//                }
//                tvName.setText(item.getName());
//                itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        imgChooseName.setVisibility(View.VISIBLE);
//                        if (checkedPosition != getAdapterPosition()) {
//                            notifyItemChanged(checkedPosition);
//                            checkedPosition = getAdapterPosition();
//                            if (listener!=null)
//                                listener.onItemClickListener(item);
//                        }
//                    }
//                });
//            }catch (Exception e){
//                Log.e("Exception",e.getMessage());
//            }
//        }
    }
}
