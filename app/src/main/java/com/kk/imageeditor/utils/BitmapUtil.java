package com.kk.imageeditor.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@SuppressWarnings("deprecation")
public class BitmapUtil {
    private static final BitmapFactory.Options moptions;

    static {
        moptions = new BitmapFactory.Options();
        if (Build.VERSION.SDK_INT < 21) {
            moptions.inPurgeable = true;
            moptions.inInputShareable = true;
        }
    }

    public static void destroy(Drawable drawable) {
        if (drawable != null) {
            drawable.setCallback(null);
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && !bitmap.isRecycled())
                    bitmap.recycle();
                bitmap = null;
            }
            drawable = null;
        }
    }

    public static boolean saveBitmap(Bitmap bm, String file, int quality) {
        if (bm == null || file == null)
            return false;
        file = file.toLowerCase(Locale.US);
        File f = new File(file);
        File dir = f.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        } else {
            f.delete();
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(f);
            if (file.endsWith("png"))
                bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            else
                bm.compress(Bitmap.CompressFormat.PNG, quality, out);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(out);
        }
        return true;
    }

    public static boolean saveBitmap(Bitmap bm, String file, int quality, int newWidth, int newHeight) {
        if (bm == null)
            return false;
        int width = bm.getWidth();
        int height = bm.getHeight();
        if (newWidth == width && newHeight == height) {
            return saveBitmap(bm, file, quality);
        } else {

            float scaleWidth = ((float) newWidth) / width;
            float scaleHeight = ((float) newHeight) / height;

            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleHeight);

            Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width,
                    height, matrix, true);
            boolean b = saveBitmap(resizedBitmap, file, quality);
            if (!resizedBitmap.isRecycled())
                resizedBitmap.recycle();
            return b;
        }
    }

    public static Bitmap getBitmap(String path) {
        InputStream inputStream = null;
        Bitmap bmp = null;
        try {
            inputStream = new FileInputStream(path);
            bmp = BitmapUtil.getBitmapByStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(inputStream);
        }
        return bmp;
    }

    public static Bitmap getBitmapByStream(InputStream drawinput) {
        Bitmap b = null;
        try {
            if (moptions != null) {
                b = BitmapFactory.decodeStream(drawinput, null, moptions);
            } else {
                b = BitmapFactory.decodeStream(drawinput);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }

    public static Drawable getDrawableByStream(Context context, InputStream drawinput) {
        if (drawinput == null)
            return null;
        Resources res = context.getResources();
        Bitmap b = null;
        BitmapDrawable bd = null;
        try {
            b = getBitmapByStream(drawinput);
            bd = new BitmapDrawable(res, b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            FileUtil.close(drawinput);
        }
        return bd;
    }

    public static Bitmap getBitmap(View view) {
        if (view == null) return null;
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        view.buildDrawingCache(false);
        return view.getDrawingCache();
    }

    /***
     * @param isAutoScale 是否根据当前缩放
     * @param w           0的时候不缩放
     * @param h           0的时候不缩放
     * @return
     */
    public static Bitmap getBitmap(View view, boolean isAutoScale,
                                   int rWidth, int rHeight, int w, int h) {
        view.setDrawingCacheEnabled(true);
        view.destroyDrawingCache();
        if (w > 0 && h > 0)
            onchildLayout(view, w, h);
        else if (rWidth > 0 && rHeight > 0) {
            onchildLayout(view, rWidth, rHeight);
        }
        view.buildDrawingCache(false);
        return view.getDrawingCache(isAutoScale);
    }

    static void onchildLayout(View view, int w, int h) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(w,
                View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(h,
                View.MeasureSpec.EXACTLY);

        view.measure(widthSpec, heightSpec);
        view.layout(0, 0, w, h);
    }
}
