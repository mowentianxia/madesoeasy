package com.kk.imageeditor.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kk.imageeditor.BuildConfig;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.ImageData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.SubItem;
import com.kk.imageeditor.bean.data.TextData;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.enums.DataType;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.draw.IDataProvider;
import com.kk.imageeditor.utils.SaveUtil;
import com.kk.imageeditor.utils.StringUtil;
import com.kk.imageeditor.view.IKView;
import com.kk.imageeditor.widgets.ComboAdapter;
import com.kk.imageeditor.widgets.ISelectImage;
import com.kk.imageeditor.widgets.ISelectImageListener;

import java.io.File;
import java.util.List;

public abstract class BaseEditActivity extends BaseActivity implements ISelectImageListener {
    protected int itemHeight;
    protected float mScale;

    protected abstract void updateViews();

    protected abstract ISelectImage getSelectImage();

    protected IDataProvider getDefaultData() {
        return mDefaultData;
    }

    public void updateData() {
        mDefaultData.updateDatas(false);
    }

    protected SelectElement tmpSelectElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        itemHeight = Math.round(Math.max(dm.widthPixels, dm.heightPixels) / getDefaultHeight(dm));
    }

    protected float getDefaultHeight(DisplayMetrics dm) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15.0f, dm);
    }

    protected void onEditCompleted(String... values) {
        mDefaultData.onChanged(tmpSelectElement, values);
        updateViews();
    }

    protected StyleInfo getStyleInfo() {
        return mDefaultData.getStyleInfo();
    }

    protected boolean saveCache() {
        if (!mDefaultData.isLoad()) return false;
        return SaveUtil.saveSetCache(mDefaultData);
    }

    protected void clearCache() {
        SaveUtil.clearTempSet(mDefaultData);
    }

    protected boolean loadCache() {
        if (!mDefaultData.isLoad()) return false;
        return SaveUtil.loadSetCache(mDefaultData);
    }

    protected String getSaveFileName() {
        return mDefaultData.getSaveFileName();
    }

    protected String getDataValue(String name) {
        return mDefaultData.getValue(name);
    }

    protected boolean onStartEdit(IDataProvider pIData, ViewData data, SelectElement pSelectElement) {
        if (pSelectElement == null) {
            return false;
        }
        tmpSelectElement = pSelectElement;
        if (BuildConfig.DEBUG) {
            Log.i("kk", "name=" + pSelectElement.getName());
        }
        switch (pSelectElement.getType()) {
            case image:
                if (data instanceof ImageData) {
                    ImageData imageData = (ImageData) data;
                    String name = imageData.file_src;
                    if (TextUtils.isEmpty(name)) {
                        name = pIData.dealString(pSelectElement.getDefault());
                    }
                    String file = pIData.getTempPath(name);
                    int width = Math.round(Math.max(data.width, data.width * mScale));
                    int height = Math.round(Math.max(data.height, data.height * mScale));
                    getSelectImage().startPhotoCut(file, width, height, true);
                }
                break;
            case select:
                showComboox(pIData, pSelectElement);
                break;
            case multi_select:
                showMultiCombobox(pIData, pSelectElement);
                break;
            case text:
                if (data instanceof TextData) {
                    TextData textData = (TextData) data;
                    String text = textData.text;
                    if (TextUtils.isEmpty(text)) {
                        text = pSelectElement.getDefault();
                    }
                    inputText(pSelectElement.getDesc(), text, textData.singleline);
                } else {
                    String text = pIData.getValue(pSelectElement.getName());
                    inputText(pSelectElement.getDesc(), text, true);
                }
                break;
        }
        return false;
    }

    protected boolean onSave(IDataProvider pIData, File file) {
        return SaveUtil.saveSet(pIData, file.getAbsolutePath());
    }

    protected boolean onLoad(IDataProvider pIData, File file) {
        return SaveUtil.loadSet(pIData, file.getAbsolutePath());
    }

    @Override
    public void onSelectImage(String path) {

    }

    @Override
    public void onCurImageCompleted(String path) {
        onEditCompleted(path);
    }

    protected ViewGroup.LayoutParams getItemLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        return lp;
    }

    private Spinner createSpinner(String value, List<SubItem> items) {
        final Spinner sp = new Spinner(this);
        final ComboAdapter adapter = new ComboAdapter(this, items);
        sp.setLayoutParams(getItemLayoutParams());
        sp.setAdapter(adapter);
        int index = adapter.getIndex(value);
        if (index >= 0 && index < sp.getCount())
            sp.setSelection(index);
        return sp;
    }

    private String getSpinnerValue(Spinner pSpinner) {
        int pos = pSpinner.getSelectedItemPosition();
        if (pos >= 0) {
            SpinnerAdapter adapter = pSpinner.getAdapter();
            if (adapter instanceof ComboAdapter) {
                return ((ComboAdapter) adapter).getItem(pos).getValue();
            }
        }
        return "";
    }

    private TextView createSpinnerTitle(String desc) {
        TextView textView = new TextView(this);
        textView.setText(desc);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        textView.setLayoutParams(lp);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        return textView;
    }

    protected void showComboox(IDataProvider pIData, SelectElement pSelectElement) {
        if (pSelectElement == null)
            return;
        String value = pIData.getValue(pSelectElement.getName());
        final Spinner sp = createSpinner(value, pSelectElement.getItems());
        showDialog(pSelectElement.getDesc(), (v, w) -> {
            onEditCompleted(getSpinnerValue(sp));
        }, null, null, sp);
    }

    protected void showMultiCombobox(IDataProvider pIData, SelectElement pSelectElement) {
        if (pSelectElement == null)
            return;
        List<SubItem> items = pSelectElement.getItems();
        final int count = items.size();
//        final Spinner[] spers = new Spinner[items.size()];
        final View[] views = new View[items.size() * 2];
        for (int i = 0; i < count; i++) {
            SubItem item = items.get(i);
            String value = getDataValue(item.getName());
            SelectElement selectElement = pIData.getSelectElement(item.getValue());
            if (selectElement == null) {
                if (Drawer.DEBUG) {
                    Log.w("kk", "no find sub " + item.getValue());
                }
                continue;
            }
            View view;
            if (selectElement.getType() == DataType.select) {
                view = createSpinner(value, selectElement.getItems());
            } else {
                view = createInputText(value, true);
            }
//            spers[i] = createSpinner(value, selectElement.getItems());
            views[i * 2] = createSpinnerTitle(selectElement.getDesc());
            views[i * 2 + 1] = view;// spers[i];
        }
        showDialog(pSelectElement.getDesc(), (v, w) -> {
            String[] values = new String[count];
            View view;
            for (int i = 0; i < values.length; i++) {
                view = views[i * 2 + 1];
                if (view == null) continue;
                if (view instanceof Spinner) {
                    values[i] = getSpinnerValue((Spinner) view);
                } else if (view instanceof EditText) {
                    values[i] = StringUtil.toString(((EditText) view).getText());
                }
            }
            onEditCompleted(values);
        }, null, null, views);
    }

    @SuppressWarnings("deprecation")
    private EditText createInputText(String defalutValue, boolean singleLine) {
        final EditText editText = new EditText(this);
        editText.setGravity(Gravity.TOP);
        editText.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editText.setSingleLine(false);
        // 水平滚动设置为False
        editText.setHorizontallyScrolling(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            editText.setBackground(null);
        } else {
            editText.setBackgroundDrawable(null);
        }
        if (!singleLine)
            editText.setMinLines(6);
        else {
            editText.setSingleLine();
            editText.setLayoutParams(getItemLayoutParams());
        }
        editText.setText(defalutValue);
        return editText;
    }

    protected void inputText(String desc, String defalutValue, boolean singleLine) {
        final EditText editText = createInputText(defalutValue, singleLine);
        showDialog(desc, (v, w) -> {
            onEditCompleted("" + editText.getText());
        }, null, null, editText);
    }

    private void showDialog(String title,
                            final DialogInterface.OnClickListener ok,
                            final DialogInterface.OnClickListener cancel,
                            final DialogInterface.OnCancelListener oncancel,
                            View... views) {
        final LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (View view : views) {
            if (view != null)
                layout.addView(view);
        }
        showDialog(title, null, layout, 0, ok, cancel, oncancel);
    }

    public void showDialog(String title, String message,
                           final View view,
                           int dialogTheme,
                           final DialogInterface.OnClickListener ok,
                           final DialogInterface.OnClickListener cancel,
                           final DialogInterface.OnCancelListener oncancel) {
        AlertDialog.Builder builder;
        if (dialogTheme != 0) {
            builder = new AlertDialog.Builder(this, dialogTheme);
        } else {
            builder = new AlertDialog.Builder(this);
        }
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(message);
        if (view != null)
            builder.setView(view);
        builder.setNegativeButton(android.R.string.ok, ok);
        builder.setPositiveButton(android.R.string.cancel, cancel);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(oncancel);
        dialog.show();
    }

    private final IDataProvider mDefaultData = new IDataProvider(this) {
        @Override
        public boolean onEdit(IKView pIKView) {
            if (pIKView == null) return false;
            return onStartEdit(this, pIKView.getDataItem(), pIKView.getSelectElement());
        }

        @Override
        public boolean save(File file) {
            return onSave(this, file);
        }

        @Override
        public boolean load(File file) {
            return onLoad(this, file);
        }
    };

}
