package net.kk.dialog;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;

public class FileFilter2 implements FileFilter {
    public static FileFilter2 DEBUFALT = new FileFilter2(false);
    private boolean onlyDir;
    private boolean ignoreCase;
    private boolean showHide;
    private String[] mEx;

    /***
     *
     * @param exs 文件后缀
     */
    public FileFilter2(boolean showHide, String... exs) {
        this(false, true, showHide, exs);
    }

    public FileFilter2(boolean onlyDir, boolean showHide) {
        this(onlyDir, true, showHide);
    }

    public FileFilter2(boolean onlyDir, boolean ignoreCase, boolean showHide, String... ex) {
        this.onlyDir = onlyDir;
        this.ignoreCase = ignoreCase;
        this.showHide = showHide;
        mEx = ex;
    }

    public void setOnlyDir(boolean onlyDir) {
        this.onlyDir = onlyDir;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    @Override
    public boolean accept(File pathname) {
        if (onlyDir && !pathname.isDirectory()) {
            return false;
        }
        String name = pathname.getName();
        if (!showHide) {
            if (name.startsWith(".")) {
                return false;
            }
        }
        if(pathname.isDirectory()){
            return true;
        }
        if (ignoreCase) {
            name = name.toLowerCase(Locale.US);
        }
        if (mEx != null && mEx.length > 0) {
            for (String ex : mEx) {
                if (name.endsWith(ex)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }
}
