package com.kk.imageeditor.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.kk.imageeditor.Constants;

import java.io.File;

public class UriCompat {

    public static Uri fromFile(Context context, File file){
        if(Build.VERSION.SDK_INT>=24 && context.getApplicationInfo().targetSdkVersion>=24){
            return FileProvider.getUriForFile(context, Constants.FILE_AUTH, file);
        }
        return Uri.fromFile(file);
    }
}
