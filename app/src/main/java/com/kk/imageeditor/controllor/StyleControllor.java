package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.XmlUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import static com.kk.imageeditor.draw.Drawer.getStyleInfo;

public class StyleControllor extends BaseControllor {
    private static final String VERCODE = "vercode";
    private static final String CURSTYLE = "curstyle";
    private String mCurStyle;
    private volatile int verCode;
    private int mCurVerCode;
    private String lastStyle;
    private PathConrollor mPathConrollor;

    StyleControllor(Context context, SharedPreferences sharedPreferences) {
        super(context, sharedPreferences);
        mPathConrollor = ControllorManager.get().getPathConrollor();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            mCurVerCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        verCode = sharedPreferences.getInt(VERCODE, 0);
        mCurStyle = sharedPreferences.getString(CURSTYLE, null);
    }

    public boolean isChangedStyle() {
        return lastStyle != null && mCurStyle != null && !TextUtils.equals(mCurStyle, lastStyle);
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
        File file=new File(path, Constants.DEFAULT_STYLE);
        if(file.exists()){
            StyleInfo style = getStyleInfo(file);
            if (style != null) {
                return file.getAbsolutePath();
            }
        }
        File[] files = FileUtil.listFiles(path, Constants.STYLE_EX);
        if (files != null) {
            for (File f : files) {
                if (Constants.DEBUG)
                    Log.i("msoe", "try " + f);
                if (f.isFile()) {
                    StyleInfo style = getStyleInfo(f);
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
            Log.i("msoe", "copy styles:"+verCode+"/"+mCurVerCode);
            verCode = mCurVerCode;
            update = true;
            sharedPreferences.edit().putInt(VERCODE, mCurVerCode).commit();
            String StylePath = mPathConrollor.getStylePath();
            try {
                FileUtil.copyFilesFromAssets(context, Constants.ASSET_STYLE, StylePath, update);
            } catch (IOException e) {
            }
        }
        return update;
    }


    /***
     * 获取样式
     *
     * @param dir     样式的目录
     * @param filters 格式过滤
     * @return
     */
    public ArrayList<StyleInfo> getStyleList(String dir, String... filters) {
        ArrayList<StyleInfo> list = new ArrayList<>();
        if (TextUtils.isEmpty(dir)) return list;
        File[] files = FileUtil.listFiles(dir, filters);
        if (files != null) {
            FileInputStream inputStream = null;
            for (File f : files) {
                StyleInfo styleInfo = getStyleInfo(f);
                if (styleInfo != null) {
                    list.add(styleInfo);
                }
            }
        }
        return list;
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
