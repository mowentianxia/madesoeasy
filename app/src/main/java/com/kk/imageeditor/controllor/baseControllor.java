package com.kk.imageeditor.controllor;

import android.content.Context;
import android.content.SharedPreferences;

class BaseControllor {
    protected Context context;
    protected SharedPreferences sharedPreferences;

    BaseControllor(Context context, SharedPreferences sharedPreferences) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
    }
}
