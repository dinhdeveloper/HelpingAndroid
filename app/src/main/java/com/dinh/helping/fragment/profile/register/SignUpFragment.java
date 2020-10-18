package com.dinh.helping.fragment.profile.register;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.dinh.helping.R;

public class SignUpFragment extends Fragment {

    private EditText edtFullName;
    private EditText edtAddress;
    private TextView edtSex;
    private EditText edtPassword;
    private EditText edtRePassword;
    private RoundLinearLayout btnSubmit;
    private LinearLayout btnLogin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_sign_up, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {

    }

    private void addControls(View view) {
        edtFullName = view.findViewById(R.id.edtFullName);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtSex = view.findViewById(R.id.edtSex);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtRePassword = view.findViewById(R.id.edtRePassword);
        btnSubmit = view.findViewById(R.id.btnSubmit);
        btnLogin = view.findViewById(R.id.btnLogin);
    }
}