package com.kk.imageeditor.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.kk.imageeditor.R;

import java.io.File;
import java.util.List;

public class ShareUtil {
    public static boolean shareImage(Context context, String title, String imgPath, String pkgName) {
        boolean ok = false;
        Intent intent = new Intent(Intent.ACTION_SEND);
        File f = new File(imgPath);
        if (f != null && f.exists() && f.isFile()) {
            intent.setType("image/jpg");
            Uri u = UriCompat.fromFile(context, f);
            intent.putExtra(Intent.EXTRA_STREAM, u);
        }
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (!TextUtils.isEmpty(pkgName)) {
            List<ResolveInfo> matches = context.getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo info : matches) {
                if (info.activityInfo.packageName.toLowerCase().startsWith(pkgName)) {
                    intent.setPackage(info.activityInfo.packageName);
                    try {
                        context.startActivity(intent);
                        ok = true;
                    } catch (Exception e) {
                    }
                    break;
                }
            }
            if (!ok) {
                Toast.makeText(context, R.string.no_share_app, Toast.LENGTH_SHORT).show();
            }
            return ok;
        }
        intent.setPackage(null);
        try {
            context.startActivity(Intent.createChooser(intent, title));
            ok = true;
        } catch (Exception e) {
        }
        return ok;
    }

}
