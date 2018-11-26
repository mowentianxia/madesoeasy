package com.kk.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.widget.Toast;

import org.jdeferred.android.AndroidDeferredManager;

/**
 * @author Lody
 * <p>
 * A set of tools for UI.
 */
public class VDeferredManager {
    private static final AndroidDeferredManager gDM = new AndroidDeferredManager();
    private static final Handler gUiHandler = new Handler(Looper.getMainLooper());

    private static Context sContext;

    public static void init(Context context) {
        sContext = context;
    }

    @Deprecated
    public static AndroidDeferredManager defer(){
        return task();
    }

    public static AndroidDeferredManager task() {
        return gDM;
    }

    public static int dpToPx(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                sContext.getResources().getDisplayMetrics());
    }

    public static void post(Runnable r) {
        gUiHandler.post(r);
    }

    public static void postDelayed(long delay, Runnable r) {
        gUiHandler.postDelayed(r, delay);
    }

    public static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static Toast toast;

    public static void showToastMessage(int msg) {
        showToastMessage(sContext.getString(msg));
    }

    @SuppressLint("ShowToast")
    public static void showToastMessage(String msg) {
        try {
            if (toast == null) {
                toast = Toast.makeText(sContext, msg, Toast.LENGTH_SHORT);
            } else {
                toast.setText(msg);
            }
            toast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
