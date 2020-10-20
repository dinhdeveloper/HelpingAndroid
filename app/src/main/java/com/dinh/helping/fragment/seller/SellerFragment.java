package com.dinh.helping.fragment.seller;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.searchdialog.SimpleSearchDialogCompat;
import com.canhdinh.lib.searchdialog.core.SearchResultListener;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.model.DistrictModel;
import com.dinh.helping.model.WardModel;
import com.dinh.helping.viewmodel.category.CategoryViewModel;
import com.dinh.helping.viewmodel.city_district.CityViewModel;
import com.dinh.helping.viewmodel.city_district.DistrictViewModel;
import com.dinh.helping.viewmodel.city_district.WardViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class SellerFragment extends Fragment {
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;

    private RoundTextView tvChooseCategory, tvCity, tvDistrict, tvWard, tvChooseImage, btnSubmit;
    private EditText edtNameProduct, edtStreet, edtPrice, edtDescription;
    private ImageView imvProduct;

    HomeActivity activity;
    CityViewModel cityViewModel;
    CategoryViewModel categoryViewModel;
    DistrictViewModel districtViewModel;
    WardViewModel wardViewModel;

    String city_id;
    String district_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        cityViewModel = ViewModelProviders.of(requireActivity()).get(CityViewModel.class);
        cityViewModel.init();

        categoryViewModel = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
        categoryViewModel.init();

        districtViewModel = ViewModelProviders.of(requireActivity()).get(DistrictViewModel.class);
        districtViewModel.init();

        wardViewModel = ViewModelProviders.of(requireActivity()).get(WardViewModel.class);
        wardViewModel.init();
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
        tvTitleHeader.setText("Đăng bán");

        tvChooseCategory.setOnClickListener(view -> {
            showPopupChooseCategory();
        });

        tvCity.setOnClickListener(view -> {
            showPopupChooseCity();
        });

        tvDistrict.setOnClickListener(view -> {
            showPopupChooseDistrict(city_id);
        });

        tvWard.setOnClickListener(view -> {
            showPopupChooseWard(district_id);
        });
    }

    private void showPopupChooseWard(String district_id) {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập xã phường?", null, getListWard(district_id),
                (SearchResultListener<WardModel>) (dialog1, item, position) -> {
                    tvWard.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }


    private void showPopupChooseDistrict(String city_id) {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập quận huyện?", null, getListDistrict(city_id),
                (SearchResultListener<DistrictModel>) (dialog1, item, position) -> {
                    district_id = item.getDistrict_id();
                    tvDistrict.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private void showPopupChooseCity() {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập thành phố?", null, getListCity(),
                (SearchResultListener<CityModel>) (dialog1, item, position) -> {

                    city_id = item.getCity_id();
                    tvCity.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private void showPopupChooseCategory() {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập danh mục sản phẩm?", null, getListCategory(),
                (SearchResultListener<CategoryModel>) (dialog1, item, position) -> {
                    tvChooseCategory.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private ArrayList getListCategory() {
        ArrayList<CategoryModel> list = new ArrayList<>();
        list.addAll(Arrays.asList(categoryViewModel.getListCategory().getValue().getData()));
        return list;
    }

    private ArrayList getListCity() {
        ArrayList<CityModel> list = new ArrayList<>();
        list.addAll(Arrays.asList(cityViewModel.getListCity().getValue().getData()));
        return list;
    }

    private ArrayList getListDistrict(String city_id) {
        districtViewModel.getListDistrictByCity(city_id);
        ArrayList<DistrictModel> list = new ArrayList<>();
        districtViewModel.getListDistrictByCity().observe(this, district -> {
            list.addAll(Arrays.asList(district.getData()));
        });
        return list;
    }

    private ArrayList getListWard(String district_id) {
        wardViewModel.getListWardByDistrict(district_id);
        ArrayList<WardModel> list = new ArrayList<>();
        wardViewModel.getListWardByDistrict().observe(this, ward -> {
            list.addAll(Arrays.asList(ward.getData()));
        });
        return list;
    }


    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);

        tvChooseCategory = view.findViewById(R.id.tvChooseCategory);
        tvCity = view.findViewById(R.id.tvCity);
        tvDistrict = view.findViewById(R.id.tvDistrict);
        tvWard = view.findViewById(R.id.tvWard);
        tvChooseImage = view.findViewById(R.id.tvChooseImage);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        edtNameProduct = view.findViewById(R.id.edtNameProduct);
        edtStreet = view.findViewById(R.id.edtStreet);
        edtPrice = view.findViewById(R.id.edtPrice);
        imvProduct = view.findViewById(R.id.imvProduct);
    }
}