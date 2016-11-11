package com.kk.imageeditor.draw;

import android.content.Context;


import com.kk.imageeditor.view.IKView;

import java.io.File;

class DefaultData extends IDataProvider {
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
}