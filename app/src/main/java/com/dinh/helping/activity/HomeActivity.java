package com.dinh.helping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.viewpager.ViewPagerIndicator;
import com.dinh.helping.R;
import com.dinh.helping.adapter.product.ListProductAdapter;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.CategoryModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.viewmodel.CategoryViewModel;
import com.dinh.helping.viewmodel.ProductViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    private CategoryViewModel categoryViewModel;
    private ProductViewModel productViewModel;

    private ListProductAdapter productAdapter;

    private ImageView viewEmptyImageSlider;
    private RelativeLayout layoutSliderImage;
    private ViewPager pager_slider_image;
    private ViewPagerIndicator indicator_pager_slider_image;
    private TextView tvLoadAllCategory, tvLoadAllProduct;
    private ImageView cvItem1, cvItem2, cvItem3, cvItem4, cvItem5, cvItem6;
    private RecyclerView rcListProduct;
    private RoundTextView tvPriceSale6,tvPriceSale5,tvPriceSale4,tvPriceSale3,tvPriceSale2,tvPriceSale1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        addControls();

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getListCategory().observe(this, categoryModels -> {
            Glide.with(HomeActivity.this).load(R.drawable.giay2).error(R.drawable.no_image_full).into(cvItem1);
            Glide.with(HomeActivity.this).load(R.drawable.tulanh).error(R.drawable.no_image_full).into(cvItem2);
            Glide.with(HomeActivity.this).load(R.drawable.giay1).error(R.drawable.no_image_full).into(cvItem3);
            Glide.with(HomeActivity.this).load(R.drawable.aoquan1).error(R.drawable.no_image_full).into(cvItem4);
            Glide.with(HomeActivity.this).load(R.drawable.tivi1).error(R.drawable.no_image_full).into(cvItem5);
            Glide.with(HomeActivity.this).load(R.drawable.aoquan2).error(R.drawable.no_image_full).into(cvItem6);

            tvPriceSale1.setText(Consts.decimalFormat.format(64000)+"đ");
            tvPriceSale2.setText(Consts.decimalFormat.format(86000)+"đ");
            tvPriceSale3.setText(Consts.decimalFormat.format(43000)+"đ");
            tvPriceSale4.setText(Consts.decimalFormat.format(130000)+"đ");
            tvPriceSale5.setText(Consts.decimalFormat.format(534000)+"đ");
            tvPriceSale6.setText(Consts.decimalFormat.format(129900)+"đ");
        });

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.init();
        productViewModel.getListProduct().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {
                productAdapter = new ListProductAdapter(HomeActivity.this,productModels);
                rcListProduct.setHasFixedSize(true);
                rcListProduct.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
                rcListProduct.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addControls() {
        viewEmptyImageSlider = findViewById(R.id.viewEmptyImageSlider);
        layoutSliderImage = findViewById(R.id.layoutSliderImage);
        pager_slider_image = findViewById(R.id.pager_slider_image);
        indicator_pager_slider_image = findViewById(R.id.indicator_pager_slider_image);
        tvLoadAllCategory = findViewById(R.id.tvLoadAllCategory);
        tvLoadAllProduct = findViewById(R.id.tvLoadAllProduct);
        cvItem1 = findViewById(R.id.cvItem1);
        cvItem2 = findViewById(R.id.cvItem2);
        cvItem3 = findViewById(R.id.cvItem3);
        cvItem4 = findViewById(R.id.cvItem4);
        cvItem5 = findViewById(R.id.cvItem5);
        cvItem6 = findViewById(R.id.cvItem6);
        rcListProduct = findViewById(R.id.rcListProduct);

        tvPriceSale1 = findViewById(R.id.tvPriceSale1);
        tvPriceSale2 = findViewById(R.id.tvPriceSale2);
        tvPriceSale3 = findViewById(R.id.tvPriceSale3);
        tvPriceSale4 = findViewById(R.id.tvPriceSale4);
        tvPriceSale5 = findViewById(R.id.tvPriceSale5);
        tvPriceSale6 = findViewById(R.id.tvPriceSale6);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}