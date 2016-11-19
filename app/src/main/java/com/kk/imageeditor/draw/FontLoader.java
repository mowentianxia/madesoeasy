package com.kk.imageeditor.draw;


import android.graphics.Typeface;


import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.FontInfo;
import com.kk.imageeditor.utils.FileUtil;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

public class FontLoader {
    public FontLoader() {
        list = new HashMap<>();
    }

    protected final HashMap<String, SoftReference<Typeface>> list;

    protected Typeface get(String fontlink) {
        if (list.containsKey(fontlink)) {
            SoftReference<Typeface> e = list.get(fontlink);
            if (e != null && e.get() != null) {
                return e.get();
            }
        }
        return null;
    }

    public Typeface getFont(FontInfo fontInfo, StyleInfo styleInfo,String tempPath) {
        if (fontInfo == null) return null;
        Typeface face = get(fontInfo.getTag());
        if (face != null)
            return face;
        File fontFile = null;
        if(styleInfo.isFolder()){
            fontFile = new File(styleInfo.getDataPath(), fontInfo.getFont());
        }else{
            fontFile = new File(tempPath,  fontInfo.getFont());
            if(!fontFile.exists()){
                FileUtil.copyFromZip(styleInfo.getDataPath(), fontInfo.getFont(), fontFile.getAbsolutePath());
            }
        }
        if (fontFile.exists()) {
            try {
                face = Typeface.createFromFile(fontFile);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
        if (face != null) {
            list.put(fontInfo.getTag(), new SoftReference<>(face));
        }
        return face;
    }

    public void clear() {
        for (Map.Entry<String, SoftReference<Typeface>> e : list.entrySet()) {
            SoftReference<Typeface> v = e.getValue();
            if (v != null) {
                v.clear();
            }
        }
        list.clear();
    }
}