package com.kk.imageeditor.bean.view;

import com.kk.imageeditor.bean.IName;
import com.kk.imageeditor.bean.data.Size;
import com.kk.imageeditor.bean.data.Value;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;

import java.util.Comparator;

@XmlElement("view")
public class ViewInfo extends IName {

    @XmlElement("size")
    protected Size[] sizes;

    @XmlElement("gravity")
    protected Value[] gravityStr;

    @XmlAttribute("index")
    protected int index;

    @XmlElement("visible")
    protected String visible;

    @XmlAttribute("click")
    protected String clickName;

    @XmlAttribute("background")
    protected String background;

    @XmlElement("angle")
    protected Value[] angle;

    public Value[] getAngle() {
        return angle;
    }

    public ViewInfo() {
        super();
    }

    public String getClickName() {
        return clickName;
    }

    public String getVisible() {
        return visible;
    }

    public Size[] getSizes() {
        return sizes;
    }

    public Value[] getGravityStr() {
        return gravityStr;
    }

    public int getIndex() {
        return index;
    }

    public String getBackground() {
        return background;
    }

    /***
     * index大到小
     */
    public static final Comparator<ViewInfo> DESC = new Comparator<ViewInfo>() {
        @Override
        public int compare(ViewInfo lhs, ViewInfo rhs) {
            return rhs.getIndex() - lhs.getIndex();
        }
    };
    /***
     * index小到大
     */
    public static final Comparator<ViewInfo> ACS = new Comparator<ViewInfo>() {
        @Override
        public int compare(ViewInfo lhs, ViewInfo rhs) {
            return lhs.getIndex() - rhs.getIndex();
        }
    };
}
