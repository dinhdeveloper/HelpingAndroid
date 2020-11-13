package com.dinh.helping.fragment.profile.info;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.alert.CustomAlertDialog;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.viewmodel.customer.CustomerViewModel;


public class ProfileFragment extends Fragment {

    private LinearLayout layoutLoginNoActive, layoutLoginActive;
    private LinearLayout btnLogout;
    private TextView tvFullName, tvPhoneNumber, tvAddress;
    private ImageView imvProfile;
    private LinearLayout layoutMyProduct;

    HomeActivity activity;
    CustomerViewModel customerViewModel;
    SharePrefs sharePrefs;


    boolean checkLogout= false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerViewModel = ViewModelProviders.of(requireActivity()).get(CustomerViewModel.class);
        customerViewModel.init();
        activity = (HomeActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        addControls(view);
        sharePrefs = new SharePrefs(activity);
        addEvents();
        return view;
    }

    private void addEvents() {
        UserResponseModel model = sharePrefs.getUserModel();
        if (model!=null){
            layoutLoginNoActive.setVisibility(View.GONE);
            layoutLoginActive.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(model.getFull_name())) {
                tvFullName.setText(model.getFull_name());
            }
            if (!TextUtils.isEmpty(model.getPhone_number())) {
                tvPhoneNumber.setText(model.getPhone_number());
            }
            if (!TextUtils.isEmpty(model.getAddress())) {
                tvAddress.setText(model.getAddress());
            }
            if (!TextUtils.isEmpty(model.getImage())) {
                Glide.with(activity).load(model.getImage()).error(R.drawable.ic_businessman_1).into(imvProfile);
            }

            onClickActive();
        }
        else {
                            layoutLoginNoActive.setVisibility(View.VISIBLE);
                layoutLoginActive.setVisibility(View.GONE);
        }
//        customerViewModel.getCheckLogin().observe(this, aBoolean -> {
//            if (aBoolean) {
//                layoutLoginNoActive.setVisibility(View.GONE);
//                layoutLoginActive.setVisibility(View.VISIBLE);
//                UserResponseModel model = sharePrefs.getUserModel();
//                if (!TextUtils.isEmpty(model.getFull_name())) {
//                    tvFullName.setText(model.getFull_name());
//                }
//                if (!TextUtils.isEmpty(model.getPhone_number())) {
//                    tvPhoneNumber.setText(model.getPhone_number());
//                }
//                if (!TextUtils.isEmpty(model.getAddress())) {
//                    tvAddress.setText(model.getAddress());
//                }
//                if (!TextUtils.isEmpty(model.getImage())){
//                    Glide.with(activity).load(model.getImage()).error(R.drawable.ic_businessman_1).into(imvProfile);
//                }
//
//                onClickActive();
//            } else {
//                layoutLoginNoActive.setVisibility(View.VISIBLE);
//                layoutLoginActive.setVisibility(View.GONE);
//            }
//        });

        layoutLoginNoActive.setOnClickListener(view -> {
            customerViewModel.changToFragmentLogin(activity);
        });

    }

    private void onClickActive() {

        layoutMyProduct.setOnClickListener(view -> {
            customerViewModel.changToFragmentMyProduct(activity);
        });

        btnLogout.setOnClickListener(view -> {
            new CustomAlertDialog(activity, CustomAlertDialog.WARNING_TYPE)
                    .setTitleText("Xác nhận")
                    .setContentText("Bạn có muốn đăng xuất?")
                    .setConfirmText("Có")
                    .setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(CustomAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            layoutLoginNoActive.setVisibility(View.VISIBLE);
                            layoutLoginActive.setVisibility(View.GONE);
                            sharePrefs.saveUserModel(null);
                            sharePrefs.saveStatusLogin(false);
                            new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Đăng xuất thành công.")
                                    .show();

                            checkLogout = false;
                        }
                    })
                    .setCancelButton("Không", new CustomAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(CustomAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
//            if (checkLogout){
//                if (!TextUtils.isEmpty(sharePrefs.getAssetTokenFB()) && sharePrefs.getAssetTokenFB()!=null){
//                    new CustomAlertDialog(activity, CustomAlertDialog.WARNING_TYPE)
//                            .setTitleText("Xác nhận")
//                            .setContentText("Bạn có muốn đăng xuất facebook?")
//                            .setConfirmText("Có")
//                            .setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(CustomAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                    layoutLoginNoActive.setVisibility(View.VISIBLE);
//                                    layoutLoginActive.setVisibility(View.GONE);
//                                    customerViewModel.setCheckLogin();
//                                    sharePrefs.saveUserModel(null);
//                                    sharePrefs.saveStatusLogin(false);
//                                    sharePrefs.saveAccessTokenFB(null);
//                                    LoginManager.getInstance().logOut();
//                                    new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText("Đăng xuất thành công.")
//                                            .show();
//
//                                    checkLogout = false;
//                                }
//                            })
//                            .setCancelButton("Không", new CustomAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(CustomAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
//                }
//                else {
//                    new CustomAlertDialog(activity, CustomAlertDialog.WARNING_TYPE)
//                            .setTitleText("Xác nhận")
//                            .setContentText("Bạn có muốn đăng xuất?")
//                            .setConfirmText("Có")
//                            .setConfirmClickListener(new CustomAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(CustomAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                    layoutLoginNoActive.setVisibility(View.VISIBLE);
//                                    layoutLoginActive.setVisibility(View.GONE);
//                                    customerViewModel.setCheckLogin();
//                                    sharePrefs.saveUserModel(null);
//                                    sharePrefs.saveStatusLogin(false);
//                                    new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
//                                            .setTitleText("Đăng xuất thành công.")
//                                            .show();
//
//                                    checkLogout = false;
//                                }
//                            })
//                            .setCancelButton("Không", new CustomAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(CustomAlertDialog sDialog) {
//                                    sDialog.dismissWithAnimation();
//                                }
//                            })
//                            .show();
//                }
//            }
        });
    }


    private void addControls(View view) {
        layoutLoginNoActive = view.findViewById(R.id.layoutLoginNoActive);
        layoutLoginActive = view.findViewById(R.id.layoutLoginActive);
        tvFullName = view.findViewById(R.id.tvFullName);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvAddress = view.findViewById(R.id.tvAddress);
        imvProfile = view.findViewById(R.id.imvProfile);

        btnLogout = view.findViewById(R.id.btnLogout);
        layoutMyProduct = view.findViewById(R.id.layoutMyProduct);
    }
}