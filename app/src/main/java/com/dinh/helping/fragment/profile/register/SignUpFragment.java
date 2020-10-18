package com.dinh.helping.fragment.profile.register;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.alert.AlertSuccess;
import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.canhdinh.lib.selectimage.BSImagePicker;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.customer.ListSexAdapter;
import com.dinh.helping.event.BackLoginFragment;
import com.dinh.helping.helper.AppUtils;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.SexModel;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.viewmodel.customer.CustomerViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.dinh.helping.activity.HomeActivity.hideSoftKeyboard;

public class SignUpFragment extends Fragment implements BSImagePicker.OnSingleImageSelectedListener,
        BSImagePicker.OnMultiImageSelectedListener, BSImagePicker.ImageLoaderDelegate, BSImagePicker.OnSelectImageCancelledListener {

    private EditText edtFullName;
    private EditText edtAddress;
    private TextView edtSex;
    private EditText edtPassword;
    private EditText edtRePassword;
    private RoundLinearLayout btnSubmit;
    private LinearLayout btnLogin;
    private ImageView imvAvatar;

    private ImageView imvVisiblePass, imvVisibleRePass;

    CustomerViewModel viewModel;
    HomeActivity activity;
    String image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = (HomeActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sign_up, container, false);
        viewModel = ViewModelProviders.of(requireActivity()).get(CustomerViewModel.class);
        viewModel.init();
        addControls(view);
        hideSoftKeyboard(activity);
        addEvents();
        return view;
    }

    boolean imvPass = true;
    boolean imvRePass = true;

    private void showPopupSex() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View popupView = layoutInflater.inflate(R.layout.custom_list_sex, null);

        RecyclerView recycler_view_list_sex = popupView.findViewById(R.id.recycler_view_list_sex);
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(popupView);
        AlertDialog dialog = alert.create();
        //dialogChooseService.setCanceledOnTouchOutside(false);
        dialog.show();

        List<SexModel> list = new ArrayList<>();
        list.add(new SexModel("Nam"));
        list.add(new SexModel("Nữ"));

        ListSexAdapter sexAdapter = new ListSexAdapter(getContext(), list);
        recycler_view_list_sex.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view_list_sex.setHasFixedSize(true);
        recycler_view_list_sex.setAdapter(sexAdapter);
        sexAdapter.notifyDataSetChanged();

        sexAdapter.setListener(model -> {
            edtSex.setText(model.getName());
            dialog.dismiss();
        });
    }

    private boolean checkDataInput() {

        if (TextUtils.isEmpty(edtFullName.getText())) {
            edtFullName.setError("Nhập tên đầy đủ");
            edtFullName.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(edtAddress.getText())) {
            edtAddress.setError("Nhập địa chỉ đầy đủ");
            edtAddress.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(edtSex.getText())) {
            edtSex.setError("Chọn giới tính");
            return false;
        }

        if (TextUtils.isEmpty(edtPassword.getText())) {
            edtPassword.setError("Nhập mật khẩu đăng ký");
            edtPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(edtRePassword.getText())) {
            edtRePassword.setError("Nhập lại mật khẩu đăng ký");
            edtRePassword.requestFocus();
            return false;
        }

        return true;
    }

    private void addEvents() {

        btnLogin.setOnClickListener(view -> {
            if (activity!=null){
                activity.checkBack();
                activity.checkBack();
                BackLoginFragment.post();
            }
        });
        imvAvatar.setOnClickListener(view -> {
            BSImagePicker pickerDialog = new BSImagePicker.Builder("com.dinh.helping.fileprovider")
                    .build();
            pickerDialog.show(getChildFragmentManager(), "picker");
        });

        edtSex.setOnClickListener(view -> {
            showPopupSex();
        });

        imvVisiblePass.setOnClickListener(view -> {
            if (imvPass) {
                imvVisiblePass.setImageResource(R.drawable.ic_visible_on);
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imvPass = false;
            } else {
                imvVisiblePass.setImageResource(R.drawable.ic_visible_off);
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imvPass = true;
            }
        });

        imvVisibleRePass.setOnClickListener(view -> {
            if (imvRePass) {
                imvVisibleRePass.setImageResource(R.drawable.ic_visible_on);
                edtRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imvRePass = false;
            } else {
                imvVisibleRePass.setImageResource(R.drawable.ic_visible_off);
                edtRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imvRePass = true;
            }
        });

        btnSubmit.setOnClickListener(view -> {
            if (checkDataInput()) {
                AppUtils.hideKeyBoard(getView());
                UserResponseModel model = new UserResponseModel();
                model.setFull_name(edtFullName.getText().toString());
                model.setPhone_number(viewModel.getPhoneNumber().getValue());
                model.setAddress(edtAddress.getText().toString());
                model.setGender(edtSex.getText().toString());
                if (!TextUtils.isEmpty(image)) {
                    model.setImage(image);
                }
                if (edtPassword.getText().toString().equalsIgnoreCase(edtRePassword.getText().toString())) {
                    model.setPassword(edtPassword.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModel.createCustomer(model);

                viewModel.getCreate().observe(this,user -> {
                    if (user.getSuccess().equalsIgnoreCase("true")) {
                        AlertSuccess.showAlertSuccess(activity,"Xác nhận","Tạo mới thành công");
                        SharePrefs sharePrefs = new SharePrefs(activity);
                        sharePrefs.saveUserModel(Arrays.asList(user.getData()).get(0));
                        activity.checkBack();
                        activity.checkBack();
                        BackLoginFragment.post();
                    } else if (user.getSuccess().equalsIgnoreCase("false")) {
                        Toast.makeText(activity, user.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void addControls(View view) {
        edtFullName = view.findViewById(R.id.edtFullName);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtSex = view.findViewById(R.id.edtSex);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLogin = view.findViewById(R.id.btnLogin);
        imvAvatar = view.findViewById(R.id.imvAvatar);

        imvVisiblePass = view.findViewById(R.id.imvVisiblePass);
        imvVisibleRePass = view.findViewById(R.id.imvVisibleRePass);
    }

    @Override
    public void loadImage(Uri imageUri, ImageView ivImage) {
        Glide.with(activity).load(imageUri).into(ivImage);
    }

    @Override
    public void onMultiImageSelected(List<Uri> uriList, String tag) {

    }

    @Override
    public void onCancelled(boolean isMultiSelecting, String tag) {

    }

    @Override
    public void onSingleImageSelected(Uri uri, String tag) {
        image = uri.getPath();
        Glide.with(activity).load(uri).into(imvAvatar);
    }
}