package com.dinh.helping.fragment.seller;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canhdinh.lib.helper.MyToast;
import com.canhdinh.lib.searchdialog.SimpleSearchDialogCompat;
import com.canhdinh.lib.searchdialog.core.BaseFilter;
import com.canhdinh.lib.searchdialog.core.BaseSearchDialogCompat;
import com.canhdinh.lib.searchdialog.core.SearchResultListener;
import com.canhdinh.lib.searchdialog.core.Searchable;
import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.model.CityModel;
import com.dinh.helping.viewmodel.city_district.CityViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerFragment extends Fragment {
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;

    HomeActivity activity;
    CityViewModel cityViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity) getActivity();
        cityViewModel = ViewModelProviders.of(requireActivity()).get(CityViewModel.class);
        cityViewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_seller, container, false);
        addControls(view);
        addEvents();
        //getDataApi();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setVisibility(View.GONE);
        tvTitleHeader.setText("Đăng bán");
    }

    void provideSimpleDialogWithApiCalls() {
//        final SimpleSearchDialogCompat<CityModel> searchDialog =
//                new SimpleSearchDialogCompat(activity, "Tìm kiếm...",
//                        "Nhập tên sản phẩm?", null, new ArrayList(),
//                        new SearchResultListener<Searchable>() {
//                            @Override
//                            public void onSelected(
//                                    BaseSearchDialogCompat dialog,
//                                    Searchable item, int position
//                            ) {
//                                Toast.makeText(activity, item.getTitle(),
//                                        Toast.LENGTH_SHORT
//                                ).show();
//                                dialog.dismiss();
//                            }
//                        }
//                );
//        BaseFilter apiFilter = new BaseFilter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//                doBeforeFiltering();
//                FilterResults results = new FilterResults();
//                results.values = new ArrayList<CityModel>();
//                results.count = 0;
//                try {
//                    ArrayList<CityModel> users = (ArrayList<CityModel>) apiService.searchProduct(charSequence.toString())
//                            .execute().body();
//                    if (users != null) {
//                        results.values = users;
//                        results.count = users.size();
//                    }
//                    return results;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                if (filterResults != null) {
//                    ArrayList<CityModel> filtered = (ArrayList<CityModel>) filterResults.values;
//                    if (filtered != null) {
//                        searchDialog.getFilterResultListener().onFilter(filtered);
//                    }
//                    doAfterFiltering();
//                }
//            }
//        };
//        searchDialog.setFilter(apiFilter);
//        searchDialog.show();
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);
    }
}