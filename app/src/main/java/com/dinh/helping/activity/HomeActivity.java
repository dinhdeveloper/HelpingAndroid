package com.dinh.helping.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.canhdinh.lib.alert.AlertError;
import com.dinh.helping.R;
import com.dinh.helping.fragment.category.ListCategoryFragment;
import com.dinh.helping.fragment.dashboard.DashboardFragment;
import com.dinh.helping.fragment.messenger.MessFragment;
import com.dinh.helping.fragment.profile.info.ProfileFragment;
import com.dinh.helping.fragment.profile.register.SignUpFragment;
import com.dinh.helping.fragment.profile.verify.VeryCodeFragment;
import com.dinh.helping.fragment.search.SearchFragment;
import com.dinh.helping.fragment.seller.SellerFragment;
import com.fxn.BubbleTabBar;
import com.fxn.OnBubbleClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    private BubbleTabBar bubbleTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (isNetworkConnected()) {
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
        else {
            AlertError.showAlertError(HomeActivity.this,"Xác nhận","Bạn chưa kết nối internet");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public String tinhThoiGian(String dates) {
        Date dateTime = null;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dates);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        StringBuffer times = new StringBuffer();
        Date current = Calendar.getInstance().getTime();
        long diffInSeconds = (current.getTime() - dateTime.getTime()) / 1000;

    /*long diff[] = new long[]{0, 0, 0, 0};
    /* sec *  diff[3] = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
    /* min *  diff[2] = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
    /* hours *  diff[1] = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
    /* days * diff[0] = (diffInSeconds = (diffInSeconds / 24));
     */
        long sec = (diffInSeconds >= 60 ? diffInSeconds % 60 : diffInSeconds);
        long min = (diffInSeconds = (diffInSeconds / 60)) >= 60 ? diffInSeconds % 60 : diffInSeconds;
        long hrs = (diffInSeconds = (diffInSeconds / 60)) >= 24 ? diffInSeconds % 24 : diffInSeconds;
        long days = (diffInSeconds = (diffInSeconds / 24)) >= 30 ? diffInSeconds % 30 : diffInSeconds;
        long months = (diffInSeconds = (diffInSeconds / 30)) >= 12 ? diffInSeconds % 12 : diffInSeconds;
        long years = (diffInSeconds = (diffInSeconds / 12));

        if (years > 0) {
            if (years == 1) {
                times.append("1 năm");
            } else {
                times.append(years + " năm");
            }
            if (years <= 6 && months > 0) {
                if (months == 1) {
                    times.append(" ,một tháng");
                } else {
                    times.append(", " + months + " tháng");
                }
            }
        } else if (months > 0) {
            if (months == 1) {
                times.append("1 tháng");
            } else {
                times.append(months + " tháng");
            }
            if (months <= 6 && days > 0) {
                if (days == 1) {
                    times.append(" ,một ngày");
                } else {
                    times.append(", " + days + " ngày");
                }
            }
        } else if (days > 0) {
            if (days == 1) {
                times.append("1 ngày");
            } else {
                times.append(days + " ngày");
            }
            if (days <= 3 && hrs > 0) {
                if (hrs == 1) {
                    times.append(",một giờ");
                } else {
                    times.append(", " + hrs + " giờ");
                }
            }
        } else if (hrs > 0) {
            if (hrs == 1) {
                times.append("1 giờ");
            } else {
                times.append(hrs + " giờ");
            }
            if (min > 1) {
                times.append(", " + min + " phút");
            }
        } else if (min > 0) {
            if (min == 1) {
                times.append("1 phút");
            } else {
                times.append(min + " phút");
            }
            if (sec > 1) {
                times.append(", " + sec + " giây");
            }
        } else {
            if (sec <= 1) {
                times.append("khoảng một giây");
            } else {
                times.append(" khoảng " + sec + " giây");
            }
        }

        times.append(" trước");

        return times.toString();
    }

    public boolean defauFragment(DashboardFragment fragment) {
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

    //FRAGMENT
    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            View view = activity.getCurrentFocus();
            if (view != null && inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception ignored) {
        }
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

    //END

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


    public void changeToSignUpFragment() {
        addFragment(new SignUpFragment(), true);
    }

    public void changeToListCategory() {
        addFragment(new ListCategoryFragment(), true);
    }

    public void changeToSearchFragment() {
        addFragment(new SearchFragment(), true);
    }
}