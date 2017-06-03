package com.kk.imageeditor.activities;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.controllor.ControllorManager;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.VUiKit;
import com.kk.imageeditor.widgets.ISelectImage;
import com.kk.imageeditor.widgets.ISelectImageListener;

import java.io.File;

import static com.kk.imageeditor.Constants.PREF_LAST_SET;
import static com.kk.imageeditor.Constants.SCALE_SETP;

public class DrawerAcitvity extends EditUIActivity implements ISelectImage {

    protected Drawer mDrawer;
    private String mSetFile;

    private String selectFile;
    private int selectWidth;
    private int selectHeigth;
    protected SharedPreferences mSharedPreferences;

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
        mSharedPreferences = ControllorManager.get().getSharedPreferences();
        mSetFile = mSharedPreferences.getString(PREF_LAST_SET, getDefaultCacheSet());
//        setActionBarTitle(FileUtil.getFileNameNoType(mSetFile));
    }

    protected String getDefaultCacheSet() {
        return null;//new File(pathConrollor.getSetPath(), getString(R.string.noname) + Constants.SET_EX2).getAbsolutePath();
    }

    @Override
    protected float getCurScale() {
        return mDrawer.getScale();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mDrawer != null && mDrawer.isLoad()) {
            saveCache();
        }
//        PreferenceUtils.setPrefString(this, KEY_LAST_PATH, mCutPath);
    }

    protected void initDrawer(ViewGroup viewGroup) {
        int w, h;
        w = getResources().getDisplayMetrics().widthPixels;
        ActionBar actionBar = getSupportActionBar();
        int actionBarH = 0;
        if (actionBar != null) {
            actionBarH = actionBar.getHeight();
        }
        h = getWindowManager().getDefaultDisplay().getHeight() - actionBarH - getStatusBarHeight();
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
//        setSetFile(null);
        clearCache();
        mDrawer.reset();
    }

    /***
     * 加载样式
     *
     * @param file
     * @return
     */
    protected Drawer.Error loadStyle(String file, boolean nocache) {
        if (mDrawer == null) return Drawer.Error.UnknownError;
        if (nocache) {
            clearCache();
        }
        return mDrawer.loadStyle(file);
    }

    /***
     * 初始化视图在loadStyle成功后，调用
     */
    protected void initStyle(boolean usechache) {
        if (mDrawer == null) return;
        mDrawer.initViews(0);
        setStyleInfo(getStyleInfo());
        //第一次启动，加载上次的存档
        if (usechache) {
            loadCache();
        }
        updateViews();
    }

    protected void setStyleInfo(StyleInfo info) {

    }

    protected boolean checkDrawer() {
        return mDrawer == null;
    }

    /***
     * 最适合比例
     */
    protected void zoomFit() {
        if (checkDrawer()) return;
        mDrawer.scaleFit();
    }

    /***
     * 缩小视图
     */
    protected void zoomOut() {
        if (checkDrawer()) return;
        float s = mDrawer.getScale() - SCALE_SETP;
        if (s <= SCALE_SETP)
            s = SCALE_SETP;
        scaleTo(s);
    }

    /***
     * 放大视图
     */
    protected void zoomIn() {
        if (checkDrawer()) return;
        float s = mDrawer.getScale() + SCALE_SETP;
        scaleTo(s);
    }

    /***
     * 缩放视图
     */
    protected void scaleTo(float f) {
        mDrawer.scale(f);
    }

    /***
     * 加载存档
     */
    protected void loadSet(File file) {
        if (checkDrawer()) return;
        if (file == null || !file.exists()) return;
        mDrawer.reset();
        VUiKit.defer().when(() -> {
            return mDrawer.loadSet(file);
        }).done((ok) -> {
            if (ok) {
                setSetFile(file.getAbsolutePath());
//                setActionBarTitle(FileUtil.getFileNameNoType(mSetFile));
                Toast.makeText(this, R.string.load_set_ok, Toast.LENGTH_SHORT).show();
                mDrawer.updateViews();
            } else {
                Toast.makeText(this, R.string.load_set_fail, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setSetFile(String setFile) {
        if (TextUtils.equals(mSetFile, setFile)) {
            return;
        }
        mSetFile = setFile;
        mSharedPreferences.edit().putString(PREF_LAST_SET, setFile).commit();
    }

    /***
     * 保存存档
     */
    protected void saveSet(String file) {
        if (checkDrawer()) return;
        if (file == null) {
            if (mSetFile == null) {
                return;
            }
            file = mSetFile;
        }
        setSetFile(file);
        mDrawer.saveSet(FileUtil.file(file));
    }

    public String getSetFile() {
        return mSetFile;
    }

    @Override
    protected void updateViews() {
        if (checkDrawer()) return;
        mDrawer.updateViews();
    }

    @Override
    protected ISelectImage getSelectImage() {
        return this;
    }

    private ISelectImageListener listener;

    @Override
    public void setListener(ISelectImageListener listener) {
        if (this.listener == listener) {
            return;
        }
        this.listener = listener;
    }

    @Override
    public void startPhotoCut(String saveFile, int width, int height, boolean needSelect) {
        if (TextUtils.isEmpty(saveFile)) return;
        selectFile = saveFile;
        selectWidth = width;
        selectHeigth = height;

//        UCrop.of(sourceUri, destinationUri)
//                .withAspectRatio(1, 1)
//                .withMaxResultSize(width, height)
//                .start(this);

        FileUtil.createDirByFile(saveFile);
        if (needSelect) {// 浏览图片
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            this.startActivityForResult(intent, Constants.REQUEST_CHOOSE_IMG);
        } else {// 拍照
            Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            File f = FileUtil.file(saveFile + ".tmp");
            if (f != null && f.exists()) {// 删除旧文件
                f.delete();
            }
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            try {
                this.startActivityForResult(intent2, Constants.REQUEST_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(this, R.string.no_find_image_selector, Toast.LENGTH_SHORT).show();
            }
        }
    }

    protected void openPhotoCut(Uri uri, String saveFile, int width, int height) {
        // 裁剪图片
        if (TextUtils.isEmpty(saveFile)) return;
        File file = FileUtil.file(saveFile);
        if (file == null) return;
        Uri saveimgUri = Uri.fromFile(file);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", height);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);// 黑边
        intent.putExtra("scaleUpIfNeeded", true);// 黑边

        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, saveimgUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        try {
            startActivityForResult(intent, Constants.REQUEST_CUT_IMG);
        } catch (Exception e) {
            Toast.makeText(this, R.string.no_find_image_cutor, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CHOOSE_IMG) {// 选择图片
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    openPhotoCut(uri, selectFile, selectWidth, selectHeigth);
                }
            }
            return;
        } else if (requestCode == Constants.REQUEST_CUT_IMG) {// 裁剪完图片
            if (listener != null) {
                listener.onCurImageCompleted(selectFile);
            }
        } else if (requestCode == Constants.REQUEST_CAPTURE) {
            File f = FileUtil.file(selectFile + ".tmp");
            if (f != null && f.exists()) {// 拍照的临时图片
                openPhotoCut(Uri.fromFile(f), selectFile, selectWidth, selectHeigth);
            } else {
                if (listener != null) {
                    listener.onCurImageCompleted(selectFile);
                }
            }
        }
    }
}
