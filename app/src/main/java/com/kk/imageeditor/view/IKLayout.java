package com.kk.imageeditor.view;


import android.view.View;
import android.view.ViewGroup;

import com.kk.imageeditor.bean.data.LayoutData;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.view.LayoutInfo;
import com.kk.imageeditor.bean.view.ViewInfo;

/**
 * View的控制接口
 */
public interface IKLayout<D extends LayoutData, E extends LayoutInfo, V extends ViewGroup> extends IKView<D, E,V> {

    int getReadHeight();

    int getReadWidth();

}
