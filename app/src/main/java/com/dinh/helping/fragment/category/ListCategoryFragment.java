package com.dinh.helping.fragment.category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.canhdinh.lib.recyclerview.XRecyclerView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.category.ListCategoryAdapter;
import com.dinh.helping.event.BackFragment;
import com.dinh.helping.viewmodel.category.CategoryViewModel;

import java.util.Arrays;

public class ListCategoryFragment extends Fragment {

    private ImageView btnBackHeader;
    private TextView tvTitleHeader;
    private RecyclerView rcListCategory;

    CategoryViewModel viewModel;
    HomeActivity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity)getActivity();
        viewModel = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_list_category, container, false);
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
        viewModel.getListCategory().observe(this, model -> {
            ListCategoryAdapter categoryAdapter = new ListCategoryAdapter(getContext(), Arrays.asList(model.getData()));
            rcListCategory.setHasFixedSize(true);
            rcListCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rcListCategory.setAdapter(categoryAdapter);
            categoryAdapter.notifyDataSetChanged();
            categoryAdapter.setListener(model1 -> {

            });
        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
        rcListCategory = view.findViewById(R.id.rcListCategory);
    }
}