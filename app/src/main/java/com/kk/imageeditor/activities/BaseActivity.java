package com.kk.imageeditor.activities;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import static com.kk.imageeditor.Constants.REQUEST_PERMISSIONS;

class BaseActivity extends AppCompatActivity {

    protected String[] getPermissions() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPermissionsActivity();
    }

    protected int getActivityHeight() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    public void enableBackHome(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    protected int getStatusBarHeight() {
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.top;
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
        PermissionsActivity.startActivityForResult(this, REQUEST_PERMISSIONS, PERMISSIONS);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_PERMISSIONS && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {
            finish();
        }
    }
}
