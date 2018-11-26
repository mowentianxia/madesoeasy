package com.kk.imageeditor.bean;

import android.text.TextUtils;

import com.kk.common.utils.FileUtil;

import net.kk.xml.annotations.XmlAttribute;
import net.kk.xml.annotations.XmlElement;
import net.kk.xml.annotations.XmlIgnore;
import net.kk.xml.IXmlElement;

import java.io.File;

@XmlElement("styleinfo")
public class StyleInfo extends IXmlElement {
    @XmlElement("name")
    private String name;
    @XmlElement("desc")
    private String desc;
    @XmlElement("author")
    private String author;
    @XmlElement("version")
    private long version = 0;
    @XmlElement("app-version")
    private long appversion = -1;
    @XmlElement("icon")
    private String icon;
    @XmlElement("data")
    private String dataXml;
    @XmlElement("layout")
    private String layoutXml;
    @XmlElement("file")
    private String datafile;

    @XmlAttribute("folder")
    private boolean mFolder;

    @XmlIgnore
    private String stylePath;
    /**
     * 文件夹或者zip文件
     */
    @XmlIgnore
    private String dataPath;

    public StyleInfo() {
        author = "author name";
        name = "style name";
    }

    public String getDataXml() {
        return dataXml;
    }

    public String getLayoutXml() {
        return layoutXml;
    }

    public String getStylePath() {
        return stylePath;
    }

    public void setStylePath(String stylePath) {
        this.stylePath = stylePath;
        this.dataPath = getDataPath();
    }

    public boolean isFolder() {
        return mFolder;
    }

    public String getDataPath() {
        if (isFolder()) {
            return FileUtil.getParent(stylePath);
        }
        if (!FileUtil.isExtension(stylePath, ".xml")) {
            return stylePath;
        }
        if (TextUtils.isEmpty(datafile)) return "";
        if (FileUtil.exists(datafile)) {
            return datafile;
        } else {
            return new File(FileUtil.getParent(stylePath), datafile).getAbsolutePath();
        }
    }

    public long getAppversion() {
        return appversion;
    }

    public String getAuthor() {
        return author;
    }

    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public long getVersion() {
        return version;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "StyleInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                ", version=" + version +
                ", appversion=" + appversion +
                ", icon='" + icon + '\'' +
                ", dataXml='" + dataXml + '\'' +
                ", layoutXml='" + layoutXml + '\'' +
                ", stylePath='" + stylePath + '\'' +
                ", dataPath='" + dataPath + '\'' +
                '}';
    }
}