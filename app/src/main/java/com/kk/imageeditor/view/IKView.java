package com.kk.imageeditor.view;


import android.view.View;

import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.view.ViewInfo;


/**
 * View的控制接口
 */
public interface IKView<D extends ViewData, E extends ViewInfo, V extends View> {

    boolean update(D item);

    D getDataItem();

    SelectElement getSelectElement();

    void bind(SelectElement item, E pViewElement);

    E getViewElement();

    V getView();

}