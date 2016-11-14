package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.BuildConfig;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.FileUtil;

import java.io.File;
import java.util.Locale;

public class StyleControllor extends BaseControllor {
    private static final String VERCODE = "vercode";
    private static final String CURSTYLE = "curstyle";
    private String mCurStyle;
    private int verCode;
    private int mCurVerCode;
    private String lastStyle;

    StyleControllor(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            mCurVerCode = packageInfo.versionCode;
            verCode = sharedPreferences.getInt(VERCODE, 0);
            mCurStyle = sharedPreferences.getString(CURSTYLE, null);
        } catch (PackageManager.NameNotFoundException e) {
        }

    }

    public boolean isChangedStyle() {
        return lastStyle!=null && mCurStyle != null && !TextUtils.equals(mCurStyle, lastStyle);
    }

    public void setCurStyle(String file) {
        if (!TextUtils.isEmpty(file)) {
            lastStyle = mCurStyle;
            mCurStyle = new File(file).getAbsolutePath();
            sharedPreferences.edit().putString(CURSTYLE, mCurStyle).apply();
        }
    }

    public String getCurStyle() {
        return mCurStyle;
    }

    public String findDefaultStyle(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return null;
        }
        if (dir.isFile()) {
            return path;
        }
        File[] zips = dir.listFiles((pathname) -> {
            String name = pathname.getName().toLowerCase(Locale.US);
            return name.endsWith(".zip");
        });
        if (zips != null) {
            for (File f : zips) {
                if (f.isFile()) {
                    Style style = Drawer.parseStyle(f.getAbsolutePath());
                    if (style != null) {
                        return f.getAbsolutePath();
                    }
                }
            }
        }
        File[] xmls = dir.listFiles((pathname) -> {
            String name = pathname.getName().toLowerCase(Locale.US);
            return name.endsWith(".xml");
        });
        if (xmls != null) {
            for (File f : xmls) {
                if (f.isFile()) {
                    Style style = Drawer.parseStyle(f.getAbsolutePath());
                    if (style != null) {
                        return f.getAbsolutePath();
                    }
                }
            }
        }
        return null;
    }

    /***
     * 只有版本不一样的时候才复制
     */
    public boolean copyStyleFromAssets() {
        boolean update = false;
        if (mCurVerCode != verCode) {
            update = true;
            sharedPreferences.edit().putInt(VERCODE, mCurVerCode).apply();
        }
        AssetManager assetManager = context.getAssets();
        String[] files = null;
        String StylePath = ControllorManager.get().getPathConrollor().getStylePath();
        try {
            files = assetManager.list("style");
        } catch (Exception e) {
            Log.e("msoe", "AssetManager", e);
        }
        if (files != null) {
            for (String file : files) {
                if (!file.contains("style/")) {
                    file = "style/" + file;
                }
                File tofile = new File(StylePath, file.replace("/style/", "").replace("style/", ""));
                if (update || !tofile.exists()) {
//                    Log.i("msoe", "file:" + file + ",to=" + tofile);
                    FileUtil.copyFromAsset(context, file, tofile.getAbsolutePath());
                }
            }
        }
        return update;
    }
    //
//    public static StyleControllor get(Context context) {
//        if (sStyleControllor == null) {
//            synchronized (StyleControllor.class) {
//                if (sStyleControllor == null) {
//                    sStyleControllor = new StyleControllor(context);
//                }
//            }
//        }
//        return sStyleControllor;
//    }
}
