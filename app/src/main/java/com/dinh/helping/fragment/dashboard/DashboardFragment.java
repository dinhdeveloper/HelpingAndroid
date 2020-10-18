package com.dinh.helping.fragment.dashboard;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.canhdinh.lib.recyclerview.ProgressStyle;
import com.canhdinh.lib.recyclerview.XRecyclerView;
import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.viewpager.ViewPagerIndicator;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.product.ListProductAdapter;
import com.dinh.helping.adapter.product.ListProductDiscountAdapter;
import com.dinh.helping.fragment.product_detail.ProductDetailFragment;
import com.dinh.helping.helper.Consts;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.viewmodel.category.CategoryViewModel;
import com.dinh.helping.viewmodel.product.ProductViewModel;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DashboardFragment extends Fragment {
    private CategoryViewModel categoryViewModel;
    private ProductViewModel productViewModel;

    private ListProductAdapter productAdapter;
    private ListProductDiscountAdapter discountAdapter;

    private ImageView viewEmptyImageSlider;
    private RelativeLayout layoutSliderImage;
    private ViewPager pager_slider_image;
    private ViewPagerIndicator indicator_pager_slider_image;
    private TextView tvLoadAllCategory, tvLoadAllProduct, tvLoadDiscount;
    private ImageView cvItem1, cvItem2, cvItem3, cvItem4, cvItem5, cvItem6;
    private RecyclerView rcListProductDiscount;
    private XRecyclerView rcListProduct;
    private RoundTextView tvPriceSale6, tvPriceSale5, tvPriceSale4, tvPriceSale3, tvPriceSale2, tvPriceSale1;
    private ShimmerFrameLayout mShimmerFrameLayout;

    private int times = 0;
    HomeActivity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#A06EF8"));
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dashboard, container, false);
        addControls(view);
        activity = (HomeActivity) getActivity();
        getData();
        return view;
    }

    private void getData() {
        //mShimmerFrameLayout.startShimmer();
        //kham pha danh muc
        categoryViewModel = ViewModelProviders.of(requireActivity()).get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getListCategory().observe(this, categoryModels -> {

            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[0].getCategory_image()).error(R.drawable.no_image_full).into(cvItem1);
            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[1].getCategory_image()).error(R.drawable.no_image_full).into(cvItem2);
            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[2].getCategory_image()).error(R.drawable.no_image_full).into(cvItem3);
            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[3].getCategory_image()).error(R.drawable.no_image_full).into(cvItem4);
            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[4].getCategory_image()).error(R.drawable.no_image_full).into(cvItem5);
            Glide.with(activity).load(Consts.HOST_API + categoryModels.getData()[5].getCategory_image()).error(R.drawable.no_image_full).into(cvItem6);

            tvPriceSale1.setText(categoryModels.getData()[0].getCategory_name());
            tvPriceSale2.setText(categoryModels.getData()[1].getCategory_name());
            tvPriceSale3.setText(categoryModels.getData()[2].getCategory_name());
            tvPriceSale4.setText(categoryModels.getData()[3].getCategory_name());
            tvPriceSale5.setText(categoryModels.getData()[4].getCategory_name());
            tvPriceSale6.setText(categoryModels.getData()[5].getCategory_name());

            //shimmerAnimation stop and hide
            mShimmerFrameLayout.stopShimmer();
            mShimmerFrameLayout.setVisibility(View.GONE);
        });
        //product
        productViewModel = ViewModelProviders.of(requireActivity()).get(ProductViewModel.class);
        productViewModel.init();
        productViewModel.getListProduct().observe(this, productModels -> {
            ArrayList<ProductModel> models = new ArrayList<>();
            models.addAll(Arrays.asList(productModels.getData()));
            discountAdapter = new ListProductDiscountAdapter(activity, models);
            rcListProductDiscount.setHasFixedSize(true);
            rcListProductDiscount.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            rcListProductDiscount.setAdapter(discountAdapter);
            discountAdapter.notifyDataSetChanged();
            discountAdapter.setListener(model -> {
                productViewModel.setSelectedItem(model);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layoutRoot, new ProductDetailFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });

            //ListProduct
            //loadMore(models);
            productAdapter = new ListProductAdapter(activity, models);
            rcListProduct.setHasFixedSize(true);
            rcListProduct.setLayoutManager(new GridLayoutManager(activity, 2));
            rcListProduct.setAdapter(productAdapter);
            productAdapter.notifyDataSetChanged();

            productAdapter.setListener(model -> {
                productViewModel.setSelectedItem(model);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.layoutRoot, new ProductDetailFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            });
        });
    }

    final int itemLimit = 5;

    private void loadMore(List<ProductModel> productModels) {
        rcListProduct.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rcListProduct.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rcListProduct.setArrowImageView(R.drawable.iconfont_downgrey);
        rcListProduct.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);

        rcListProduct.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                if (times < 2) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for (int i = 0; i < itemLimit; i++) {
                                productViewModel.getListProduct().getValue();
                            }
                            if (rcListProduct != null) {
                                rcListProduct.loadMoreComplete();
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                } else {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            for (int i = 0; i < itemLimit; i++) {
                                productViewModel.getListProduct().getValue();
                            }
                            if (rcListProduct != null) {
                                rcListProduct.setNoMore(true);
                                productAdapter.notifyDataSetChanged();
                            }
                        }
                    }, 1000);
                }
                times++;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    private void addControls(View view) {
        viewEmptyImageSlider = view.findViewById(R.id.viewEmptyImageSlider);
        layoutSliderImage = view.findViewById(R.id.layoutSliderImage);
        pager_slider_image = view.findViewById(R.id.pager_slider_image);
        indicator_pager_slider_image = view.findViewById(R.id.indicator_pager_slider_image);

        tvLoadAllCategory = view.findViewById(R.id.tvLoadAllCategory);
        tvLoadAllProduct = view.findViewById(R.id.tvLoadAllProduct);
        tvLoadDiscount = view.findViewById(R.id.tvLoadDiscount);

        cvItem1 = view.findViewById(R.id.cvItem1);
        cvItem2 = view.findViewById(R.id.cvItem2);
        cvItem3 = view.findViewById(R.id.cvItem3);
        cvItem4 = view.findViewById(R.id.cvItem4);
        cvItem5 = view.findViewById(R.id.cvItem5);
        cvItem6 = view.findViewById(R.id.cvItem6);

        rcListProduct = view.findViewById(R.id.rcListProduct);
        rcListProductDiscount = view.findViewById(R.id.rcListProductDiscount);

        tvPriceSale1 = view.findViewById(R.id.tvPriceSale1);
        tvPriceSale2 = view.findViewById(R.id.tvPriceSale2);
        tvPriceSale3 = view.findViewById(R.id.tvPriceSale3);
        tvPriceSale4 = view.findViewById(R.id.tvPriceSale4);
        tvPriceSale5 = view.findViewById(R.id.tvPriceSale5);
        tvPriceSale6 = view.findViewById(R.id.tvPriceSale6);

        mShimmerFrameLayout = view.findViewById(R.id.shimmer_view_product);
    }
}