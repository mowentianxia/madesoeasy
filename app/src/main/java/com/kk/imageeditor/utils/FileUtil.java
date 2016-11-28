package com.kk.imageeditor.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
    private static final Pattern NAME_NUMBER = Pattern.compile("\\(([0-9]+)\\)\\.");

    public static File file(String path, boolean autorename) {
        File f = null;
        try {
            f = new File(path);
            if (autorename) {
                if (f.exists()) {
                    Matcher matcher = NAME_NUMBER.matcher(path);
                    if (matcher.find()) {
                        int num = Integer.parseInt(matcher.group(1));
                        f = new File(path.replace("(" + num + ").", "(" + (num + 1) + ")."));
                    } else {
                        String ex = getFileType(f.getName());
                        if (ex == null) ex = "";
                        if (ex.startsWith(".")) {
                            ex = getFileNameNoType(path) + "(1)" + ex;
                        } else {
                            ex = getFileNameNoType(path) + "(1)." + ex;
                        }
                        f = new File(f.getParent(), ex);
                    }
                }
            }
        } catch (Exception e) {

        }
        return f;
    }

    public static boolean isExtension(String path, String ex) {
        if (ex == null || TextUtils.isEmpty(path)) {
            return false;
        }
        ex = ex.toLowerCase(Locale.US);
        if (!ex.startsWith(".")) {
            ex = "." + ex;
        }
        File file = new File(path);
        return file.getName().toLowerCase(Locale.US).endsWith(ex);
    }

    public static File file(String path) {
        return file(path, false);
    }

    public static String getParent(String file) {
        if (TextUtils.isEmpty(file)) return "/";
        File dir = new File(file).getParentFile();
        return dir.getAbsolutePath();
    }

    public static File file(File dir, String name) {
        File f = null;
        try {
            f = new File(dir, name);
        } catch (Exception e) {

        }
        return f;
    }

    public static File file(String dir, String name) {
        File f = null;
        try {
            f = new File(dir, name);
        } catch (Exception e) {

        }
        return f;
    }

    public static String getFileType(String url) {
        if (TextUtils.isEmpty(url)) return "";
        String name = getFileName(url);
        int i = name.lastIndexOf(".");
        return i >= 0 ? name.substring(i) : "";
    }

    public static String getFileNameNoType(String file) {
        if (TextUtils.isEmpty(file)) return "";
        String name = getFileName(file);
        int i = name.lastIndexOf(".");
        return i >= 0 ? name.substring(0, i) : "";
    }

    public static String getFileName(String file) {
        if (TextUtils.isEmpty(file)) return "";
        if (file.startsWith("http")) {
            int i = file.indexOf("?");
            if (i > 0) {
                file = file.substring(0, i);
            }
            i = file.lastIndexOf("/");
            if (i > 0) {
                return file.substring(i);
            }
            return file.replace("/", "");
        }
        File localFile = new File(file);
        return localFile.getName();
    }

    /***
     * 文件所在的目录
     */
    public static boolean createDirByFile(String file) {
        if (TextUtils.isEmpty(file))
            return false;
        File outFile = new File(file);
        if (!outFile.exists()) {
            File dir = outFile.getParentFile();
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }
        return true;
    }
