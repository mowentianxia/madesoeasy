package com.kk.imageeditor.bean;

import com.kk.imageeditor.utils.MD5Util;

import net.kk.xml.annotations.XmlElement;

@XmlElement("style")
public class StyleInfoEx {
    @XmlElement("info")
    private StyleInfo info;

    @XmlElement("style")
    private String stylePath;

    @XmlElement("md5")
    private String md5;

    public StyleInfoEx() {

    }

    public StyleInfoEx(StyleInfo info) {
        this.info = info;
        if (info != null) {
            this.stylePath = info.getStylePath();
            this.md5 = MD5Util.getFileMD5(stylePath);
        }
    }

    public String getStylePath() {
        return stylePath;
    }

    public StyleInfo getInfo() {
        return info;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

}
