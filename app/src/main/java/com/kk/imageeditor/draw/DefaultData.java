package com.kk.imageeditor.draw;

import android.content.Context;
import android.text.TextUtils;


import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.view.IKView;

import java.io.File;

class DefaultData extends IDataProvider {

    String tempPath;
    String cachePath;

    public DefaultData(Context pContext) {
        super(pContext);
    }

    @Override
    public boolean onEdit(IKView pIKView) {
        return false;
    }

    @Override
    public boolean save(File file) {
        return false;
    }

    @Override
    public boolean load(File file) {
        return false;
    }

    @Override
    public void bind(Style pStyleInfo) {
        super.bind(pStyleInfo);
        if (pStyleInfo.getInfo().isFolder()) {
            tempPath = new File(pStyleInfo.getDataFile(), "temp").getAbsolutePath();
            cachePath = new File(pStyleInfo.getDataFile(), "cache").getAbsolutePath();
        } else {
            String path = new File(pStyleInfo.getDataFile()).getParent();
            tempPath = new File(path, "temp").getAbsolutePath();
            cachePath = new File(path, "cache").getAbsolutePath();
        }
    }

    @Override
    public String getCachePath(String name) {
        if (TextUtils.isEmpty(name)) {
            return cachePath;
        }
        return new File(cachePath, name).getAbsolutePath();
    }

    @Override
    public String getTempPath(String name) {
        if (TextUtils.isEmpty(name)) {
            return tempPath;
        }
        return new File(tempPath, name).getAbsolutePath();
    }
}