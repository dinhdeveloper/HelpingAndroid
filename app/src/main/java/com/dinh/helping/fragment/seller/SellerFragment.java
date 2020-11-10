package com.dinh.helping.fragment.seller;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.alert.AlertConfirm;
import com.canhdinh.lib.alert.AlertConfirmSuccess;
import com.canhdinh.lib.alert.AlertError;
import com.canhdinh.lib.alert.AlertSuccess;
import com.canhdinh.lib.alert.CustomAlertDialog;
import com.canhdinh.lib.roundview.CurrencyEditText;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.searchdialog.SimpleSearchDialogCompat;
import com.canhdinh.lib.searchdialog.core.SearchResultListener;
import com.canhdinh.lib.selectimage.BSImagePicker;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.product.ListImageViewAdapter;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.BaseResponseModel;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.model.DistrictModel;
import com.dinh.helping.model.PhotoModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.WardModel;
import com.dinh.helping.viewmodel.category.CategoryViewModel;
import com.dinh.helping.viewmodel.city_district.CityViewModel;
import com.dinh.helping.viewmodel.city_district.DistrictViewModel;
import com.dinh.helping.viewmodel.city_district.WardViewModel;
import com.dinh.helping.viewmodel.product.ProductViewModel;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static com.dinh.helping.activity.HomeActivity.hideSoftKeyboard;

public class SellerFragment extends Fragment implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnSelectImageCancelledListener {
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;

    private RoundTextView tvChooseCategory, tvCity, tvDistrict, tvWard, tvChooseImage, btnSubmit, btnLogin;
    private EditText edtNameProduct, edtStreet, edtDescription, edtQuantity, edtDiscount;
    private ImageView imvProduct;
    private CurrencyEditText edtPrice;
    private TextView tvQuantity;
    private LinearLayout layout_empty;
    private LinearLayout layout_root;

    HomeActivity activity;
    CityViewModel cityViewModel;
    CategoryViewModel categoryViewModel;
    DistrictViewModel districtViewModel;
    WardViewModel wardViewModel;
    ProductViewModel productViewModel;

    String city_id;
    String district_id;
    String ward_id;
    String imageProduct;
    String category_id;

