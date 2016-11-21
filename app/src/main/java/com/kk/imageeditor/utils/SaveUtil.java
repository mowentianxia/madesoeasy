package com.kk.imageeditor.utils;


import android.text.TextUtils;
import android.util.Log;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.SaveInfo;
import com.kk.imageeditor.draw.IDataProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.kk.imageeditor.Constants.SET_EX1;
import static com.kk.imageeditor.Constants.SET_EX2;
import static com.kk.imageeditor.Constants.ZIP_SET;

public class SaveUtil {

    @SuppressWarnings("unchecked")
    private static boolean loadSet1(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        if (Constants.DEBUG) {
            Log.d("msoe", "load set 1 " + file);
        }
        File f = FileUtil.file(file);
        if (f != null && f.exists()) {
            String dir = pIData.getTempPath("");
            ZipUtil.unzip(file, dir);// 解压
            String setF = new File(dir, ZIP_SET).getAbsolutePath();
            HashMap<String, String> values = (HashMap<String, String>) SerializationUtil
                    .loadObject(setF);
            if (values == null || values.size() == 0) {
                Log.w("kk", "load set fail " + file);
                return false;
            } else {
                if (Constants.DEBUG) {
                    Log.i("kk", "load set ok " + values.entrySet());
                }
                HashMap<String, String> values2 = new HashMap<>();
                for (Map.Entry<String, String> e : values.entrySet()) {
                    String k = e.getKey();
                    if (k.startsWith("#")) {
                        k = k.substring(1);
                    }
                    values2.put(k, e.getValue());
                }
                Log.i("msoe", "laod set1:" + values2);
                pIData.updateValues(values2);
            }

            return true;
        }
        return false;
    }

    private static boolean saveSet1(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        String dir = pIData.getTempPath("");
        File f = FileUtil.file(file);
        if (f == null) return false;
        FileUtil.createDirByFile(file);
        if (f.exists()) {
            f.delete();
        }
        String setFile = new File(dir, ZIP_SET).getAbsolutePath();
        HashMap<String, String> values = new HashMap<>();
        for (Map.Entry<String, String> e : pIData.getValues().entrySet()) {
            String k = e.getKey();
            if (!k.startsWith("#")) {
                k = "#" + k;
            }
            values.put(k, e.getValue());
        }
        SerializationUtil.saveObject(setFile, values);
        ArrayList<String> files = pIData.getOutFiles();
        files.add(setFile);
        ZipUtil.zip(file, files.toArray(new String[files.size()]));// 压缩
        return true;
    }

    private static boolean loadSet2(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        if (Constants.DEBUG) {
            Log.d("msoe", "load set 2 " + file);
        }
        File f = FileUtil.file(file);
        if (f != null && f.exists()) {
            String dir = pIData.getTempPath("");
            ZipUtil.unzip(file, dir);// 解压
            String setF = new File(dir, ZIP_SET).getAbsolutePath();
            SaveInfo saveInfo = null;
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(setF);
                saveInfo = XmlUtils.getSetUtils().getObject(SaveInfo.class, inputStream);
            } catch (Exception e) {
                if (Constants.DEBUG) {
                    Log.d("msoe", "load set 2",e);
                }
            } finally {
                FileUtil.close(inputStream);
            }
            if (saveInfo != null) {
                if (Constants.DEBUG) {
                    Log.d("msoe", "laod set2:" + saveInfo.values);
                }
                pIData.updateValues(saveInfo.values);
            }
            return true;
        }
        return false;
    }

    public static boolean loadSetCache(IDataProvider pIData) {
        String dir = pIData.getTempPath("");
        String setF = new File(dir, ZIP_SET).getAbsolutePath();
        if (!FileUtil.exists(setF)) return false;
        SaveInfo saveInfo = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(setF);
            saveInfo = XmlUtils.getSetUtils().getObject(SaveInfo.class, inputStream);
        } catch (Exception e) {
            if (Constants.DEBUG) {
                Log.e("kk", "loadSetCache", e);
            }
        } finally {
            FileUtil.close(inputStream);
        }
        if (saveInfo != null && saveInfo.values.size() > 0) {
            if (Constants.DEBUG) {
                Log.d("kk", "" + saveInfo);
            }
            pIData.updateValues(saveInfo.values);
            return true;
        }
        return false;
    }

    public static void clearTempSet(IDataProvider pIData) {
        String dir = pIData.getTempPath("");
        FileUtil.delete(dir);
    }

    public static boolean saveSetCache(IDataProvider pIData) {
        String dir = pIData.getTempPath("");
        SaveInfo saveInfo = new SaveInfo();
        saveInfo.style = pIData.getStyleInfo();
        saveInfo.values.putAll(pIData.getValues());
        String setFile = new File(dir, ZIP_SET).getAbsolutePath();
        FileUtil.createDirByFile(setFile);
        FileUtil.delete(setFile);
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(setFile);
            XmlUtils.getSetUtils().saveXml(saveInfo, outputStream);
        } catch (Exception e) {
            if (Constants.DEBUG) {
                Log.e("kk", "saveSetCache", e);
            }
        } finally {
            FileUtil.close(outputStream);
        }
        return true;
    }

    private static boolean saveSet2(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        String dir = pIData.getTempPath("");
        File f = FileUtil.file(file);
        if (f == null) return false;
        FileUtil.createDirByFile(file);
        if (f.exists()) {
            f.delete();
        }
        SaveInfo saveInfo = new SaveInfo();
        saveInfo.style = pIData.getStyleInfo();
        saveInfo.values.putAll(pIData.getValues());
        String setFile = new File(dir, ZIP_SET).getAbsolutePath();
        OutputStream outputStream = null;
        try {
            FileUtil.createDirByFile(setFile);
            outputStream = new FileOutputStream(setFile);
            XmlUtils.getSetUtils().saveXml(saveInfo, outputStream);
        } catch (Exception e) {
            if (Constants.DEBUG) {
                Log.e("kk", "saveSet2", e);
            }
        } finally {
            FileUtil.close(outputStream);
        }
        ArrayList<String> files = pIData.getOutFiles();
        files.add(setFile);
        ZipUtil.zip(file, files.toArray(new String[files.size()]));// 压缩
        return true;
    }

    public static boolean loadSet(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        String dir = pIData.getTempPath("");
        FileUtil.delete(dir);
        if (file.endsWith(SET_EX1)) {
            return loadSet1(pIData, file);
        } else {
            if (file.endsWith(SET_EX2)) {
                return loadSet2(pIData, file);
            }
        }
        return loadSet2(pIData, file + SET_EX2);
    }

    public static boolean saveSet(IDataProvider pIData, String file) {
        if (TextUtils.isEmpty(file))
            return false;
        if (file.endsWith(SET_EX1)) {
            return saveSet1(pIData, file);
        } else {
            if (file.endsWith(SET_EX2)) {
                return saveSet2(pIData, file);
            }
        }
        return saveSet2(pIData, file + SET_EX2);

    }
}