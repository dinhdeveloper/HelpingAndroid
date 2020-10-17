package com.dinh.helping.fragment.profile;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.canhdinh.lib.roundview.RoundLinearLayout;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;

public class ProfileFragment extends Fragment {

    private ImageView btnBackHeader;
    private TextView tvTitleHeader;
    private ImageView imvProfile;
    private RecyclerView rcListProductByCus;
    private TextView tvName,tvLocation;

    private LinearLayout layout_sign_up;
    private TextView tvForgot;
    private RoundLinearLayout btnLogin;
    private EditText edtPhoneNumber,edtPassword;

    private View layout_empty,layout_active;

    HomeActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#F3F3F5"));
        }
        super.onCreate(savedInstanceState);
        activity = (HomeActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_profile, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setVisibility(View.GONE);
        tvTitleHeader.setText("Thông tin cá nhân");

        tvName.setText("Trần Cảnh Dinh");
        tvLocation.setText("Bình Thạnh, Hồ Chí Minh");

        layout_sign_up.setOnClickListener(view -> {
           Fragment fragment = new VeryCodeFragment();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.layoutRoot, fragment)
                    .commit();
        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
        imvProfile = view.findViewById(R.id.imvProfile);
        rcListProductByCus = view.findViewById(R.id.rcListProductByCus);
        tvName = view.findViewById(R.id.tvName);
        tvLocation = view.findViewById(R.id.tvLocation);

        layout_sign_up = view.findViewById(R.id.layout_sign_up);
        tvForgot = view.findViewById(R.id.tvForgot);
        btnLogin = view.findViewById(R.id.btnLogin);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtPhoneNumber = view.findViewById(R.id.edtPhoneNumber);
        layout_empty = view.findViewById(R.id.layout_empty);
        layout_active = view.findViewById(R.id.layout_active);
    }
}