//
//    public static String getDirByFile(String file) {
//        if (file == null)
//            return "";
//        File localFile = new File(file);
//        if (localFile != null) {
//            return localFile.getParent();
//        }
//        int i = file.lastIndexOf("/");
//        return (i > 0) ? file.substring(0, i + 1) : "/";
//    }

    public static String getStoragePath() {
        String str = "/storage/emulated/legacy/";
        try {
            str = Environment.getExternalStorageDirectory().getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String getSDCardPath(String file) {
        if (TextUtils.isEmpty(file)) return "";
        return new File(getStoragePath(), file).getAbsolutePath();
    }

    public static boolean exists(String path) {
        if (TextUtils.isEmpty(path)) return false;
        File file = file(path);
        return file != null && file.exists();
    }

    public static boolean copyFile(String file, String toFile) {
        if (TextUtils.isEmpty(file) || TextUtils.isEmpty(toFile)) return false;
        boolean isOK = false;
        if (createDirByFile(toFile))
            return isOK;
        InputStream inputstream = null;
        OutputStream outputStream = null;
        try {
            inputstream = new FileInputStream(file);
            outputStream = new FileOutputStream(toFile);
            copy(inputstream, outputStream);
            isOK = true;
        } catch (IOException e) {
            e.printStackTrace();
            isOK = false;
        } finally {
            close(outputStream);
            close(inputstream);
        }
        return isOK;
    }

    @SuppressWarnings("deprecation")
    public static long getSDFreeSize() {
        File path = Environment.getExternalStorageDirectory(); // 取得SD卡文件路径
        StatFs stat = new StatFs(path.getPath());
        if (Build.VERSION.SDK_INT < 19) {
            long blockSize = stat.getBlockSize();
            long availableBlocks = stat.getAvailableBlocks();
            return blockSize * availableBlocks;
        } else {
            long blockSize = stat.getBlockSizeLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            return blockSize * availableBlocks;
        }
    }

    public static boolean copyFromZip(ZipFile zipFile, String zipEntryName, String destName) {
        if (zipFile == null
                || TextUtils.isEmpty(zipEntryName) || TextUtils.isEmpty(destName)) return false;
        //创建文件夹
        boolean isOK = false;
        InputStream is = null;
        OutputStream outputStream = null;
        try {
            FileUtil.createDirByFile(destName);
            ZipEntry ZEpng = zipFile.getEntry(zipEntryName);
            if (ZEpng != null) {
                is = zipFile.getInputStream(ZEpng);
                outputStream = new FileOutputStream(destName);
                copy(is, outputStream);
                isOK = true;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(outputStream);
            close(is);
        }
        return isOK;
    }

    /***
     * 从zip复制文件
     *
     * @param zipEntryName
     * @param destName
     * @return
     */
    public static boolean copyFromZip(String zipFile, String zipEntryName, String destName) {
        if (TextUtils.isEmpty(zipFile)
                || TextUtils.isEmpty(zipEntryName) || TextUtils.isEmpty(destName)) return false;
        //创建文件夹
        boolean isOK = false;
        ZipFile zf = null;
        InputStream is = null;
        OutputStream outputStream = null;
        try {
            if (!FileUtil.exists(zipFile))
                return false;
            FileUtil.createDirByFile(destName);
            zf = new ZipFile(zipFile);
            ZipEntry ZEpng = zf.getEntry(zipEntryName);
            if (ZEpng != null) {
                is = zf.getInputStream(ZEpng);
                outputStream = new FileOutputStream(destName);
                copy(is, outputStream);
                isOK = true;
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(outputStream);
            close(is);
            closeZip(zf);
        }
        return isOK;
    }


    public static boolean rename(String form, String to) {
        if (TextUtils.isEmpty(form) || TextUtils.isEmpty(to)) return false;
        if (form != null && form.equals(to)) return true;
        File formFile = new File(form);
        File toFile = new File(to);
        if (formFile.exists()) {
            toFile.delete();
            return formFile.renameTo(toFile);
        }
        return false;
    }

    public static void delete(String file) {
        if (TextUtils.isEmpty(file)) return;
        File f = new File(file);
        delete(f);
    }

    public static void delete(File file) {
        if (file == null || !file.exists()) return;
        if (file.isFile()) {
            file.delete();
        } else {
            File[] files = file.listFiles();
            if (files == null)
                return;
            for (File f : files) {
                delete(f);
            }
            file.delete();
        }
    }

    public static void closeZip(ZipFile closeable) {
        if (closeable == null) return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable e) {
            }
        }
    }

    public static byte[] readFile(String file) {
        ByteArrayOutputStream outputStream = null;
        InputStream inputStream = null;
        byte[] result = null;
        if (exists(file)) return null;
        try {
            inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            copy(inputStream, outputStream);
            result = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
            close(outputStream);
        }
        return result;
    }

    public static boolean writeFile(String file, byte[] data) {
        OutputStream outputStream = null;
        InputStream inputStream = null;
        delete(file);
        try {
            inputStream = new ByteArrayInputStream(data);
            outputStream = new FileOutputStream(file);
            copy(inputStream, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(inputStream);
            close(outputStream);
        }
        return exists(file);
    }

    public static File[] listFiles(String dir, final String... filters) {
        if (TextUtils.isEmpty(dir)) return null;
        File[] files = new File(dir).listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                if (new File(dir, filename).isDirectory()) return false;
                if (filters == null || filters.length == 0) {
                    return true;
                }
                for (String f : filters) {
                    if (filename.endsWith(f))
                        return true;
                }
                return false;
            }
        });
        return files;
    }

    public static String getName(String path) {
        return new File(path).getName();
    }

    public static String join(String path1, String path2) {
        if (TextUtils.isEmpty(path1)) {
            return path2;
        }
        if (TextUtils.isEmpty(path2)) {
            return path1;
        }
        if (!path1.endsWith("/")) {
            path1 += "/";
        }
        if (path2.startsWith("/")) {
            path2 = path2.substring(1);
        }
        return path1 + path2;
    }

    public static boolean isDirectory(Context context, String assets) {
        String[] files = new String[0];
        try {
            files = context.getAssets().list(assets);
        } catch (IOException e) {

        }
        if (files != null && files.length > 0) {
            return true;
        }
        return false;
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] cache = new byte[1024 * 8];
        int len;
        while ((len = in.read(cache)) != -1) {
            out.write(cache, 0, len);
        }
    }

    public static void copyToFile(InputStream in, String file) {
        FileOutputStream outputStream = null;
        try {
            File dir = new File(file).getParentFile();
            if (dir != null && !dir.exists()) {
                dir.mkdirs();
            }
            outputStream = new FileOutputStream(file);
            copy(in, outputStream);
        } catch (Exception e) {

        } finally {
            close(outputStream);
            close(in);
        }
    }

    public static int copyFilesFromAssets(Context context, String assets, String toPath, boolean update) throws IOException {
        AssetManager am = context.getAssets();
        String[] files = am.list(assets);
        if (files == null) {
            return 0;
        }
        if (files.length == 0) {
            //is file
            String file = getName(assets);
            String tofile = join(toPath, file);
            if (update || !new File(tofile).exists()) {
                copyToFile(am.open(assets), tofile);
            }
            return 1;
        } else {
            int count = 0;
            for (String file : files) {
                String path = join(assets, file);
                if (isDirectory(context, path)) {
                    count += copyFilesFromAssets(context, path, join(toPath, file), update);
                } else {
                    File f = new File(join(toPath, file));
                    if (update || !f.exists()) {
                        copyToFile(am.open(path), f.getAbsolutePath());
                    }
                    count++;
                }
            }
            return count;
        }
    }

    public static boolean createNoMedia(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        if (file.isDirectory()) {
            //
            File n = new File(file, ".nomedia");
            if (n.exists()) {
                return true;
            }
            try {
                n.createNewFile();
                return true;
            } catch (IOException e) {
            }
        }
        return false;
    }
}