    SharePrefs sharePrefs;

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

        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);
        productViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_seller, container, false);
        addControls(view);
        sharePrefs = new SharePrefs(activity);
        if (sharePrefs.getUserModel() != null) {
            layout_empty.setVisibility(View.GONE);
            layout_root.setVisibility(View.VISIBLE);
            addEvents();
        } else {
            layout_empty.setVisibility(View.VISIBLE);
            layout_root.setVisibility(View.GONE);
            btnBackHeader.setVisibility(View.GONE);
            tvTitleHeader.setText("Đăng bán");
            btnLogin.setOnClickListener(view1 -> {
                productViewModel.changToFragmentLogin(activity);
            });
        }
        hideSoftKeyboard(activity);
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

        tvChooseImage.setOnClickListener(view -> {
            BSImagePicker pickerDialog = new BSImagePicker.Builder("com.dinh.helping.fileprovider")
                    .build();
            pickerDialog.show(getChildFragmentManager(), "picker");

//            BSImagePicker pickerDialog = new BSImagePicker.Builder("com.dinh.helping.fileprovider")
//                    .setMaximumDisplayingImages(Integer.MAX_VALUE)
//                    .isMultiSelect()
//                    .setMinimumMultiSelectCount(1)
//                    .setMaximumMultiSelectCount(10)
//                    .build();
//            pickerDialog.show(getChildFragmentManager(), "picker");
        });

        imvProduct.setOnClickListener(view -> {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            View popupView = layoutInflater.inflate(R.layout.custom_popup_image, null);

            RecyclerView rcListImage = popupView.findViewById(R.id.rcListImage);

            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setView(popupView);
            AlertDialog dialog = alert.create();
            //dialog.setCanceledOnTouchOutside(false);
            dialog.show();

            rcListImage.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            ListImageViewAdapter listImageViewAdapter = new ListImageViewAdapter(activity, arr);
            rcListImage.setAdapter(listImageViewAdapter);
        });

        btnSubmit.setOnClickListener(view -> {
            if (checkInput()) {
                ProductModel model = new ProductModel();
                model.setCategory_id(category_id);
                model.setCategory_name(tvChooseCategory.getText().toString());
                model.setProduct_name(edtNameProduct.getText().toString());
                model.setCity_id(city_id);
                model.setDistrict_id(district_id);
                model.setWard_id(ward_id);
                model.setLocation(edtStreet.getText().toString());
                model.setPrice_sale(edtPrice.getStringValue());
                model.setDescription(edtDescription.getText().toString());
                model.setQuantity(edtQuantity.getText().toString());
                if (!TextUtils.isEmpty(edtDiscount.getText().toString())) {
                    model.setDiscount(edtDiscount.getText().toString());
                } else {
                    model.setDiscount(String.valueOf(0));
                }
                //model.setPhone_contact(sharePrefs.getUserModel().getPhone_number());
                model.setPhone_contact(sharePrefs.getUserModel().getPhone_number());
                if (!TextUtils.isEmpty(imageProduct)) {
                    model.setProduct_image(imageProduct);
                }
//                Date date = new Date();
//                model.setDate_create(new Timestamp(date.getTime()).toString());
                CustomAlertDialog pDialog = new CustomAlertDialog(activity, CustomAlertDialog.PROGRESS_TYPE);
                pDialog.setTitleText("Đang tạo");
                pDialog.setCancelable(false);
                pDialog.show();
                productViewModel.createProduct(model, sharePrefs.getUserModel().getId()).observe(this, model1 -> {

                    if (!TextUtils.isEmpty(model1.getSuccess()) && model1.getSuccess().equalsIgnoreCase("true")) {
                        pDialog.dismissWithAnimation();
                        new CustomAlertDialog(activity, CustomAlertDialog.WARNING_TYPE)
                                .setTitleText("Xác nhận")
                                .setContentText("Bạn có muốn thêm hình ảnh minh họa?")
                                .setConfirmText("Có")
                                .setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(CustomAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                    }
                                })
                                .setCancelButton("Không", new CustomAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(CustomAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        clearDataInput();
                                        new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                                                .setTitleText("Tạo sản phẩm thành công.")
                                                .show();
                                    }
                                })
                                .show();
                    } else {
                        pDialog.dismissWithAnimation();
                        AlertError.showAlertError(activity, model1.getMessage());
                    }
                });
            }
        });
    }

    private void clearDataInput() {
        imageProduct = null;
        Glide.with(activity).load(R.drawable.no_image_full).into(imvProduct);
        tvChooseCategory.setText(null);
        tvChooseCategory.setHint("Chọn");
        tvCity.setText(null);
        tvCity.setHint("Chọn");
        tvDistrict.setText(null);
        tvDistrict.setHint("Chọn");
        tvWard.setText(null);
        tvWard.setHint("Chọn");
        edtStreet.setText(null);
        edtNameProduct.setText(null);
        edtQuantity.setText(null);
        edtDiscount.setText(null);
        edtDescription.setText(null);
    }

    public boolean checkInput() {
        if (TextUtils.isEmpty(imageProduct)) {
            AlertError.showAlertError(activity, "Vui lòng chọn hình ảnh");
            return false;
        }
        if (TextUtils.isEmpty(tvChooseCategory.getText().toString())) {
            AlertError.showAlertError(activity, "Vui lòng chọn danh mục");
            return false;
        }
        if (TextUtils.isEmpty(edtNameProduct.getText().toString())) {
            edtNameProduct.setError("Không để trống");
            edtNameProduct.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(tvCity.getText().toString())) {
            AlertError.showAlertError(activity, "Vui lòng chọn thành phố");
            return false;
        }
        if (TextUtils.isEmpty(tvDistrict.getText().toString())) {
            AlertError.showAlertError(activity, "Vui lòng chọn quận, huyện");
            return false;
        }
        if (TextUtils.isEmpty(tvWard.getText().toString())) {
            AlertError.showAlertError(activity, "Vui lòng chọn xã, phường");
            return false;
        }
        if (TextUtils.isEmpty(edtPrice.getStringValue())) {
            edtPrice.setError("Nhập giá bán");
            edtPrice.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(edtQuantity.getText().toString())) {
            edtQuantity.setError("Nhập số lượng");
            edtQuantity.requestFocus();
            return false;
        }
        return true;
    }

    private void showPopupChooseWard(String district_id) {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập xã phường?", null, getListWard(district_id),
                (SearchResultListener<WardModel>) (dialog1, item, position) -> {
                    ward_id = item.getWard_id();
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
                    category_id = item.getCategory_id();
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
        tvQuantity = view.findViewById(R.id.tvQuantity);
        edtDescription = view.findViewById(R.id.edtDescription);
        edtQuantity = view.findViewById(R.id.edtQuantity);
        edtDiscount = view.findViewById(R.id.edtDiscount);
        layout_empty = view.findViewById(R.id.layout_empty);
        layout_root = view.findViewById(R.id.layout_root);
        btnLogin = view.findViewById(R.id.btnLogin);
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(activity).load(imageUri).into(ivImage);
    }

    List<PhotoModel> arr = new ArrayList<>();
    List<String> uriL = new ArrayList<>();

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {
        Glide.with(activity).load(uriList.get(0)).into(imvProduct);
        for (int i = 0; i < uriList.size(); i++) {
            uriL.add(uriList.get(i).getPath());
            PhotoModel model = new PhotoModel();
            model.setImage_id(String.valueOf(i));
            model.setProduct_photo(String.valueOf(uriList.get(i)));
            arr.add(model);
        }
        if (uriList != null) {
            if (uriList.size() == 1) {
                tvQuantity.setVisibility(View.GONE);
            } else {
                tvQuantity.setVisibility(View.VISIBLE);
                tvQuantity.setText("+" + uriList.size());
                tvQuantity.setBackgroundColor(Color.parseColor("#40000000"));
            }
        }
    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        getRealPathFromURI(uri);
        Glide.with(activity).load(uri).into(imvProduct);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        imageProduct = result;
        return result;
    }
}