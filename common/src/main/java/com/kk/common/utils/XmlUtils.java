package com.kk.common.utils;


import android.util.Log;

import net.kk.xml.XmlOptions;
import net.kk.xml.XmlReader;
import net.kk.xml.XmlWriter;

import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class XmlUtils {
    public static boolean DEBUG = false;
    private static final XmlOptions OPTIONS = new XmlOptions.Builder().useSpace().dontUseSetMethod()
            .enableSameAsList()
//            .registerConstructorAdapter(Location.class, new XmlConstructorAdapter() {
//                @Override
//                public <T> T create(Class<T> aClass, Object o) {
//                    return (T) new Location(LocationManager.GPS_PROVIDER);
//                }
//            })
            .build();
    XmlOptions options;


    public static XmlUtils getStyleUtils() {
        return new XmlUtils(OPTIONS);
    }

    public static XmlUtils getSetUtils() {
        return getStyleUtils();
    }

    private XmlUtils(XmlOptions options) {
        this.options = options;
    }

    public void saveXml(Object object, OutputStream outputStream) throws Exception {
        XmlWriter writer = new XmlWriter(XmlPullParserFactory.newInstance().newSerializer(), options);
        writer.write(object, outputStream, null);
    }

    public String toXml(Object object) throws Exception {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        saveXml(object, arrayOutputStream);
        return new String(arrayOutputStream.toByteArray());
    }

    public <T> T getObject(Class<T> tClass, InputStream inputStream) throws Exception {
        XmlReader reader = new XmlReader(XmlPullParserFactory.newInstance().newPullParser(), options);
        return reader.fromInputStream(tClass, inputStream, null);
    }

    public <T> T getObject(Class<T> tClass, File f) throws Exception {
        XmlReader reader = new XmlReader(XmlPullParserFactory.newInstance().newPullParser(), options);
        FileInputStream inputStream = new FileInputStream(f);
        T t = reader.fromInputStream(tClass, inputStream, null);
        inputStream.close();
        return t;

    }

    /**
     * @param xmlFile   xml或者是zip文件
     * @param entryName zip里面的名字
     */
    public <T> T parseXml(Class<T> pClass, File xmlFile, String entryName) {
        T styleInfo = null;
        InputStream inputStream = null;
        ZipFile zipFile = null;
        try {
            if (FileUtil.isExtension(xmlFile.getAbsolutePath(), ".xml")) {
                inputStream = new FileInputStream(xmlFile);
            } else {
                File dir = xmlFile.getParentFile();
                File cache = new File(dir, entryName);
                if (cache.exists()) {
                    inputStream = new FileInputStream(cache);
                } else {
                    zipFile = new ZipFile(xmlFile);
                    ZipEntry zipEntry = zipFile.getEntry(entryName);
                    if (zipEntry != null) {
                        inputStream = zipFile.getInputStream(zipEntry);
                    } else {
                        //第一个xml
                        Enumeration<? extends ZipEntry> es = zipFile.entries();
                        while (es.hasMoreElements()) {
                            zipEntry = es.nextElement();
                            if (zipEntry.getName().endsWith(".xml")) {
                                inputStream = zipFile.getInputStream(zipEntry);
                                break;
                            }
                        }
                    }
                }
            }
            if (inputStream != null) {
                styleInfo = XmlUtils.getStyleUtils().getObject(pClass, inputStream);
            }
        } catch (Exception e) {
            if (DEBUG)
                Log.e("msoe", "parseXml", e);
        } finally {
            FileUtil.close(inputStream);
            FileUtil.closeZip(zipFile);
        }
        return styleInfo;
    }

    public <T> T getObject(Class<T> tClass, String xml) throws Exception {
        if (xml == null) {
            return null;
        }
        return getObject(tClass, new ByteArrayInputStream(xml.getBytes()));
    }
}
