package com.kk.imageeditor.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kk.imageeditor.R;

import uk.co.senab.photoview.PhotoView;

public class PhotoViewActivity extends BaseActivity {
    PhotoView mPhotoView;
    Bitmap mImage;
    String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mPhotoView = (PhotoView) findViewById(R.id.pv_main);
        if (loadItem(getIntent())) {
            mPhotoView.setImageBitmap(mImage);
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
                break;
            case R.id.nav_share:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (loadItem(intent)) {
            mPhotoView.setImageBitmap(mImage);
        }
    }

    public static void showImage(Context context, Bitmap bmp, String name) {
        if (bmp == null || bmp.isRecycled()) {
            Toast.makeText(context, R.string.image_is_null, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(context, PhotoViewActivity.class);
        intent.putExtra(Intent.EXTRA_STREAM, bmp);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    private boolean loadItem(Intent intent) {
        try {
            if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                mName = intent.getStringExtra(Intent.EXTRA_TEXT);
            }
            mImage = intent.getParcelableExtra(Intent.EXTRA_STREAM);
        } catch (Exception e) {

        }
        if (mImage != null) {
            return true;
        }
        return false;
    }
}
