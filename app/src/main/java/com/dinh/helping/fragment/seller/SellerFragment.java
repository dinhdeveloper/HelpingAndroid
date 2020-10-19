package com.dinh.helping.fragment.seller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinh.helping.R;

public class SellerFragment extends Fragment {
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_seller, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setVisibility(View.GONE);
        tvTitleHeader.setText("Đăng bài");
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
    }
}