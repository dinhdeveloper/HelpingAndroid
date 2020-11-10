package com.dinh.helping.fragment.login_nomal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.canhdinh.lib.alert.CustomAlertDialog;
import com.canhdinh.lib.roundview.RoundTextView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.helper.SharePrefs;
import com.dinh.helping.model.UserResponseModel;
import com.dinh.helping.viewmodel.customer.CustomerViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class LoginFragment extends Fragment {

    private ImageView btnBackHeader;
    private EditText edtPhoneNumber, edtPassword;
    private ImageView imvVisiblePass;
    private TextView tvForgotPass, tvRegister;
    private RoundTextView btnLoginPhone;
    private LoginButton loginButton;

    CustomerViewModel viewModel;
    SharePrefs sharePrefs;
    HomeActivity activity;
    boolean checkShowPass = false;
    private CallbackManager callbackManager;

    String first_name = "";
    String last_name = "";
    String email = "";
    String id = "";
    String img_url = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        viewModel = ViewModelProviders.of(requireActivity()).get(CustomerViewModel.class);
        viewModel.init();
        activity = (HomeActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_login, container, false);
        addControls(view);
        addEvents();
        loginFacebook();
        sharePrefs = new SharePrefs(activity);
        return view;
    }

    private void loginFacebook() {
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        loginButton.setFragment(this);

        checkLoginStatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                loadUserProfile(accessToken);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.e("AAAAAAAAAA", error.getMessage());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    if (id != null) {
                        first_name = object.getString("first_name");
                        last_name = object.getString("last_name");
                        email = object.getString("email");
                        id = object.getString("id");
                        img_url = "https://avatars.io/facebook/" + id + "/medium";
                        UserResponseModel customer = new UserResponseModel();
                        customer.setFull_name(last_name + " " + first_name);
                        customer.setAddress(email);
                        customer.setId(id);
                        customer.setImage(img_url);
                        //luu vao SharedPreferences
                        sharePrefs.saveUserModel(customer);
                        sharePrefs.saveStatusLogin(true);
                        sharePrefs.saveAccessTokenFB(accessToken.getToken());
                        viewModel.changToFragmentProfile2(activity);
                    }

                    RequestOptions requestOptions = new RequestOptions();
                    requestOptions.dontAnimate();
//                    Glide.with(LoginActivity.this).load(img_url).centerCrop().into(profile_pic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        Bundle paramaster = new Bundle();
        paramaster.putString("fields", "first_name,last_name,email,id");
        request.setParameters(paramaster);
        request.executeAsync();
    }

    private void checkLoginStatus() {
        if (AccessToken.getCurrentAccessToken() != null) {
            loadUserProfile(AccessToken.getCurrentAccessToken());
        }
    }

    private void addEvents() {
        btnBackHeader.setOnClickListener(view -> {
            if (activity != null)
                activity.checkBack();
            activity.showBottomBar();
        });
        tvRegister.setOnClickListener(view -> {
            if (activity != null)
                activity.changeToFragmentVeryCode();
        });
        imvVisiblePass.setOnClickListener(view -> {
            if (checkShowPass) {
                edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imvVisiblePass.setImageResource(R.drawable.ic_visible_on);
                checkShowPass = false;
            } else {
                edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imvVisiblePass.setImageResource(R.drawable.ic_visible_off);
                checkShowPass = true;
            }
        });

        //dang nhap sdt
        btnLoginPhone.setOnClickListener(view -> {
            UserResponseModel model = new UserResponseModel();
            if (!TextUtils.isEmpty(edtPhoneNumber.getText().toString())) {
                model.setPhone_number(edtPhoneNumber.getText().toString());
            } else {
                edtPhoneNumber.setError("Nhập số điện thoại");
                edtPhoneNumber.requestFocus();
                return;
            }
            if (!TextUtils.isEmpty(edtPassword.getText().toString())) {
                model.setPassword(edtPassword.getText().toString());
            } else {
                edtPassword.setError("Nhập mật khẩu");
                edtPassword.requestFocus();
                return;
            }

            viewModel.login(model).observe(this, user -> {

                if (!TextUtils.isEmpty(user.getSuccess()) && user.getSuccess().equalsIgnoreCase("true")) {
                    sharePrefs.saveUserModel(Arrays.asList(user.getData()).get(0));
                    viewModel.changToFragmentProfile(activity, user);
                } else {
                    new CustomAlertDialog(activity, CustomAlertDialog.ERROR_TYPE)
                            .setTitleText(user.getMessage())
                            .show();
                }
            });
        });

        //dang nhap sdt
        edtPassword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                UserResponseModel model = new UserResponseModel();
                if (!TextUtils.isEmpty(edtPhoneNumber.getText().toString())) {
                    model.setPhone_number(edtPhoneNumber.getText().toString());
                } else {
                    edtPhoneNumber.setError("Nhập số điện thoại");
                    edtPhoneNumber.requestFocus();
                    return false;
                }
                if (!TextUtils.isEmpty(edtPassword.getText().toString())) {
                    model.setPassword(edtPassword.getText().toString());
                } else {
                    edtPassword.setError("Nhập mật khẩu");
                    edtPassword.requestFocus();
                    return false;
                }

                viewModel.login(model).observe(this, user -> {

                    if (!TextUtils.isEmpty(user.getSuccess()) && user.getSuccess().equalsIgnoreCase("true")) {
                        sharePrefs.saveUserModel(Arrays.asList(user.getData()).get(0));
                        viewModel.changToFragmentProfile(activity, user);
                    } else {
                        new CustomAlertDialog(activity, CustomAlertDialog.ERROR_TYPE)
                                .setTitleText(user.getMessage())
                                .show();
                    }
                });

                return true;
            }
            return false;
        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        edtPassword = view.findViewById(R.id.edtPassword);
        imvVisiblePass = view.findViewById(R.id.imvVisiblePass);
        tvForgotPass = view.findViewById(R.id.tvForgotPass);
        tvRegister = view.findViewById(R.id.tvRegister);
        btnLoginPhone = view.findViewById(R.id.btnLoginPhone);
        loginButton = view.findViewById(R.id.loginButton);
    }
}