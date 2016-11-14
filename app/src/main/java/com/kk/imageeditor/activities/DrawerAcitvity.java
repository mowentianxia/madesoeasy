package com.kk.imageeditor.activities;

import android.Manifest;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kk.imageeditor.activities.EditUIActivity;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.widgets.ISelectImage;

import java.io.File;

public class DrawerAcitvity extends EditUIActivity {
    private static final float SETP = 0.25f;
    private static final String PREF_FILE = "drawer.settings";
    private static final String KEY_SCALE = "scale";
    protected Drawer mDrawer;
    private boolean firstRun = true;
    private String mSetFile;
    private SharedPreferences sharedPreferences;


    @Override
    protected String[] getPermissions() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(PREF_FILE, MODE_PRIVATE);
        mScale = loadScale();
    }

    protected float loadScale() {
        return sharedPreferences.getFloat(KEY_SCALE, 0f);
    }

    protected void saveScale(float scale) {
        sharedPreferences.edit().putFloat(KEY_SCALE, scale).apply();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawer != null && mDrawer.isLoad()) {
            saveScale(mScale);
            saveCache();
        }
//        PreferenceUtils.setPrefString(this, KEY_LAST_PATH, mCutPath);
    }

    protected void initDrawer(ViewGroup viewGroup) {
        ViewParent vp = viewGroup.getParent();
        int w, h;
        if (vp == null) {
            w = viewGroup.getWidth();
            h = viewGroup.getHeight();
        } else {
            w = ((View) vp).getWidth();
            h = ((View) vp).getHeight();
        }
        mDrawer = new Drawer(this, viewGroup, w, h, getDefaultData());
    }

    protected Bitmap getDrawBitmap() {
        if (mDrawer == null) return null;
        return mDrawer.getImage();
    }

    /***
     * 重置数据
     */
    protected void resetData() {
        if (mDrawer == null) return;
        clearCache();
        mDrawer.reset();
    }

    /***
     * 加载样式
     *
     * @param file
     * @return
     */
    protected boolean loadStyle(String file) {
        if (mDrawer == null) return false;
        if (!firstRun) {
            clearCache();
        }
        Drawer.Error error = mDrawer.loadStyle(file);
        return error == Drawer.Error.None;
    }

    /***
     * 初始化视图在loadStyle成功后，调用
     */
    protected void initStyle() {
        if (mDrawer == null) return;
        mScale = mDrawer.initViews(mScale);
        setStyleInfo(getStyleInfo());
        if (firstRun) {
            loadCache();
        }
        updateViews();
        firstRun = false;
    }

    protected void setStyleInfo(StyleInfo info) {

    }

    /***
     * 最适合比例
     */
    protected void zoomFit() {
        if (mDrawer == null) return;
        mScale = mDrawer.scaleFit();
    }

    /***
     * 缩小视图
     */
    protected void zoomOut() {
        if (mDrawer == null) return;
        float s = mScale - SETP;
        if (s <= SETP)
            s = SETP;
        scaleTo(s);
    }

    /***
     * 放大视图
     */
    protected void zoomIn() {
        if (mDrawer == null) return;
        float s = mScale + SETP;
        scaleTo(s);
    }

    /***
     * 缩放视图
     */
    protected void scaleTo(float f) {
        mScale = f;
        mDrawer.scale(f);
    }

    /***
     * 加载存档
     */
    protected void loadSet(File file) {
        if (mDrawer == null) return;
        if (file == null || !file.exists()) return;
        mSetFile = file.getAbsolutePath();
        mDrawer.reset();
        mDrawer.loadSet(file);
        mDrawer.updateViews();
    }

    /***
     * 保存存档
     */
    protected void saveSet(String file) {
        if (mDrawer == null) return;
        if (file == null) return;
        mSetFile = file;
        mDrawer.saveSet(FileUtil.file(file));
    }

    @Override
    protected void updateViews() {
        if (mDrawer == null) return;
        mDrawer.updateViews();
    }

    @Override
    protected ISelectImage getSelectImage() {
        return null;
    }
}
