package com.kk.common.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kk.common.R;
import com.kk.common.ui.QuickOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @时间 on 2018/8/22
 * @作者 月月鸟
 * @email 1262235066@qq.com
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements IQuickBinder {

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doOnCreate(savedInstanceState);
        checkRequestPermissions();
    }

    public boolean isHasEnterAnim() {
        return true;
    }

    public boolean isHasExitAnim() {
        return true;
    }

    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

    }

    protected int getStatusBarHeight() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
    }

    private void checkRequestPermissions() {
        String[] permissions = getRequestPermissions();
        if (permissions != null) {
            List<String> pers = new ArrayList<>();
            for (String permission : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, permission)) {
                    pers.add(permission);
                }
            }
            if (pers.size() > 0) {
                String[] ps = new String[pers.size()];
                ActivityCompat.requestPermissions(this, pers.toArray(ps), 0);
            } else {
                initData();
            }
        } else {
            initData();
        }
    }

    protected String[] getRequestPermissions() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initData();
    }

    protected void initData() {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T bind(int id) {
        return (T) findViewById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T bindClick(int id, View.OnClickListener clickListener) {
        T t = findViewById(id);
        if (t != null && clickListener != null) {
            t.setOnClickListener((v) -> clickListener.onClick(t));
        }
        return t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends View> T bindClick(int id, QuickOnClickListener clickListener) {
        T t = findViewById(id);
        if (t != null && clickListener != null) {
            t.setOnClickListener((v) -> clickListener.onClick());
        }
        return t;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CheckBox> T bindCheck(int id, CompoundButton.OnCheckedChangeListener clickListener) {
        T t = findViewById(id);
        if (t != null && clickListener != null) {
            t.setOnCheckedChangeListener(clickListener);
        }
        return t;
    }

    public void enableBackHome() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setActivityTitle(int title) {
        setActivityTitle(getString(title));
    }

    public void setActivityTitle(String title) {
        setTitle(title);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Toast toast;

    @SuppressLint("ShowToast")
    public void showToastMessage(String msg, int type) {
        try {
            if (toast == null) {
                toast = Toast.makeText(this.getApplicationContext(), msg, type);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToastMessage(int msg) {
        showToastMessage(getString(msg));
    }

    @SuppressLint("ShowToast")
    public void showToastMessage(String msg) {
        try {
            if (toast == null) {
                toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface AnimMode {
        // 设置界面跳转动画效果
        /**
         * 设置切换动画，从左边进入，右边退出
         */
        int ANIM_LEFT_RIGHT = 0x2001;
        /**
         * 设置切换动画，从右边进入，左边退出
         */
        int ANIM_RIGHT_LEFT = 0x2002;
        /**
         * 设置切换动画，从下边进入，上边退出
         */
        int ANIM_BOTTOM_TOP = 0x2011;
        /**
         * 设置切换动画，从上边进入，下边退出
         */
        int ANIM_TOP_BOTTOM = 0x2012;
    }

    /**
     * 界面跳转增加跳转动画
     *
     * @param animId 动画id
     * @see AnimMode
     */
    public void setAnim(int animId) {
        if (animId == AnimMode.ANIM_LEFT_RIGHT) {
            // 设置切换动画，从左边进入，右边退出
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        } else if (animId == AnimMode.ANIM_RIGHT_LEFT) {
            // 设置切换动画，从右边进入，左边退出
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        } else if (animId == AnimMode.ANIM_TOP_BOTTOM) {
            // 设置切换动画，从上边进入，下边退出
            overridePendingTransition(R.anim.in_from_top, R.anim.out_to_down);
        } else if (animId == AnimMode.ANIM_BOTTOM_TOP) {
            // 设置切换动画，从下边进入，上边退出
            overridePendingTransition(R.anim.in_from_down, R.anim.out_to_up);
        } else {
            overridePendingTransition(0, 0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (isHasExitAnim()) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (isHasEnterAnim()) {
            setAnim();
        }
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (isHasEnterAnim()) {
            setAnim();
        }
    }


    @SuppressLint("RestrictedApi")
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void startActivityForResult(Intent intent, int requestCode, @Nullable Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        if (isHasEnterAnim()) {
            setAnim();
        }
    }

    private void setAnim() {
        overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
    }
}
