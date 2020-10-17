package com.dinh.helping.fragment.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.alert.AlertDialog;
import com.canhdinh.lib.alert.AlertError;
import com.canhdinh.lib.alert.AlertLoading;
import com.canhdinh.lib.edittext.FormattedEditText;
import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.textview.PinTextView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VeryCodeFragment extends Fragment {

    private ImageView tmvClose;
    private FormattedEditText edtPhoneNumber;
    private RoundLinearLayout btnSubmit;

    private View layout_phone;
    private View layout_code;

    private TextView tvPhoneInput,tvCancel;
    private PinTextView pinview;
    private RoundTextView btnVerify;

    private String verificationID;
    String smsCode;
    HomeActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_very_code, container, false);

        getControls(view);
        getEvents();

        return view;
    }

    private void getEvents() {
        btnSubmit.setOnClickListener(view -> {
            if (!TextUtils.isEmpty(edtPhoneNumber.getRealText())){
//            PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential credential) {
//                    if (credential != null) {
//                        smsCode = credential.getSmsCode();
//                    }
//                }
//
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                        //The format of the phone number provided is incorrect
//                        //phone numbers are written in the format [+][country code][subscriber number including area code]
//
//                        AlertError.showAlertError(getActivity(), "Không thể gửi mã xác thực, hãy kiểm tra lại số điện thoại của bạn.");
//
//                    } else if (e instanceof FirebaseTooManyRequestsException) {
//                        AlertError.showAlertError(getActivity(), "Bạn đã gửi quá nhiều yêu cầu xác thực, xin hãy thử lại sau!!!");
//                    }
//
//                }
//
//                @Override
//                public void onCodeSent(String verificationId,
//                                       PhoneAuthProvider.ForceResendingToken token) {
//                    verificationID = verificationId;
//                    if (verificationId != null) {
//                        //đã gửi code
//                        layout_phone.setVisibility(View.GONE);
//                        layout_code.setVisibility(View.VISIBLE);
//
//                        tvPhoneInput.setText("Mã code gửi cho "+edtPhoneNumber.getRealText());
//
//                        btnVerify.setOnClickListener(view1 -> {
//                            if (pinview.getValue().equalsIgnoreCase(smsCode)){
//                                Toast.makeText(activity, "ok", Toast.LENGTH_SHORT).show();
//                            }
//                            else {
//                                Toast.makeText(activity, "No ok", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
//                }
//            };

//            PhoneAuthProvider.getInstance().verifyPhoneNumber(
//                    "+84" + edtPhoneNumber.getRealText(),        // Phone number to verify
//                    60,                 // Timeout duration
//                    TimeUnit.SECONDS,   // Unit of timeout
//                    activity,               // Activity (for callback binding)
//                    mCallbacks);        // OnVerificationStateChangedCallbacks
            }
            else {
                Toast.makeText(activity, "Bạn chưa nhập số điện thoại", Toast.LENGTH_SHORT).show();
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
    }
}