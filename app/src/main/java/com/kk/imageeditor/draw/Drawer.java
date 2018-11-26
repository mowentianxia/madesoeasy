package com.kk.imageeditor.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.kk.common.utils.BitmapUtil;
import com.kk.common.utils.FileUtil;
import com.kk.common.utils.XmlUtils;
import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.StyleData;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.view.ViewInfo;
import com.kk.imageeditor.view.IKView;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

import static com.kk.imageeditor.Constants.DEBUG;

public class Drawer {
    protected Context context;
    protected IDataProvider mDataProvider;
    protected ViewGroup mViewGroup;
    protected Style mStyle;
    protected View mView;
    protected int maxWidth;
    protected int maxHeight;
    private float mScale;
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
    public boolean loadSet(File file) {
        if (mDataProvider != null)
            return mDataProvider.load(file);
        return false;
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
        if (isEmpty()) return;
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
        StyleInfo styleInfo = getStyleInfo(new File(zipFile));
        Error e = check(styleInfo);
        if (e != Error.None) {
            if (DEBUG)
                Log.e("msoe", "check style fail");
            return e;
        }
        StyleData data = getStyleData(styleInfo);
        if (data == null) {
            if (DEBUG)
                Log.e("msoe", "get data fail");
            return Error.NoData;
        }
        Style style = getStyle(styleInfo);
        if (style == null) {
            if (DEBUG)
                Log.e("msoe", "get layout fail");
            return Error.NoLayout;
        }
        style.setInfo(styleInfo);
        style.setData(data);
        mStyle = style;
        if (mDataProvider != null) {
            mDataProvider.bind(mStyle);
        }
        return Error.None;
    }

    /***
     * 检查错误
     *
     * @return
     */
    public static Error check(StyleInfo styleInfo) {
        if (styleInfo == null) return Error.InfoEmpty;
        if (styleInfo.getAppversion() < 0) return Error.UnknownVersion;
        if (styleInfo.getAppversion() > Version) return Error.NeedUpdate;
        if (TextUtils.isEmpty(styleInfo.getDataXml())) return Error.NoData;
        if (TextUtils.isEmpty(styleInfo.getLayoutXml())) return Error.NoLayout;
        return Error.None;
    }


    public static StyleInfo getStyleInfo(File f) {
        StyleInfo styleInfo = XmlUtils.getStyleUtils().parseXml(StyleInfo.class, f, Constants.STYLE_XMl);
        if (styleInfo != null) {
            styleInfo.setStylePath(f.getAbsolutePath());
            Error error = check(styleInfo);
            if (error == Drawer.Error.None) {
                return styleInfo;
            }
            if (DEBUG)
                Log.e("msoe", "get style:" + styleInfo);
        } else {
            if (DEBUG)
                Log.e("msoe", "get style:" + f);
        }
        return null;
    }

    protected static Style getStyle(StyleInfo styleInfo) {
        Style info;
        if (styleInfo.isFolder()) {
            info = XmlUtils.getStyleUtils().parseXml(Style.class,
                    new File(styleInfo.getDataPath(), styleInfo.getLayoutXml()).getAbsoluteFile(),
                    null);
        } else {
            info = XmlUtils.getStyleUtils().parseXml(Style.class,
                    new File(styleInfo.getDataPath()),
                    styleInfo.getLayoutXml());
        }
        return info;
    }

    protected static StyleData getStyleData(StyleInfo styleInfo) {
        StyleData info;
        if (styleInfo.isFolder()) {
            File xml = new File(styleInfo.getDataPath(), styleInfo.getDataXml());
            if (DEBUG)
                Log.d("kk", "load from folder " + xml);
            info = XmlUtils.getStyleUtils().parseXml(StyleData.class,
                    xml,
                    null);
        } else {
            if (DEBUG)
                Log.d("kk", "load from zip " + styleInfo.getDataPath() + "!" + styleInfo.getDataXml());
            info = XmlUtils.getStyleUtils().parseXml(StyleData.class,
                    new File(styleInfo.getDataPath()),
                    styleInfo.getDataXml());
        }
        return info;
    }

    public static Bitmap readImage(StyleInfo styleInfo, String zipname, int w, int h) {
        Bitmap bmp = null;
        if (styleInfo.isFolder()) {
            File imgfile = FileUtil.file(styleInfo.getDataPath(), zipname);
            bmp = BitmapUtil.getBitmapFromFile(imgfile.getAbsolutePath(), w, h);
        } else {
            ZipFile zipFile = null;
            try {
                zipFile = new ZipFile(styleInfo.getDataPath());
                bmp = BitmapUtil.getBitmapFormZip(zipFile, zipname, w, h);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                FileUtil.closeZip(zipFile);
            }
        }
        return bmp;
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
        if (s <= 0) {
            s = getDefaultScale();
            mScale = s;
        }
        mStyle.setScale(s);
        if (mDataProvider == null) {
            mDataProvider = new DefaultData(context);
            mDataProvider.bind(mStyle);
            if (DEBUG)
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
        }
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
//        Log.i("msoe", mStyle.getWidth() + "/" + maxWidth + "," + mStyle.getHeight() + "/" + maxHeight);
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
        return BitmapUtil.getBitmapFromFile(mView);
    }

    public float getScale() {
        return mScale;
    }

    /***
     * 缩放
     *
     * @param view
     * @param sc
     */
    protected void scale(View view, float sc) {
        if (isEmpty()) return;
        if (sc <= 0) {
            sc = getDefaultScale();
        }
        mScale = sc;
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
