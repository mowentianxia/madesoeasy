package com.kk.imageeditor.bean.view;

import com.kk.imageeditor.bean.data.FontInfo;
import com.kk.imageeditor.bean.data.ShadowInfo;
import com.kk.imageeditor.bean.data.Value;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;

import java.util.Arrays;

@XmlElement("text")
public class TextInfo extends ViewInfo {

    @XmlElement("font")
    protected FontInfo[] font;
    @XmlElement("color")
    protected Value[] color;
    @XmlAttribute("singleline")
    protected boolean singleline = true;
    @XmlAttribute("keepWord")
    protected boolean keepWord = true;
    @XmlAttribute("line-space")
    protected float lineSpace;
    @XmlAttribute("line-space-scale")
    protected float lineSpaceScale;
    @XmlElement("align")
    protected Value[] alignStr;
    @XmlElement("shadow")
    protected ShadowInfo[] shadows;
    @XmlElement("text")
    protected String text;
    @XmlAttribute("justify")
    protected boolean justify;

    public ShadowInfo[] getShadows() {
        return shadows;
    }

    public boolean isKeepWord() {
        return keepWord;
    }

    public float getLineSpace() {
        return lineSpace;
    }

    public float getLineSpaceScale() {
        return lineSpaceScale;
    }

    public boolean isSingleline() {
        return singleline;
    }

    public String getText() {
        return text;
    }

    public Value[] getColor() {
        return color;
    }

    public FontInfo[] getFont() {
        return font;
    }

    public Value[] getAlignStr() {
        return alignStr;
    }

    public boolean isJustify() {
        return justify;
    }

    @Override
    public String toString() {
        return "TextElement{" +
                "name='" + name + "'," +
                " font=" + Arrays.toString(font) +
                ", sizes='" + Arrays.toString(sizes) + '\'' +
                ", gravity='" + Arrays.toString(gravityStr) + '\'' +
                ", align='" + Arrays.toString(alignStr) + '\'' +
                ", visible='" + visible +"'"+
                ", index=" + index +
                ", click=" + clickName +
                ", color='" + Arrays.toString(color) + '\'' +
                ", singleline=" + singleline +
                ", keepWord=" + keepWord +
                ", lineSpace=" + lineSpace +
                ", lineSpaceScale=" + lineSpaceScale +
                ", text='" + text + '\'' +
                ", justify ="+justify+
                '}';
    }
}