package com.dinh.helping.fragment.search;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dinh.helping.R;
import com.dinh.helping.activity.HomeActivity;
import com.dinh.helping.event.BackFragment;
import com.dinh.helping.viewmodel.search.SearchViewModel;


public class SearchFragment extends Fragment implements LifecycleOwner {
    private ImageView btnBackHeader;
    private TextView tvTitleHeader;

    private ImageView imvClearSearch;
    private EditText edtFilter;

    HomeActivity activity;
    SearchViewModel searchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_search, container, false);
        addControls(view);
        searchViewModel = ViewModelProviders.of(requireActivity()).get(SearchViewModel.class);
        searchViewModel.init();
        addEvents();
        return view;
    }

    private void addEvents() {
        btnBackHeader.setOnClickListener(view -> {
            activity.checkBack();
            BackFragment.post();
            activity.showBottomBar();
        });
        tvTitleHeader.setText("Tìm kiếm");

        edtFilter.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!TextUtils.isEmpty(textView.getText().toString())){
                    imvClearSearch.setVisibility(View.VISIBLE);

                    imvClearSearch.setOnClickListener(view -> {
                        edtFilter.setText(null);
                        imvClearSearch.setVisibility(View.GONE);
                    });
                }
                else {
                    imvClearSearch.setVisibility(View.GONE);
                }
                searchViewModel.searchProduct(edtFilter.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);
        tvTitleHeader = view.findViewById(R.id.tvTitleHeader);

        imvClearSearch = view.findViewById(R.id.imvClearSearch);
        edtFilter = view.findViewById(R.id.edtFilter);
    }
}