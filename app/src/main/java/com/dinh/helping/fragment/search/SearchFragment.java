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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

    private ImageView imvClearSearch;
    private EditText edtFilter;
    private View layout_empty;

    HomeActivity activity;
    SearchViewModel searchViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = (HomeActivity)getActivity();
        searchViewModel = ViewModelProviders.of(requireActivity()).get(SearchViewModel.class);
        searchViewModel.init();
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
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,InputMethodManager.HIDE_IMPLICIT_ONLY);

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

                searchViewModel.getListSearch().observe(this,model -> {
                    if (model!=null){
                        layout_empty.setVisibility(View.GONE);
                        Toast.makeText(activity, ""+model.getData()[0].getProduct_name(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        layout_empty.setVisibility(View.VISIBLE);
                    }
                });
                return true;
            }
            layout_empty.setVisibility(View.VISIBLE);
            return false;
        });
    }

    private void addControls(View view) {
        btnBackHeader = view.findViewById(R.id.btnBackHeader);

        imvClearSearch = view.findViewById(R.id.imvClearSearch);
        edtFilter = view.findViewById(R.id.edtFilter);
        layout_empty = view.findViewById(R.id.layout_empty);
    }
}