package com.dinh.helping.fragment.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.event.BackFragment;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.viewmodel.category.CategoryViewModel;

public class CategoryFragment extends Fragment {

    CategoryViewModel viewModel;
    HomeActivity activity;

    private ImageView btnBackHeader;
    private TextView tvTitleHeader;
    private RecyclerView rcListCategory;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        viewModel = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_category, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setOnClickListener(view -> {
            if (activity!=null){
                activity.checkBack();
                BackFragment.post();
            }
        });
        tvTitleHeader.setText("Danh mục");

        viewModel.getProductByCategory().observe(this, model -> {

        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
        rcListCategory = view.findViewById(R.id.rcListCategory);
    }
}