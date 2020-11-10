package com.dinh.helping.fragment.product_detail;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.canhdinh.lib.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.canhdinh.lib.smarteist.autoimageslider.SliderAnimations;
import com.canhdinh.lib.smarteist.autoimageslider.SliderView;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.product.ProductDetailSliderAdapter;
import com.dinh.helping.model.PhotoModel;
import com.dinh.helping.viewmodel.product.ProductViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductDetailFragment extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {
    private ProductViewModel productViewModel;
    HomeActivity activity;
    private static final int REQUEST_PHONE_CALL = 1;
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;


    SliderView sliderView;
    private ProductDetailSliderAdapter adapter;

    private String contactZalo;
    private String contactMessenger;

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
        productViewModel.init();
        productViewModel.getSelectedItem().observe(this, model -> {
            ArrayList<PhotoModel> photoModels = new ArrayList<>();
            photoModels.addAll(Arrays.asList(model.getProduct_photo()));
            adapter = new ProductDetailSliderAdapter(activity, photoModels);
            sliderView.setSliderAdapter(adapter);
            sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
            sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
            sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
            sliderView.setIndicatorSelectedColor(Color.WHITE);
            sliderView.setIndicatorUnselectedColor(Color.GRAY);
            sliderView.setScrollTimeInSec(3);
            sliderView.setAutoCycle(true);
            sliderView.startAutoCycle();

            //click vao tung item hinh anh.
            sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
                @Override
                public void onIndicatorClicked(int position) {

                }
            });
        });
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        tvTitleHeader.setText("Chi tiết sản phẩm");
        btnBackHeader.setOnClickListener(view1 -> {
            activity.checkBack();
            activity.showBottomBar();
        });
    }

    private void addControls(View view) {
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        sliderView = view.findViewById(R.id.imageSlider);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.call:
                ContactCall();
                return true;
            case R.id.sms:
                ContactSMS();
                return true;
            case R.id.zalo:
                ContactZalo();
                return true;
            case R.id.mess:
                ContactMessenger();
                return true;
        }
        return false;
    }

    private void ContactCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:0975469232"));

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
        } else {
            startActivity(callIntent);
        }
    }

    private void ContactSMS() {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", new String("0975469232"));
        smsIntent.putExtra("sms_body", "ass");

        try {
            startActivity(smsIntent);
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity,
                    "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void ContactZalo() {
        String phone = "0975469232";
        try {
            Intent waIntent = new Intent(Intent.ACTION_VIEW);
            waIntent.setData(Uri.parse("https://zalo.me/" + phone));
            waIntent.setPackage("com.zing.zalo");
            activity.startActivity(waIntent);
        } catch (Exception ex) {
            ex.printStackTrace();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.zing.zalo&hl=vi"));
            activity.startActivity(intent);
        }
    }

    private void ContactMessenger() {
        String fbID = "thuhuyendepgai00";
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setPackage("com.facebook.orca");
            intent.setData(Uri.parse("https://m.me/" + fbID));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=com.facebook.orca")
                    )
            );
        }
    }
}