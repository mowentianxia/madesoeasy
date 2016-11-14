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
    protected Context context;
    protected IDataProvider mDataProvider;
    protected ViewGroup mViewGroup;
    protected Style mStyle;
    protected View mView;
    protected int maxWidth;
    protected int maxHeight;
    public static final long Version = 3;

    public enum Error {
        /**
         * 没错
         */
        None,
        /**
         * 没有样式信息
         */
        InfoEmpty,
        /**
         * 版本过低，需要更新
         */
        NeedUpdate,
        /**
         * 没有数据
         */
        NoData,
        /**
         * 没有主布局
         */
        NoLayout,
        /**
         * 未知版本
         */
        UnknownVersion,
        /**
         * 未知错误
         */
        UnknownError
    }

    /***
     * @param context
     * @param pViewGroup 布局容器
     * @param maxWidth   最大宽度
     * @param maxHeight  最大高度
     * @param pIDataor   内容提供
     */
    public Drawer(Context context, ViewGroup pViewGroup, int maxWidth, int maxHeight, IDataProvider pIDataor) {
        this.context = context;
        this.mDataProvider = pIDataor == null ?
                new DefaultData(context) : pIDataor;
        this.mViewGroup = pViewGroup;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    /***
     * @return 是否加载成功
     */
    public boolean isLoad() {
        return mStyle != null;
    }

    /***
     * @param file 加载存档
     */
    public void loadSet(File file) {
        if (mDataProvider != null)
            mDataProvider.load(file);
    }

    /***
     * @param file 保存存档
     */
    public void saveSet(File file) {
        if (mDataProvider != null)
            mDataProvider.save(file);
    }

    /***
     * 重置信息
     */
    public void reset() {
        if(isEmpty())return;
        if (mDataProvider != null) {
            mDataProvider.reset();
            List<String> files = mDataProvider.getOutFiles();
            for (String file : files) {
                FileUtil.delete(file);
            }
        }
        updateViews();
    }

    /***
     * @param zipFile 样式zip/xml
     * @return 加载样式
     */
    public Error loadStyle(String zipFile) {
        if (TextUtils.isEmpty(zipFile))
            return Error.UnknownError;
        mStyle = parseStyle(zipFile);
        Error e = check(mStyle);
        if (e != Error.None) return e;
        if (mDataProvider != null) {
            mDataProvider.bind(mStyle);
        }
        return Error.None;
    }

    /***
     * 检查错误
     *
     * @param pStyleInfo
     * @return
     */
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

    /***
     * 初始化
     *
     * @param s 比例 如果<=0，则自适应
     * @return 当前比例
     */
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
        if (mDataProvider == null) {
            mDataProvider = new DefaultData(context);
            mDataProvider.bind(mStyle);
            if (Drawer.DEBUG)
                Log.i("kk", "create default dataor");
        }
        ViewCreator creator = new ViewCreator(context);
        mView = creator.draw(mStyle, mViewGroup, mDataProvider);
        updateViews();
        return s;
    }

    /***
     * 刷新视图
     */
    public void updateViews() {
        if (mStyle == null || mViewGroup == null) return;
        updateView(mViewGroup);
    }

    /***
     * 刷新某个视图
     *
     * @param view
     */
    protected void updateView(View view) {
        if (view == null) return;
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                updateView(vg.getChildAt(i));
            }
        }
        if (view instanceof IKView<?, ?, ?>) {
            IKView<ViewData, ViewInfo, View> ikView = (IKView<ViewData, ViewInfo, View>) view;
            ViewGroup.LayoutParams params = ScaleHelper.getLayoutParams(ikView,
                    view.getParent(), mStyle.getScale());
            view.setLayoutParams(params);
            ViewData viewData = mDataProvider.get(ikView.getViewElement());
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
            FileUtil.closeZip(zipFile);
        }
        if (style != null) {
            if (isXml) {
                style.setFile(getDataFile(style.getStyleInfo(), zip));
            } else {
                style.setFile(zip);
            }
        }
        return style;
    }

    /***
     * 获取数据包
     *
     * @param style 样式
     * @param file  zip/xml
     * @return
     */
    protected static String getDataFile(StyleInfo style, String xml) {
        if (TextUtils.isEmpty(xml)) return "";
        if (style == null) {
            return xml.replace(".xml", ".zip");
        } else {
            String file = style.getFilepath();
            if (!TextUtils.isEmpty(file)) {
                if (FileUtil.exists(file)) {
                    return file;
                } else {
                    return new File(FileUtil.getParent(xml), file).getAbsolutePath();
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

    public boolean isEmpty() {
        return (!isLoad() || mViewGroup == null);
    }

    /***
     * 缩放视图
     *
     * @param sc 比例
     */
    public void scale(float sc) {
        if (isEmpty()) return;
        scale(mViewGroup, sc);
        updateViews();
    }

    /***
     * 适应最大宽高
     *
     * @return
     */
    public float scaleFit() {
        if (isEmpty()) return 1.0f;
        float sc = getDefaultScale();
        scale(sc);
        return sc;
    }

    /***
     * 默认比例
     */
    public float getDefaultScale() {
        if (isEmpty()) return 1.0f;
        if (DEBUG) {
            Log.i("kk", mStyle.getWidth() + "/" + maxWidth + "," + mStyle.getHeight() + "/" + maxHeight);
        }
        float s = ScaleHelper.getScale(maxWidth, maxHeight, mStyle.getWidth(), mStyle.getHeight());
        return s;
    }

    /***
     * 获取图片
     *
     * @return
     */
    public Bitmap getImage() {
        if (isEmpty()) return null;
        return BitmapUtil.getBitmap(mView);
    }

    /***
     * 缩放
     *
     * @param view
     * @param sc
     */
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
            View v = ((IKView<?, ?, View>) view).getView();
            ViewParent vp = v == null ? null : v.getParent();
            ScaleHelper.getLayoutParams((IKView<?, ?, View>) view, vp, sc);
        }
    }
}
