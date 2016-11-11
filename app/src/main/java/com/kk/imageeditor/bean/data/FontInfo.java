package com.kk.imageeditor.bean.data;

import net.kk.xml.annotations.XmlAttribute;

public class FontInfo extends When<FontInfo> {
    @XmlAttribute("name")
    private String font;
    @XmlAttribute("style")
    private Style fontstyle = Style.normal;
    @XmlAttribute("size")
    private float size;

    @Override
    public FontInfo get() {
        return this;
    }

    public String getFont() {
        return font;
    }

    public Style getFontstyle() {
        return fontstyle;
    }

    public float getSize() {
        return size;
    }


    public String getTag() {
        return ""+getFont();
    }

    public enum Style {
        normal,
        bold,
        italic,
        bold_italic,
    }

    @Override
    public String toString() {
        return "FontInfo{" +
                "when='" + when + "'," +
                "font='" + font + '\'' +
                ", fontstyle=" + fontstyle +
                ", size=" + size +
                '}';
    }
}