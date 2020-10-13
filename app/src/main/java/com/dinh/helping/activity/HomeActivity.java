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
import com.canhdinh.lib.recyclerview.ProgressStyle;
import com.canhdinh.lib.recyclerview.XRecyclerView;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.viewpager.ViewPagerIndicator;
import com.dinh.helping.R;
import com.dinh.helping.adapter.product.ListProductAdapter;
import com.dinh.helping.adapter.product.ListProductDiscountAdapter;
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
    private ListProductDiscountAdapter discountAdapter;

    private ImageView viewEmptyImageSlider;
    private RelativeLayout layoutSliderImage;
    private ViewPager pager_slider_image;
    private ViewPagerIndicator indicator_pager_slider_image;
    private TextView tvLoadAllCategory, tvLoadAllProduct,tvLoadDiscount;
    private ImageView cvItem1, cvItem2, cvItem3, cvItem4, cvItem5, cvItem6;
    private RecyclerView rcListProductDiscount;
    private XRecyclerView rcListProduct;
    private RoundTextView tvPriceSale6,tvPriceSale5,tvPriceSale4,tvPriceSale3,tvPriceSale2,tvPriceSale1;


    private int times = 0;
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

            tvPriceSale1.setText("Giày");
            tvPriceSale2.setText("Tủ lạnh");
            tvPriceSale3.setText("Thời trang");
            tvPriceSale4.setText("Áo quần");
            tvPriceSale5.setText("Tivi");
            tvPriceSale6.setText("Áo quần");
        });

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.init();
        productViewModel.getListProduct().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(List<ProductModel> productModels) {

                discountAdapter = new ListProductDiscountAdapter(HomeActivity.this,productModels);
                rcListProductDiscount.setHasFixedSize(true);
                rcListProductDiscount.setLayoutManager(new LinearLayoutManager(HomeActivity.this,LinearLayoutManager.HORIZONTAL,false));
                rcListProductDiscount.setAdapter(discountAdapter);
                discountAdapter.notifyDataSetChanged();

                loadMore(productModels);
                productAdapter = new ListProductAdapter(HomeActivity.this,productModels);
                rcListProduct.setHasFixedSize(true);
                rcListProduct.setLayoutManager(new GridLayoutManager(HomeActivity.this,2));
                rcListProduct.setAdapter(productAdapter);
                productAdapter.notifyDataSetChanged();
            }
        });
    }
    final int itemLimit = 5;
    private void loadMore(List<ProductModel> productModels) {
        rcListProduct.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rcListProduct.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rcListProduct.setArrowImageView(R.drawable.iconfont_downgrey);
        rcListProduct.getDefaultRefreshHeaderView().setRefreshTimeVisible(false);

        rcListProduct.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }
            @Override
            public void onLoadMore() {
                if(times < 2){
                    new Handler().postDelayed(new Runnable(){
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                productViewModel.getListProduct().getValue();
                            }
                            if(rcListProduct != null) {
                                rcListProduct.loadMoreComplete();
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for(int i = 0; i < itemLimit ;i++){
                                productViewModel.getListProduct().getValue();
                            }
                            if(rcListProduct != null) {
                                rcListProduct.setNoMore(true);
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times ++;
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
        tvLoadDiscount = findViewById(R.id.tvLoadDiscount);

        cvItem1 = findViewById(R.id.cvItem1);
        cvItem2 = findViewById(R.id.cvItem2);
        cvItem3 = findViewById(R.id.cvItem3);
        cvItem4 = findViewById(R.id.cvItem4);
        cvItem5 = findViewById(R.id.cvItem5);
        cvItem6 = findViewById(R.id.cvItem6);

        rcListProduct = findViewById(R.id.rcListProduct);
        rcListProductDiscount = findViewById(R.id.rcListProductDiscount);

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