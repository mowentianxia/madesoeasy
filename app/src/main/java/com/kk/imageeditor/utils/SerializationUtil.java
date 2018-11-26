package com.kk.imageeditor.utils;

import android.text.TextUtils;

import com.kk.common.utils.FileUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

/**
 *
 * @date 2015年2月11日 下午3:24:39
 *
 * @author 柯永裕
 *
 */
public class SerializationUtil {

    /**
     * 对象转字符串
     *
     * @param object
     *            对象
     * @return 对象序列化后的字符串
     */
    public static String serialize(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        String serStr = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            serStr = byteArrayOutputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(objectOutputStream);
            FileUtil.close(byteArrayOutputStream);
        }
        return serStr;
    }

    /**
     * 字符串转对象
     *
     * @param str
     *            对象序列化后的字符串
     * @return
     */
    public static Object deSerialization(String str) {
        String redStr = null;
        ByteArrayInputStream byteArrayInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object data = null;
        try {
            redStr = java.net.URLDecoder.decode(str, "UTF-8");
            byteArrayInputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            data = objectInputStream.readObject();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(objectInputStream);
            FileUtil.close(byteArrayInputStream);
        }
        return data;
    }

    /**
     * 保存对象
     *
     * @return 是否保存成功
     */
    public static boolean saveObject(String file, Object obj) {
        boolean isOK = false;
        FileOutputStream out = null;
        ObjectOutputStream oos = null;
        try {
            out = new FileOutputStream(file);
            oos = new ObjectOutputStream(out);
            oos.writeObject(obj);
            oos.flush();
            isOK = true;
        } catch (IOException e) {
            e.printStackTrace();
            isOK = false;
        } finally {
            FileUtil.close(oos);
            FileUtil.close(out);
        }
        return isOK;
    }

    /**
     * 读取对象
     *
     * @param file
     *            文件
     * @return
     */
    public static Object loadObject(String file) {
        Object o = null;
        if (TextUtils.isEmpty(file))
            return o;
        FileInputStream in = null;
        ObjectInputStream ois = null;
        File f = new File(file);
        if (!f.exists())
            return o;
        try {
            in = new FileInputStream(f);
            ois = new ObjectInputStream(in);
            o = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(ois);
            FileUtil.close(in);
        }
        return o;
    }
}
