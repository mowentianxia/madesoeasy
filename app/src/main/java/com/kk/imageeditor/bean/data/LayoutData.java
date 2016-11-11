package com.kk.imageeditor.bean.data;

import com.kk.imageeditor.bean.enums.LayoutType;

public class LayoutData extends ViewData {
    public LayoutType layoutType = LayoutType.frame;

    @Override
    public String toString() {
        return "LayoutData{" +
                "layoutType='" + layoutType + '\'' +
                '}';
    }
}
