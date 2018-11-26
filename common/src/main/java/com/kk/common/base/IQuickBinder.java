package com.kk.common.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kk.common.ui.QuickOnClickListener;

public interface IQuickBinder {
    Context getContext();
    Activity getActivity();
    BaseActivity getBaseActivity();
    <T extends View> T bind(int id);
    <T extends View> T bindClick(int id, QuickOnClickListener clickListener);
    <T extends View> T bindClick(int id, View.OnClickListener clickListener);
    <T extends CheckBox> T bindCheck(int id, CompoundButton.OnCheckedChangeListener clickListener);
}
