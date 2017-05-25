package com.kk.imageeditor;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;
import android.widget.ImageView;

import com.kk.imageeditor.controllor.ControllorManager;

import net.kk.dialog.FileDialog;
import net.kk.dialog.IImagerDisplay;

import java.io.File;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        ControllorManager.get().attach(this);
        FileDialog.setImagerDisplayDefault(new IImagerDisplay() {
            @Override
            public void bind(File file, ImageView imageView) {
                if(file != null ){
                    if(file.isDirectory()){
                        imageView.setImageResource(R.drawable.ic_folder_color);
                    }else{
                        imageView.setImageResource(R.drawable.ic_file_color);
                    }
                }
            }
        });
    }
}
