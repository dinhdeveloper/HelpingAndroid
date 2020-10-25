package com.dinh.helping.fragment.search;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.roundview.RoundTextView;
import com.canhdinh.lib.searchdialog.SimpleSearchDialogCompat;
import com.canhdinh.lib.searchdialog.core.SearchResultListener;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.adapter.product.ListProductSearchAdapter;
import com.dinh.helping.event.BackFragment;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.model.DistrictModel;
import com.dinh.helping.model.ProductModel;
import com.dinh.helping.model.WardModel;
import com.dinh.helping.viewmodel.category.CategoryViewModel;
import com.dinh.helping.viewmodel.city_district.CityViewModel;
import com.dinh.helping.viewmodel.city_district.DistrictViewModel;
import com.dinh.helping.viewmodel.city_district.WardViewModel;
import com.dinh.helping.viewmodel.search.SearchViewModel;

import java.util.ArrayList;
import java.util.Arrays;


public class SearchFragment extends Fragment implements LifecycleOwner {
    private ImageView btnBackHeader;

    private ImageView imvClearSearch;
    private EditText edtFilter;
    private View layout_empty;
    private RecyclerView rcListProductSearch;


    private RoundTextView tvCity, tvDistrict, tvWard;

    HomeActivity activity;
    SearchViewModel searchViewModel;
    CityViewModel cityViewModel;
    DistrictViewModel districtViewModel;
    WardViewModel wardViewModel;

    String city_id;
    String district_id;
    String ward_id;
    String search;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        searchViewModel = ViewModelProviders.of(requireActivity()).get(SearchViewModel.class);
        searchViewModel.init();

        cityViewModel = ViewModelProviders.of(requireActivity()).get(CityViewModel.class);
        cityViewModel.init();

        districtViewModel = ViewModelProviders.of(requireActivity()).get(DistrictViewModel.class);
        districtViewModel.init();

        wardViewModel = ViewModelProviders.of(requireActivity()).get(WardViewModel.class);
        wardViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setOnClickListener(view -> {
            activity.checkBack();
            BackFragment.post();
            activity.showBottomBar();
        });

        //forcus edittext and show keyboard
        edtFilter.requestFocus();
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        edtFilter.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!TextUtils.isEmpty(textView.getText().toString())) {
                    imvClearSearch.setVisibility(View.VISIBLE);
                    search = edtFilter.getText().toString();
                    imvClearSearch.setOnClickListener(view -> {
                        edtFilter.setText(null);
                        imvClearSearch.setVisibility(View.GONE);
                    });
                    if (!TextUtils.isEmpty(search)) { // co tu khoa search
                        searchViewModel.searchProduct(search, city_id, district_id, ward_id);

                        searchViewModel.getListSearch().observe(this, model -> {
                            rcListProductSearch.setVisibility(View.VISIBLE);
                            layout_empty.setVisibility(View.GONE);
                            ArrayList<ProductModel> list = new ArrayList<>();
                            if (model.getData() != null) {
                                list.addAll(Arrays.asList(model.getData()));
                                ListProductSearchAdapter searchAdapter = new ListProductSearchAdapter(activity, list);
                                rcListProductSearch.setHasFixedSize(true);
                                rcListProductSearch.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                                rcListProductSearch.setAdapter(searchAdapter);
                                searchAdapter.notifyDataSetChanged();
                            }
                            else {
                                Toast.makeText(activity, ""+model.getMessage(), Toast.LENGTH_SHORT).show();
                                rcListProductSearch.setVisibility(View.GONE);
                                layout_empty.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        Toast.makeText(activity, "Vui lòng nhập từ khóa tìm kiếm", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    imvClearSearch.setVisibility(View.GONE);
                }
                return true;
            }
            layout_empty.setVisibility(View.VISIBLE);
            return false;
        });

        tvCity.setOnClickListener(view -> {
            showPopupChooseCity();
        });

        tvDistrict.setOnClickListener(view -> {
            showPopupChooseDistrict(city_id);
        });

        tvWard.setOnClickListener(view -> {
            showPopupChooseWard(district_id);
        });

    }

    private void showPopupChooseWard(String district_id) {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập xã phường?", null, getListWard(district_id),
                (SearchResultListener<WardModel>) (dialog1, item, position) -> {
                    ward_id = item.getWard_id();
                    tvWard.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private void showPopupChooseDistrict(String city_id) {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập quận huyện?", null, getListDistrict(city_id),
                (SearchResultListener<DistrictModel>) (dialog1, item, position) -> {
                    district_id = item.getDistrict_id();
                    tvDistrict.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private void showPopupChooseCity() {
        SimpleSearchDialogCompat dialog = new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
                "Nhập thành phố?", null, getListCity(),
                (SearchResultListener<CityModel>) (dialog1, item, position) -> {

                    city_id = item.getCity_id();
                    tvCity.setText(item.getTitle());
                    dialog1.dismiss();
                }
        );
        dialog.show();
        dialog.getSearchBox().setTypeface(Typeface.SERIF);
    }

    private ArrayList getListCity() {
        ArrayList<CityModel> list = new ArrayList<>();
        list.addAll(Arrays.asList(cityViewModel.getListCity().getValue().getData()));
        return list;
    }

    private ArrayList getListDistrict(String city_id) {
        districtViewModel.getListDistrictByCity(city_id);
        ArrayList<DistrictModel> list = new ArrayList<>();
        districtViewModel.getListDistrictByCity().observe(this, district -> {
            list.addAll(Arrays.asList(district.getData()));
        });
        return list;
    }

    private ArrayList getListWard(String district_id) {
        wardViewModel.getListWardByDistrict(district_id);
        ArrayList<WardModel> list = new ArrayList<>();
        wardViewModel.getListWardByDistrict().observe(this, ward -> {
            list.addAll(Arrays.asList(ward.getData()));
        });
        return list;
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);

        imvClearSearch = view.findViewById(R.id.imvClearSearch);
        edtFilter = view.findViewById(R.id.edtFilter);
        layout_empty = view.findViewById(R.id.layout_empty);
        rcListProductSearch = view.findViewById(R.id.rcListProductSearch);

        tvCity = view.findViewById(R.id.tvCity);
        tvDistrict = view.findViewById(R.id.tvDistrict);
        tvWard = view.findViewById(R.id.tvWard);
    }
}