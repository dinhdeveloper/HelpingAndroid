package com.dinh.helping.fragment.profile.verify;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.alert.AlertDialog;
import com.canhdinh.lib.alert.AlertError;
import com.canhdinh.lib.alert.AlertLoading;
import com.canhdinh.lib.alert.CustomAlertDialog;
import com.canhdinh.lib.edittext.FormattedEditText;
import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.textview.PinTextView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.event.BackFragment;
import com.dinh.helping.viewmodel.customer.CustomerViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.dinh.helping.activity.HomeActivity.hideSoftKeyboard;

public class VeryCodeFragment extends Fragment {

    private ImageView tmvClose;
    private EditText edtPhoneNumber;
    private RoundLinearLayout btnSubmit;

    private View layout_phone;
    private View layout_code;

    private TextView tvPhoneInput, tvCancel;
    private PinTextView pinview;
    private RoundTextView btnVerify;
    private View layoutRootView;

    private String verificationID;
    String smsCode;
    HomeActivity activity;

    CustomerViewModel viewModel;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        activity.hideBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_very_code, container, false);
        mAuth = FirebaseAuth.getInstance();
        viewModel = ViewModelProviders.of(requireActivity()).get(CustomerViewModel.class);
        getControls(view);
        hideSoftKeyboard(activity);
        getEvents();

        return view;
    }

    private void getEvents() {
        CustomAlertDialog pDialog = new CustomAlertDialog(activity, CustomAlertDialog.PROGRESS_TYPE);
        tmvClose.setOnClickListener(view -> {
            if (activity != null) {
                activity.checkBack();
                activity.showBottomBar();
                activity.fullScreen();
                BackFragment.post();
            }
        });
        try {
            btnSubmit.setOnClickListener(view -> {
                if (!TextUtils.isEmpty(edtPhoneNumber.getText().toString().trim())) {
                    viewModel.checkPhone(edtPhoneNumber.getText().toString().trim());
                    viewModel.getCheckPhone().observe(this,baseResponseModel -> {
                        if (!TextUtils.isEmpty(baseResponseModel.getSuccess()) && baseResponseModel.getSuccess().equalsIgnoreCase("true")){
                            pDialog.setTitleText("Đang gửi mã");
                            pDialog.setCancelable(false);
                            pDialog.show();
                            PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(PhoneAuthCredential credential) {
                                    if (credential != null) {
                                        smsCode = credential.getSmsCode();
                                    }
                                }

                                @Override
                                public void onVerificationFailed(FirebaseException e) {
                                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                        //The format of the phone number provided is incorrect
                                        //phone numbers are written in the format [+][country code][subscriber number including area code]
                                        pDialog.dismissWithAnimation();
                                        AlertError.showAlertError(getActivity(), "Không thể gửi mã xác thực, hãy kiểm tra lại số điện thoại của bạn.");

                                    } else if (e instanceof FirebaseTooManyRequestsException) {
                                        pDialog.dismissWithAnimation();
                                        AlertError.showAlertError(getActivity(), "Bạn đã gửi quá nhiều yêu cầu xác thực, xin hãy thử lại sau!!!");
                                    }

                                }

                                @Override
                                public void onCodeSent(String verificationId,
                                                       PhoneAuthProvider.ForceResendingToken token) {
                                    verificationID = verificationId;
                                    if (verificationId != null) {
                                        //đã gửi code
                                        pDialog.dismissWithAnimation();
                                        layout_phone.setVisibility(View.GONE);
                                        layout_code.setVisibility(View.VISIBLE);

                                        tvPhoneInput.setText("Mã code gửi cho " + edtPhoneNumber.getText().toString());

                                        btnVerify.setOnClickListener(view1 -> {
                                            signInWithPhoneAuthCredential(pinview.getValue());
//                            if (pinview.getValue().toString().equalsIgnoreCase(smsCode.toString())){
//                                if (activity != null) {
//                                    viewModel.setPhoneNumber(edtPhoneNumber.getText().toString());
//                                    activity.changeToSignUpFragment();
//                                }
//                            }
//                            else {
//                                Toast.makeText(activity, "Nhập sai mã code", Toast.LENGTH_SHORT).show();
//                            }
                                        });
                                    }
                                }
                            };

                            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                    "+84" + edtPhoneNumber.getText().toString(),        // Phone number to verify
                                    60,                 // Timeout duration
                                    TimeUnit.SECONDS,   // Unit of timeout
                                    activity,               // Activity (for callback binding)
                                    mCallbacks);        // OnVerificationStateChangedCallbacks
                        }
                        else {
                            new CustomAlertDialog(activity, CustomAlertDialog.ERROR_TYPE)
                                    .setTitleText(baseResponseModel.getMessage())
                                    .show();
                        }
                    });
                } else {
                    pDialog.dismissWithAnimation();
                    new CustomAlertDialog(activity, CustomAlertDialog.ERROR_TYPE)
                            .setTitleText("Bạn chưa nhập số điện thoại")
                            .show();
                }
            });
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
        }
    }

    private void signInWithPhoneAuthCredential(String smsCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationID, smsCode);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(activity, "Mã xác minh hợp lệ", Toast.LENGTH_SHORT).show();
                if (activity != null) {
                    viewModel.setPhoneNumber(edtPhoneNumber.getText().toString());
                    activity.changeToSignUpFragment();
                }
            } else {
                Toast.makeText(activity, "Mã xác minh không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getControls(View view) {
        tmvClose = view.findViewById(R.id.tmvClose);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        layout_phone = view.findViewById(R.id.layout_phone);
        layout_code = view.findViewById(R.id.layout_code);

        tvPhoneInput = view.findViewById(R.id.tvPhoneInput);
        tvCancel = view.findViewById(R.id.tvCancel);
        pinview = view.findViewById(R.id.pinview);
        btnVerify = view.findViewById(R.id.btnVerify);
        layoutRootView = view.findViewById(R.id.layoutRootView);
    }
}