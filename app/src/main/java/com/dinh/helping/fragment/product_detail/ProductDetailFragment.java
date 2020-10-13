package com.dinh.helping.fragment.product_detail;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.viewmodel.ProductViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProductDetailFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ProductViewModel productViewModel;
    HomeActivity activity;
    private static final int REQUEST_PHONE_CALL = 1;
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;
    private BottomNavigationView navBottom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        activity.hideBottomBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_product_detail, container, false);
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);
        Toast.makeText(activity, "" + productViewModel.getSelectedItem().getValue().getProductName(), Toast.LENGTH_SHORT).show();
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        navBottom.setOnNavigationItemSelectedListener(this);
        tvTitleHeader.setText("Chi tiết sản phẩm");
        btnBackHeader.setOnClickListener(view1 -> {
            activity.checkBack();
            activity.showBottomBar();
        });
    }

    private void addControls(View view) {
        navBottom = view.findViewById(R.id.navBottom);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:0975469232"));

                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
                } else {
                    startActivity(callIntent);
                }
                return true;
            case R.id.sms:
                Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                smsIntent.setData(Uri.parse("smsto:"));
                smsIntent.setType("vnd.android-dir/mms-sms");
                smsIntent.putExtra("address", new String("0975469232"));
                smsIntent.putExtra("sms_body", productViewModel.getSelectedItem().getValue().getProductName());

                try {
                    startActivity(smsIntent);
                    Log.i("Finished sending SMS...", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(activity,
                            "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
                }
                return true;
        }
        return false;
    }
}