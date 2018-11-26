package net.kk.dialog;

import android.widget.ImageView;

import com.kk.imageeditor.R;

import java.io.File;

class DefaultImagerDisplay implements IImagerDisplay {
    private boolean isImage(String name) {
        return name.endsWith(".jpg") || name.endsWith(".gif") || name.endsWith(".bmp") || name.endsWith(".png");
    }

    @Override
    public void bind(File file, ImageView imageView) {
        if (file.isDirectory()) {
            imageView.setImageResource(R.drawable.ic_directory);
        } else {
            // if (isImage(file.getName().toLowerCase(Locale.US)))
            imageView.setImageResource(R.drawable.ic_file);
        }
    }
}
