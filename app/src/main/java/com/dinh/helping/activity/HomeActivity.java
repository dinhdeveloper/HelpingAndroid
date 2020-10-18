package com.dinh.helping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.dinh.helping.R;
import com.dinh.helping.fragment.dashboard.DashboardFragment;
import com.dinh.helping.fragment.messenger.MessFragment;
import com.dinh.helping.fragment.profile.info.ProfileFragment;
import com.dinh.helping.fragment.profile.register.SignUpFragment;
import com.dinh.helping.fragment.profile.verify.VeryCodeFragment;
import com.dinh.helping.fragment.seller.SellerFragment;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;


public class HomeActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    private BubbleTabBar bubbleTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bubbleTabBar = findViewById(R.id.bubbleTabBar);
        defauFragment(new DashboardFragment());
        fullScreen();
        bubbleTabBar.addBubbleListener(new OnBubbleClickListener() {
            Fragment fragment = null;

            @Override
            public void onBubbleClick(int i) {
                switch (i) {
                    case R.id.mess:
                        fragment = new MessFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layoutRoot, fragment)
                                .commit();
                        break;
                    case R.id.sale:
                        fragment = new SellerFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layoutRoot, fragment)
                                .commit();
                        break;
                    case R.id.profile:
                        fragment = new ProfileFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layoutRoot, fragment)
                                .commit();
                        break;
                    default:
                        fragment = new DashboardFragment();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.layoutRoot, fragment)
                                .commit();
                        break;
                }
            }
        });
    }

    private boolean defauFragment(DashboardFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.layoutRoot, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public void fullScreen() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    public void showBottomBar() {
        bubbleTabBar.setVisibility(View.VISIBLE);
    }

    public void hideBottomBar() {
        bubbleTabBar.setVisibility(View.GONE);
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

    private int isShowContainer = 0;

    public void checkBack() {
        if (isShowContainer > 0) {
            isShowContainer--;
            FragmentManager fm = getSupportFragmentManager();
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
            }
        } else {
            checkFragment();
        }
    }

    private void checkFragment() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack();

        } else {
            super.onBackPressed();
        }
    }

    public void changeToVeryCodeFragment() {
        Fragment fragment = new VeryCodeFragment();
        addFragment(fragment, true);
    }

    public void addFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.layoutRoot, fragment);

            // skip add fragment to back stack if it is first fragment
            if (addToBackStack) {
                transaction.addToBackStack(null);
            } else {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            transaction.commitAllowingStateLoss();
        }
    }

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        if (fragment != null && !fragment.isAdded()) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.layoutRoot, fragment);

            // skip add fragment to back stack if it is first fragment
            if (addToBackStack) {
                transaction.addToBackStack(null);
            } else {
                fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }

            transaction.commitAllowingStateLoss();
        }
    }

    public void changeToSignUpFragment() {
        addFragment(new SignUpFragment(), true);
    }
}