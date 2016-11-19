package com.kk.imageeditor.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.BooleanElement;
import com.kk.imageeditor.bean.data.FontInfo;
import com.kk.imageeditor.bean.data.ImageData;
import com.kk.imageeditor.bean.data.LayoutData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.ShadowInfo;
import com.kk.imageeditor.bean.data.Size;
import com.kk.imageeditor.bean.data.SubItem;
import com.kk.imageeditor.bean.data.TextData;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.data.When;
import com.kk.imageeditor.bean.enums.DataType;
import com.kk.imageeditor.bean.view.ImageInfo;
import com.kk.imageeditor.bean.view.LayoutInfo;
import com.kk.imageeditor.bean.view.TextInfo;
import com.kk.imageeditor.bean.view.ViewInfo;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.Calculator;
import com.kk.imageeditor.utils.FileUtil;
import com.kk.imageeditor.utils.GravityUtil;
import com.kk.imageeditor.utils.JudgUtils;
import com.kk.imageeditor.view.IKView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class IDataProvider {
    protected Context context;
    public static final String CACHE = "cache";
    public static final String TEMP = "temp";

    public IDataProvider(Context pContext) {
        context = pContext;
        bools = new HashMap<>();
        values = new HashMap<>();
        datas = new HashMap<>();
        mFontLoader = new FontLoader();
    }

    final static Pattern NamePattern = Pattern.compile("\\{\\{[a-zA-Z0-9_-]+\\}\\}");

    protected Style mStyle;
    protected final FontLoader mFontLoader;

    protected final HashMap<String, String> values;

    protected final Map<String, Boolean> bools;

    protected final Map<ViewInfo, ViewData> datas;

    public boolean isLoad() {
        return mStyle != null;
    }

    public void bind(Style pStyleInfo) {
        this.mStyle = pStyleInfo;
        if (isLoad()) {
            inidData();
        }
    }

    public abstract boolean onEdit(IKView pIKView);

    public void onChanged(SelectElement pSelectInfo, String... value) {
        if (pSelectInfo == null || value == null || value.length == 0) return;
        String name = pSelectInfo.getName();
        String[] names;
        if (pSelectInfo.getType() == DataType.multi_select) {
            List<SubItem> items = pSelectInfo.getItems();
            int count = Math.min(value.length, items.size());
            names = new String[count];
            for (int i = 0; i < count; i++) {
                names[i] = items.get(i).getName();
                values.put(names[i], value[i]);
                if (Constants.DEBUG) {
                    Log.d("kk", "update " + names[i] + "=" + value[i]);
                }
            }

        } else {
//            if (pSelectInfo.getType() == DataType.image) {
//                String tofile=getTempPath()
//                FileUtil.re
//            }
//                values.put(name, value[0]);
//                if (imageeditor.DEBUG) {
//                    Log.d("kk", "update " + name + "=" + value[0]);
//                }
//            }
            values.put(name, value[0]);
            names = new String[]{name};
        }
        setBools(names);
        initValues(true);
        //     updateAllBools();
        updateDatas(false);
    }
    public abstract String getCachePath(String name);
    public abstract String getTempPath(String name);

    public String getSaveFileName() {
        if (!isLoad()) return "";
        return dealString(mStyle.getSavename());
    }
//
//    protected void changeImage(SelectInfo pSelectInfo, String value) {
//        String name = pSelectInfo.getName();
//        //删除旧的图片
//        String old = getFilePath(values.get(name));
//        //根据变量重命名
//        String filename = dealString(pSelectInfo.getDefault());
//        if (!TextUtils.equals(old, value)) {
//            FileUtil.delete(old);
//            FileUtil.rename(value, getFilePath(filename));
//        } else {
//            //更新关联图片
//            for (ViewData data : datas.values()) {
//                if (data instanceof ImageData) {
//                    ImageData id = (ImageData) data;
//                    if (TextUtils.equals(id.file_src, old)) {
//                        id.outFile = true;
//                        id.setSrc(getDrawable(filename));
//                    }
//                } else if (data instanceof LayoutData) {
//                    LayoutData ld = (LayoutData) data;
//                    if (TextUtils.equals(ld.file_background, old)) {
//                        ld.outFile = true;
//                        ld.setBackground(getDrawable(filename));
//                    }
//                }
//            }
//        }
//        values.put(name, filename);
//    }

    protected List<String> setBools(String... names) {
        List<String> tmp = new ArrayList<>();
        if (!isLoad()) return tmp;
        List<BooleanElement> booleanInfos = mStyle.getBooleanElements();
        for (BooleanElement be : booleanInfos) {
            for (String v : names) {
                if (be.getValue().contains("{{" + v + "}}")) {
                    bools.put(be.getName(), JudgUtils.getBoolean(dealString(be.getValue()), false));
                    tmp.add(be.getName());
                }
            }
        }
        for (BooleanElement be : booleanInfos) {
            for (String v : tmp) {
                if (be.getValue().contains("{{" + v + "}}")) {
                    bools.put(be.getName(), JudgUtils.getBoolean(dealString(be.getValue()), false));
                }
            }
        }
        return tmp;
    }

    public ArrayList<String> getOutFiles() {
        ArrayList<String> files = new ArrayList<>();
        for (Map.Entry<ViewInfo, ViewData> e : datas.entrySet()) {
            ViewData v = e.getValue();
            if (v != null && v instanceof ImageData) {
                ImageData iv = (ImageData) v;
                if (iv.outFile) {
                    files.add(getTempPath(iv.file_src));
                }
            }
        }
        return files;
    }

    protected void initValues(boolean update) {
        if (!isLoad()) return;
        List<SelectElement> selectInfos = mStyle.getSelectElements();
        for (SelectElement selectInfo : selectInfos) {
            if (update) {
                if (selectInfo.getType() == DataType.image) {
                    values.put(selectInfo.getName(), dealString(selectInfo.getDefault()));
                }
            } else {
                if (selectInfo.getType() == DataType.multi_select) {
                    List<SubItem> items = selectInfo.getItems();
                    for (SubItem item : items) {
                        String name = item.getName();
                        SelectElement link = mStyle.getDataElement(name);
                        if (link == null) {
                            values.put(name, "");
                            continue;
                        }
                        values.put(name, dealString(link.getDefault()));
                    }
                } else {
                    values.put(selectInfo.getName(), dealString(selectInfo.getDefault()));
                }
            }
        }
    }

    protected void inidData() {
        if (!isLoad()) return;
        initValues(false);
        updateAllBools();
        updateDatas(true);
    }

    public void updateDatas(boolean init) {
        if (!isLoad()) return;
        updateAllData(mStyle.getLayoutInfo(), init);
    }

    protected void updateAllBools() {
        if (!isLoad()) return;
        List<BooleanElement> booleanInfos = mStyle.getBooleanElements();
        for (BooleanElement be : booleanInfos) {
            String name = be.getName();
            boolean value = JudgUtils.getBoolean(dealString(be.getValue()), false);
            bools.put(name, value);
        }
    }

    public String dealString(String str) {
        if (!isLoad()) return str;
        if (TextUtils.isEmpty(str)) return str;
        Matcher matcher = NamePattern.matcher(str);
        while (matcher.find()) {
            String key = matcher.group();
            String name = key.substring(2, key.length() - 2);
            String value = values.get(name);
            if (value == null) {
                value = "" + bools.get(name);
            }
            if (Constants.DEBUG) {
                Log.v("kk", "deal " + name + "=" + value);
            }
            str = str.replace(key, value == null ? "" : value);
        }
        return str;
    }

    public SelectElement getSelectElement(String name) {
        if (!isLoad()) return null;
        return mStyle.getDataElement(name);
    }

    public StyleInfo getStyleInfo() {
        if (!isLoad()) return null;
        return mStyle.getStyleInfo();
    }

    protected void updateAllData(LayoutInfo pInfo, boolean init) {
        TextInfo[] texts = pInfo.getTexts();
        if (texts != null) {
            for (TextInfo te : texts) {
                updateData(te);
            }
        }
        ImageInfo[] images = pInfo.getImages();
        if (images != null) {
            for (ImageInfo ie : images) {
                updateData(ie, init);
            }
        }
        LayoutInfo[] layouts = pInfo.getLayouts();
        if (layouts != null) {
            for (LayoutInfo le : layouts) {
                updateAllData(le, init);
            }
        }
        LayoutData data = get(pInfo);
        update(data, pInfo);
        data.layoutType = pInfo.getLayoutType();
        datas.put(pInfo, data);
    }

    protected void updateData(ImageInfo pInfo, boolean init) {
        if (!isLoad()) return;
        ImageData imageData = get(pInfo);
        update(imageData, pInfo);
        imageData.scaleType = pInfo.getScaleType();
        String srcVal = getValue(pInfo.getSrc());
        String file_src = dealString(srcVal);
        boolean outfile = false;
        if (imageData.outFile) {
            outfile = true;
//            if (imageeditor.DEBUG) {
//                Log.i("kk", "check " + file_src+"  "+srcVal);
//            }
        } else if (init) {
            SelectElement selectInfo = mStyle.getDataElement(pInfo.getClickName());
            if (selectInfo != null && selectInfo.getType() == DataType.image) {
                imageData.outFile = true;
                outfile = true;
            }
        }
        if (outfile) {
            String oldfile = getTempPath(imageData.file_src);
            String newfile = getTempPath(file_src);
            if (!FileUtil.exists(newfile)) {
                if (!TextUtils.equals(oldfile, newfile)) {
                    if (Constants.DEBUG) {
                        Log.i("kk", "rename " + oldfile + " " + newfile);
                    }
                    FileUtil.rename(oldfile, newfile);
                }
            }
        }
        if (outfile || !TextUtils.equals(file_src, imageData.file_src)) {
            imageData.file_src = file_src;
            imageData.setSrc(getDrawable(file_src, imageData.width, imageData.height));
        }
        datas.put(pInfo, imageData);
    }

    protected void updateData(TextInfo pInfo) {
        TextData data = get(pInfo);
        update(data, pInfo);
        FontInfo fontInfo = getValue(pInfo.getFont());
        if (fontInfo != null) {
            data.font = mFontLoader.getFont(fontInfo, mStyle.getStyleInfo(), getCachePath(""));
            data.font_size = fontInfo.getSize();// * mStyle.getScale();
            data.fontstyle = fontInfo.getFontstyle().ordinal();
        }
        String colorStr = dealString(getValue(pInfo.getColor()));
        data.color = toColor(colorStr);
        data.singleline = pInfo.isSingleline();
        data.keepWord = pInfo.isKeepWord();
        data.lineSpace = pInfo.getLineSpace();
        data.lineSpaceScale = pInfo.getLineSpaceScale();
        data.text = dealString(pInfo.getText());
        data.align = GravityUtil.getTextAlign(getValue(pInfo.getAlignStr()));
        //阴影
        ShadowInfo shadowInfo = getValue(pInfo.getShadows());
        if (shadowInfo != null) {
            data.shadowColor = toColor(shadowInfo.color);
            data.shadowDx = shadowInfo.dx;
            data.shadowDy = shadowInfo.dy;
            data.shadowRadius = shadowInfo.radius;
        }
        datas.put(pInfo, data);
    }

    protected void update(ViewData pViewData, ViewInfo pInfo) {
        Size size = getValue(pInfo.getSizes());
        if (size == null) {
            size = new Size();
        }
        pViewData.top = size.top;
        pViewData.left = size.left;
        pViewData.right = size.right;
        pViewData.bottom = size.bottom;
        pViewData.width = size.width;
        pViewData.height = size.height;
        pViewData.weight = size.weight;
        pViewData.gravity = GravityUtil.getAlign(getValue(pInfo.getGravityStr()));
        pViewData.index = pInfo.getIndex();
        String angle = dealString(getValue(pInfo.getAngle()));
        if (TextUtils.isEmpty(angle)) {
            pViewData.needRotate = false;
        } else {
            pViewData.needRotate = true;
            pViewData.angle = (float) Calculator.getResult(angle);
        }

        String vl = dealString(pInfo.getVisible());
        pViewData.visible = JudgUtils.getBoolean(vl, true);

        if (Constants.DEBUG) {
            Log.v("kk", "visable=" + pInfo.getVisible() + "    " + vl + "     " + pViewData.visible);
        }
        String file_background = dealString(pInfo.getBackground());
        if (TextUtils.isEmpty(file_background)) {
            pViewData.file_background = "";
            pViewData.setBackground(null);
        } else if (!TextUtils.equals(file_background, pViewData.file_background)) {
            pViewData.file_background = file_background;
            pViewData.setBackground(getDrawable(file_background, pViewData.width, pViewData.height));
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T getValue(When<T>... whens) {
        if (whens == null) {
            return null;
        }
        for (When<T> eWhen : whens) {
            if (eWhen == null) {
                continue;
            }
            if (TextUtils.isEmpty(eWhen.getWhen())) {
                return eWhen.get();
            }
            if (JudgUtils.getBoolean(dealString(eWhen.getWhen()), true)) {
                return eWhen.get();
            }
        }
        return null;
    }

    public HashMap<String, String> getValues() {
        return values;
    }

    public void updateValues(Map<String, String> datas) {
        values.putAll(datas);
        initValues(true);
        updateAllBools();
        updateDatas(false);
    }

    public String getValue(String name) {
        return values.get(name);
    }

    public boolean getBool(String name) {
        return bools.get(name) == Boolean.TRUE;
    }

    protected Drawable getDrawable(String name, float w, float h) {
        if (name == null || !isLoad()) return null;
        if (name.startsWith("#")) {
            return new ColorDrawable(toColor(name));
        }
        String zipname = dealString(name);
        File imgfile = FileUtil.file(getTempPath(zipname));
        Bitmap bmp = BitmapUtil.getBitmapFromFile(imgfile.getAbsolutePath(), (int) w, (int) h);
        if (bmp == null) {
            bmp = Drawer.readImage(mStyle.getStyleInfo(), zipname, (int)w, (int)h);
        }
        return new BitmapDrawable(context.getResources(), bmp);
    }

    protected int toColor(String name) {
        int color = Color.TRANSPARENT;
        try {
            color = Color.parseColor(name);
        } catch (Exception e) {

        }
        return color;
    }


    public void reset() {
        if (isLoad()) {
            values.clear();
            datas.clear();
            bools.clear();
            inidData();
        }
    }

    /**
     * @param file 存档
     * @return 保存成功
     */
    public abstract boolean save(File file);

    /**
     * @param file 存档
     * @return 加载成功
     */
    public abstract boolean load(File file);

    public ViewData get(ViewInfo pInfo) {
        if (pInfo instanceof ImageInfo) {
            return get((ImageInfo) pInfo);
        }
        if (pInfo instanceof LayoutInfo) {
            return get((LayoutInfo) pInfo);
        }
        if (pInfo instanceof TextInfo) {
            return get((TextInfo) pInfo);
        }
        return null;
    }

    /**
     * @param pImageInfo 图片元素
     * @return 图片元素的数据
     */
    public ImageData get(ImageInfo pImageInfo) {
        ViewData data = datas.get(pImageInfo);
        if (data != null && data instanceof ImageData) {
            return (ImageData) data;
        }
        return new ImageData();
    }

    /**
     * @param pTextInfo 文字元素
     * @return 文字元素的数据
     */
    public TextData get(TextInfo pTextInfo) {
        ViewData data = datas.get(pTextInfo);
        if (data != null && data instanceof TextData) {
            return (TextData) data;
        }
        return new TextData();
    }

    /**
     * @param pLayoutInfo 布局元素
     * @return 布局元素的数据
     */
    public LayoutData get(LayoutInfo pLayoutInfo) {
        ViewData data = datas.get(pLayoutInfo);
        if (data != null && data instanceof LayoutData) {
            return (LayoutData) data;
        }
        return new LayoutData();
    }

    public void cleanCache() {
        String dir = getCachePath("");
        File f = new File(dir);
        if (f.exists()) {
            if (f.isDirectory()) {
                FileUtil.delete(f);
            }
        }
    }


    public boolean copyFonts() {
        if (!isLoad()) return false;
        if (mStyle.getInfo().isFolder()) {
            return true;
        }
        ArrayList<String> list = new ArrayList<>();
        getFonts(list, mStyle.getLayoutInfo());
        if (!FileUtil.exists(mStyle.getDataFile())) {
            return false;
        }
        for (String file : list) {
            FileUtil.copyFromZip(mStyle.getDataFile(), file, getCachePath(file));
        }
        return true;
    }

    protected void getFonts(ArrayList<String> list, LayoutInfo pLayoutInfo) {
        TextInfo[] texts = pLayoutInfo.getTexts();
        if (texts != null) {
            for (TextInfo te : texts) {
                if (te == null) continue;
                FontInfo[] fontInfos = te.getFont();
                if (fontInfos != null) {
                    for (FontInfo ff : fontInfos) {
                        if (ff != null) {
                            if (!list.contains(ff.getFont())) {
                                list.add(ff.getFont());
                            }
                        }
                    }
                }
            }
        }
        LayoutInfo[] layouts = pLayoutInfo.getLayouts();
        if (layouts != null) {
            for (LayoutInfo le : layouts) {
                getFonts(list, le);
            }
        }
    }
}
