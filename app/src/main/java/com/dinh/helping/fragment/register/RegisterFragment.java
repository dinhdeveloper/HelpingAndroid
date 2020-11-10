package com.dinh.helping.fragment.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.alert.AlertSuccess;
import com.canhdinh.lib.alert.CustomAlertDialog;
import com.canhdinh.lib.roundview.RoundTextView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.event.BackLoginFragment;
import com.dinh.helping.helper.AppUtils;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.viewmodel.customer.CustomerViewModel;

import java.util.Arrays;

import static com.dinh.helping.activity.HomeActivity.hideSoftKeyboard;

public class RegisterFragment extends Fragment {
    private ImageView btnBackHeader;
    private TextView tvLogin;
    private RoundTextView btnLoginPhone;
    private ImageView imvVisiblePass,imvReVisiblePass;
    private EditText edtFullName, edtAddress, edtPassword, edtRePassword;

    HomeActivity activity;
    CustomerViewModel viewModel;
    boolean imvPass = true;
    boolean imvRePass = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireActivity()).get(CustomerViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_register, container, false);
        activity = (HomeActivity) getActivity();
        addControls(view);
        hideSoftKeyboard(activity);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setOnClickListener(view -> {
            if (activity != null)
                activity.checkBack();
                activity.checkBack();
            activity.hideBottomBar();
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

        imvReVisiblePass.setOnClickListener(view -> {
            if (imvRePass) {
                imvReVisiblePass.setImageResource(R.drawable.ic_visible_on);
                edtRePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imvRePass = false;
            } else {
                imvReVisiblePass.setImageResource(R.drawable.ic_visible_off);
                edtRePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imvRePass = true;
            }
        });

        btnLoginPhone.setOnClickListener(view -> {
            if (checkDataInput()) {
                AppUtils.hideKeyBoard(getView());
                UserResponseModel model = new UserResponseModel();
                model.setFull_name(edtFullName.getText().toString());
                model.setPhone_number(viewModel.getPhoneNumber().getValue());
                model.setAddress(edtAddress.getText().toString());

                if (edtPassword.getText().toString().equalsIgnoreCase(edtRePassword.getText().toString())) {
                    model.setPassword(edtPassword.getText().toString());
                } else {
                    Toast.makeText(getContext(), "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }
                viewModel.createCustomer(model);

                viewModel.getCreate().observe(this,user -> {
                    if (user.getSuccess().equalsIgnoreCase("true")) {
                        new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Đăng ký thành công.")
                                .show();
                        SharePrefs sharePrefs = new SharePrefs(activity);
                        sharePrefs.saveUserModel(Arrays.asList(user.getData()).get(0));
                        activity.checkBack();
                        activity.checkBack();
                        BackLoginFragment.post();
                    } else if (user.getSuccess().equalsIgnoreCase("false")) {
                        new CustomAlertDialog(activity, CustomAlertDialog.SUCCESS_TYPE)
                                .setTitleText(user.getMessage())
                                .show();
                    }
                });
            }
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


    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        edtFullName = view.findViewById(R.id.edtFullName);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        tvLogin = view.findViewById(R.id.tvLogin);
        btnLoginPhone = view.findViewById(R.id.btnLoginPhone);
        imvReVisiblePass = view.findViewById(R.id.imvReVisiblePass);
        imvVisiblePass = view.findViewById(R.id.imvVisiblePass);
    }
}