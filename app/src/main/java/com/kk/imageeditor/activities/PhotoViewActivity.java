package com.kk.imageeditor.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.kk.imageeditor.R;
import com.kk.imageeditor.controllor.ControllorManager;
import com.kk.imageeditor.filebrowser.DialogFileFilter;
import com.kk.imageeditor.filebrowser.SaveFileDialog;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.ShareUtil;
import com.kk.imageeditor.utils.VUiKit;

import java.io.File;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseActivity {

    final static String[] IMAGE_EX = new String[]{".jpg", ".png"};
    private static final String EXTRA_FILE = "filepath";
    PhotoView mPhotoView;
    private String mPath;
    String mName;
    Bitmap mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
//        setActionBarTitle(R.string.);
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
        final SaveFileDialog fileDialog = new SaveFileDialog(this);
        fileDialog.setCurPath(ControllorManager.get().getPathConrollor().getCurPath());
        fileDialog.setHideCreateButton(true);
        fileDialog.setEditText(mName);
        fileDialog.setDialogFileFilter(new DialogFileFilter(false, false, IMAGE_EX));
        fileDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.ok),
                (dialog, which) -> {
                    File file = fileDialog.getSelectFile();
                    if (file != null && !file.isDirectory()) {
                        String filepath = file.getAbsolutePath();
                        if (!filepath.endsWith(".png")) {
                            filepath += ".png";
                        }
                        BitmapUtil.saveBitmap(mImage, filepath, 100);
                    }
                });
        fileDialog.setButton(DialogInterface.BUTTON_POSITIVE,
                getString(android.R.string.cancel), (DialogInterface.OnClickListener) null);
        fileDialog.show();
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

    public static void showImage(Context context, String bmp, String name) {
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(EXTRA_FILE, bmp);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean loadItem(Intent intent) {
        try {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                mName = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
            if (intent.hasExtra(EXTRA_FILE)) {
                mPath = intent.getStringExtra(EXTRA_FILE);
            }
        } catch (Exception e) {

        }
        return !TextUtils.isEmpty(mPath);
    }
}
