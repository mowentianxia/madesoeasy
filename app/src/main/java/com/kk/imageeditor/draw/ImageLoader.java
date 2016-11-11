package com.kk.imageeditor.draw;

import android.graphics.Bitmap;
import android.util.Log;


import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ImageLoader {

    public static Bitmap getBitmapFormFile(File file, int w, int h) {
        if (file == null || !file.exists()) return null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            inputStream = new FileInputStream(file);
            bitmap = BitmapUtil.getBitmapByStream(inputStream, w, h);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(inputStream);
        }
        return bitmap;
    }

    public static Bitmap getBitmapFormZip(String file, String name, int w, int h) {
        ZipFile zipFile = null;
        InputStream inputStream = null;
        Bitmap bitmap = null;
        try {
            zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry(name);
            if (entry != null) {
                inputStream = zipFile.getInputStream(entry);
                bitmap = BitmapUtil.getBitmapByStream(inputStream, w, h);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {

        } finally {
//            FileUtil.close(inputStream);
//            FileUtil.close(zipFile);
        }
        return bitmap;
    }
}