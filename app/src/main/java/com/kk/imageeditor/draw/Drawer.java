package com.kk.imageeditor.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kk.imageeditor.BuildConfig;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.view.ViewInfo;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.XmlUtils;
import com.kk.imageeditor.view.IKView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Drawer {
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String XML_STYLE_NAME = "style.xml";
    private Context context;
    protected IDataProvider IDataor;
    protected ViewGroup mViewGroup;
    protected Style mStyle;
    protected View mView;
    protected int pWidth;
    protected int pHeight;
    public static final long Version = 3;

    public enum Error {
        None,
        InfoEmpty,
        NeedUpdate,
        NoData,
        NoLayout,
        UnknownVersion,
        UnknownError
    }

    public Drawer(Context context, ViewGroup pViewGroup, int pWidth, int pHeight, IDataProvider pIDataor) {
        this.context = context;
        this.IDataor = pIDataor;
        this.mViewGroup = pViewGroup;
        this.pWidth = pWidth;
        this.pHeight = pHeight;
    }

    public boolean isLoad() {
        return mStyle != null;
    }

    public void loadSet(File file) {
        if (IDataor != null)
            IDataor.load(file);
    }

    public void saveSet(File file) {
        if (IDataor != null)
            IDataor.save(file);
    }

    public void reset() {
        if (IDataor != null) {
            IDataor.reset();
            List<String> files = IDataor.getOutFiles();
            for (String file : files) {
                FileUtil.delete(file);
            }
        }
        updateViews();
    }
//
//    public StyleInfo formXml(String xmlFile) {
//        XmlReader reader = new XmlReader();
//        InputStream inputStream = null;
//        StyleInfo styleInfo = null;
//        try {
//            inputStream = new FileInputStream(xmlFile);
//            styleInfo = reader.from(inputStream, StyleInfo.class);
//            styleInfo.setFile(xmlFile, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            FileUtil.close(inputStream);
//        }
//        return styleInfo;
//    }

    public Error loadStyle(String zipFile) {
        if (TextUtils.isEmpty(zipFile))
            return Error.UnknownError;
        mStyle = parseStyle(zipFile);
        Error e = check(mStyle);
        if (e != Error.None) return e;
        if (IDataor != null) {
            IDataor.bind(mStyle);
        }
        return Error.None;
    }

    public static Error check(Style pStyleInfo) {
        if (pStyleInfo == null) return Error.UnknownError;
        StyleInfo styleInfo = pStyleInfo.getStyleInfo();
        if (styleInfo == null) return Error.InfoEmpty;
        if (styleInfo.getAppversion() < 0) return Error.UnknownVersion;
        if (styleInfo.getAppversion() > Version) return Error.NeedUpdate;
        if (!FileUtil.exists(pStyleInfo.getFile())) return Error.NoData;
        if (pStyleInfo.isEmpty()) return Error.NoLayout;
        return Error.None;
    }

    public float initViews(float s) {
        if (mStyle == null || mViewGroup == null) {
            if (DEBUG)
                Log.w("kk", "viewgroup is null");
            return s;
        }
        mViewGroup.removeAllViews();
        if (s <= 0)
            s = getDefaultScale();
        mStyle.setScale(s);
        if (IDataor == null) {
            IDataor = new DefaultData(context);
            IDataor.bind(mStyle);
            if (Drawer.DEBUG)
                Log.i("kk", "create default dataor");
        }
        ViewCreator creator = new ViewCreator(context);
        mView = creator.draw(mStyle, mViewGroup, IDataor);
        updateViews();
        return s;
    }

    public void updateViews() {
        if (mStyle == null || mViewGroup == null) return;
        updateView(mViewGroup);
    }

    protected void updateView(View view) {
        if (view == null) return;
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                updateView(vg.getChildAt(i));
            }
        }
        if (view instanceof IKView<?,?,?>) {
            IKView<ViewData, ViewInfo, View> ikView = (IKView<ViewData, ViewInfo, View>) view;
            ViewGroup.LayoutParams params = ScaleHelper.getLayoutParams(ikView,
                    view.getParent(), mStyle.getScale());
            view.setLayoutParams(params);
            ViewData viewData = IDataor.get(ikView.getViewElement());
            if (viewData != null) {
                ikView.update(viewData);
            }
        } else {
            if (DEBUG) {
                Log.w("kk", view + " update null");
            }
        }
    }

    /***
     * xml,zip/style
     *
     * @param zip
     * @return
     */
    public static Style parseStyle(String zip) {
        if (TextUtils.isEmpty(zip)) return null;
        ZipFile zipFile = null;
        InputStream inputStream = null;
        Style style = null;
        boolean isXml = zip.endsWith(".xml");
        try {
            if (isXml) {
                inputStream = new FileInputStream(zip);
            } else {
                zipFile = new ZipFile(zip);
                ZipEntry entry = zipFile.getEntry(XML_STYLE_NAME);
                if (entry != null) {
                    inputStream = zipFile.getInputStream(entry);
                }
            }
            if (inputStream != null) {
                style = XmlUtils.getObject(Style.class, inputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(inputStream);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                FileUtil.close(zipFile);
            }
        }
        if (style != null) {
            if (isXml) {
                StyleInfo styleInfo = style.getStyleInfo();
                style.setFile(getDataFile(style, zip));
            } else {
                style.setFile(zip);
            }
        }
        return style;
    }

    public static String getDataFile(Style style, String zip) {
        if (TextUtils.isEmpty(zip)) return "";
        if (style == null) {
            return zip.replace(".xml", ".zip");
        } else {
            String file = style.getFile();
            if (!TextUtils.isEmpty(file)) {
                if (FileUtil.exists(file)) {
                    return file;
                } else {
                    File file1 = FileUtil.file(FileUtil.getParent(zip), file);
                    if (file1 != null) {
                        return file1.getAbsolutePath();
                    }
                }
            }
        }
        return "";
    }

    /***
     * 获取样式
     *
     * @param dir     样式的目录
     * @param filters 格式过滤
     * @return
     */
    public static ArrayList<StyleInfo> getStyleList(String dir, String... filters) {
        ArrayList<StyleInfo> list = new ArrayList<>();
        if (TextUtils.isEmpty(dir)) return list;
        File[] files = FileUtil.listFiles(dir, filters);
        if (files != null) {
            for (File f : files) {
                Log.d("xml", "file=" + f.getAbsolutePath());
                Style style = parseStyle(f.getAbsolutePath());
                if (style != null) {
                    Error error = check(style);
                    if (error == Error.None && style.getStyleInfo() != null) {
                        StyleInfo styleInfo = style.getStyleInfo();
                        styleInfo.setFilepath(f.getAbsolutePath());
                        list.add(styleInfo);
                    }
                }
            }
        }
        return list;
    }

    public boolean isEmpty(){
        return  (mStyle == null || mViewGroup == null);
    }

    public void scale(float sc) {
        if (isEmpty()) return;
        scale(mViewGroup, sc);
        updateViews();
    }

    public float scaleFit() {
        if (isEmpty()) return 1.0f;
        float sc = getDefaultScale();
        scale(sc);
        return sc;
    }

    public float getDefaultScale() {
        if (isEmpty()) return 1.0f;
        if (DEBUG) {
            Log.i("kk", mStyle.getWidth() + "/" + pWidth + "," + mStyle.getHeight() + "/" + pHeight);
        }
        float s = ScaleHelper.getScale(pWidth, pHeight, mStyle.getWidth(), mStyle.getHeight());
        return s;
    }

    public Bitmap getImage() {
        if (isEmpty()) return null;
        return BitmapUtil.getBitmap(mView);
    }

    protected void scale(View view, float sc) {
        if (isEmpty()) return;
        mStyle.setScale(sc);
        if (view == null) return;
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                scale(vg.getChildAt(i), sc);
            }
        }
        if (view instanceof IKView) {
            View v  = ((IKView<?,?,View>) view).getView();
            ViewParent vp= v==null?null:v.getParent();
            ScaleHelper.getLayoutParams((IKView<?,?,View>) view, vp, sc);
        }
    }
}
