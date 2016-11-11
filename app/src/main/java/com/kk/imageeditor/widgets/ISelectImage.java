package com.kk.imageeditor.widgets;

public interface ISelectImage {

    void setListener(ISelectImageListener listener);

    void startPhotoCut(String file, int width, int height, boolean needSelect);
}
