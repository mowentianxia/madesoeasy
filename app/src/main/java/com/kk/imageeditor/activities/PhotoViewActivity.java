package com.kk.imageeditor.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.controllor.ControllorManager;
import com.kk.imageeditor.controllor.MyPreference;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.ShareUtil;
import com.kk.imageeditor.utils.VUiKit;

import net.kk.dialog.FileDialog;
import net.kk.dialog.FileFilter2;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseActivity {
    private MyPreference mMyPreference;
    PhotoView mPhotoView;
    private String mPath;
    String mName;
    Bitmap mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        enableBackHome();
        mMyPreference = MyPreference.get(this);
        mPhotoView = (PhotoView) findViewById(R.id.pv_main);
        if (loadItem(getIntent())) {
            loadImage();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_save_image:
                saveImage();
                break;
            case R.id.nav_share:
                ShareUtil.shareImage(this, getString(R.string.share_image), mPath, null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveImage() {
        String name = mName;
        String ex = mMyPreference.getImageType();
        if (ex != null) {
            if (!ex.startsWith(".")) {
                ex = "." + ex;
            }
            if (!name.endsWith(ex)) {
                name += ex;
            }
        }
        final FileDialog dialog = new FileDialog(this, FileDialog.Mode.SaveFile);
        dialog.setTitle(R.string.save_image);
        dialog.setInputText(name);
        dialog.setPath(new File(ControllorManager.get().getPathConrollor().getCurPath()),
                new FileFilter2(false, Constants.IMAGE_EX));
        dialog.setFileChooseListener((dlg, file) -> {
            if (file != null && !file.isDirectory()) {
                String filepath = file.getAbsolutePath();
                BitmapUtil.saveBitmap(mImage, filepath, 100);
            }
        });
        dialog.show();
    }

    private void loadImage() {
        VUiKit.defer().when(() -> {
            mImage = BitmapUtil.getBitmapFromFile(mPath, 0, 0);
            return mImage;
        }).done((v) -> {
            mPhotoView.setImageBitmap(v);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (loadItem(intent)) {
            loadImage();
        }
    }

    public static void showImage(Activity context, String bmp, String name) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(Constants.EXTRA_FILE, bmp);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean loadItem(Intent intent) {
        try {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                mName = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
            if (intent.hasExtra(Constants.EXTRA_FILE)) {
                mPath = intent.getStringExtra(Constants.EXTRA_FILE);
            }
        } catch (Exception e) {

        }
        return !TextUtils.isEmpty(mPath);
    }
}
