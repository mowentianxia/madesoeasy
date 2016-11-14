package com.kk.imageeditor.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    protected static final int REQUEST_CODE = 1;

    protected String[] getPermissions() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPermissionsActivity();
    }

    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setActionBarTitle(int rid) {
        setActionBarTitle(getString(rid));
    }

    protected void startPermissionsActivity() {
        String[] PERMISSIONS = getPermissions();
        if (PERMISSIONS == null || PERMISSIONS.length == 0) return;
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
