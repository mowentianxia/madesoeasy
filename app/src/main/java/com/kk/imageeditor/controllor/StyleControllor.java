package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.StyleInfoEx;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.MD5Util;
import com.kk.imageeditor.utils.XmlUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Locale;

import static com.kk.imageeditor.Constants.DEBUG;
import static com.kk.imageeditor.draw.Drawer.check;
import static com.kk.imageeditor.draw.Drawer.parseStyle;

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
        File[] zips = dir.listFiles((pathname) -> {
            String name = pathname.getName().toLowerCase(Locale.US);
            return name.endsWith(".zip");
        });
        if (zips != null) {
            for (File f : zips) {
                if (f.isFile()) {
                    Style style = parseStyle(f.getAbsolutePath());
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
                    Style style = parseStyle(f.getAbsolutePath());
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
        if (update) {
            sharedPreferences.edit().putInt(VERCODE, mCurVerCode).apply();
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
            for (File f : files) {
                File cache = new File(context.getFilesDir(), f.getName());
                if (cache.exists()) {
                    try {
                        StyleInfoEx styleInfoEx = XmlUtils.getStyleUtils().getObject(StyleInfoEx.class, cache);
                        if (styleInfoEx != null) {
                            if (TextUtils.equals(styleInfoEx.getMd5(), MD5Util.getFileMD5(f.getAbsolutePath()))) {
                                //一样
                                StyleInfo info = styleInfoEx.getInfo();
                                info.setStylePath(f.getAbsolutePath());
                                Log.i("kk", "info="+info);
                                list.add(info);
                                continue;
                            } else {
                                cache.delete();
                            }
                        }
                    } catch (Exception e) {
                    }
                }
                long start = System.currentTimeMillis();
                Style style = parseStyle(f.getAbsolutePath());
                if (DEBUG) {
                    Log.i("msoe", "time=" + (System.currentTimeMillis() - start));
                }
                if (style != null) {
                    Drawer.Error error = check(style);
                    if (error == Drawer.Error.None && style.getStyleInfo() != null) {
                        StyleInfo styleInfo = style.getStyleInfo();
                        styleInfo.setStylePath(f.getAbsolutePath());
                        StyleInfoEx styleInfoEx = new StyleInfoEx(styleInfo);
                        FileOutputStream outputStream = null;
                        try {
                            outputStream = new FileOutputStream(cache);
                            XmlUtils.getStyleUtils().saveXml(styleInfoEx, outputStream);
                            outputStream.close();
                        } catch (Exception e) {

                        } finally {
                            FileUtil.close(outputStream);
                        }
                        list.add(styleInfo);
                    }
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
