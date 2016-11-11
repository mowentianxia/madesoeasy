package com.kk.imageeditor.bean.data;

import android.text.TextUtils;

import net.kk.xml.annotations.XmlAttribute;

public abstract class When<T> {
    @XmlAttribute("when")
    protected String when;

    public String getWhen() {
        return when;
    }

    public boolean hasWhen() {
        return !TextUtils.isEmpty(when);
    }

    public abstract T get();
}
