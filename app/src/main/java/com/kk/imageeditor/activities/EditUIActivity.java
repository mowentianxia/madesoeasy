package com.kk.imageeditor.activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.bean.data.ImageData;
import com.kk.imageeditor.bean.data.SelectElement;
import com.kk.imageeditor.bean.data.SubItem;
import com.kk.imageeditor.bean.data.TextData;
import com.kk.imageeditor.bean.data.ViewData;
import com.kk.imageeditor.bean.enums.DataType;
import com.kk.imageeditor.controllor.ControllorManager;
import com.kk.imageeditor.controllor.PathConrollor;
import com.kk.imageeditor.controllor.StyleControllor;
import com.kk.imageeditor.draw.IDataProvider;
import com.kk.imageeditor.utils.SaveUtil;
import com.kk.imageeditor.utils.StringUtil;
import com.kk.imageeditor.utils.VUiKit;
import com.kk.imageeditor.view.IKView;
import com.kk.imageeditor.widgets.ComboAdapter;
import com.kk.imageeditor.widgets.ISelectImage;
import com.kk.imageeditor.widgets.ISelectImageListener;

import java.io.File;
import java.util.List;
import java.util.Map;

abstract class EditUIActivity extends BaseActivity implements ISelectImageListener {
    protected int itemHeight;
    protected PathConrollor pathConrollor;
    protected StyleControllor styleControllor;
    protected SelectElement tmpSelectElement;

    protected abstract void updateViews();

    protected abstract ISelectImage getSelectImage();

    protected IDataProvider getDefaultData() {
        return mDefaultData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        itemHeight = Math.round(Math.max(dm.widthPixels, dm.heightPixels) / 15.0f);
        pathConrollor = ControllorManager.get().getPathConrollor();
        styleControllor = ControllorManager.get().getStyleControllor();
    }

    /***
     * 更新数据
     */
    public void updateData() {
        mDefaultData.updateDatas(false);
    }


    protected void onEditCompleted(String... values) {
        mDefaultData.onChanged(tmpSelectElement, values);
        updateViews();
    }

    protected StyleInfo getStyleInfo() {
        return mDefaultData.getStyleInfo();
    }

    /***
     * 保存缓存
     *
     * @return
     */
    protected boolean saveCache() {
        if (!mDefaultData.isLoad()) return false;
        return SaveUtil.saveSetCache(mDefaultData);
    }

    /***
     * 清理缓存
     */
    protected void clearCache() {
        SaveUtil.clearTempSet(mDefaultData);
    }

    /***
     * 加载缓存
     *
     * @return
     */
    protected boolean loadCache() {
        if (!mDefaultData.isLoad()) return false;
        return SaveUtil.loadSetCache(mDefaultData);
    }

    /***
     * 获取保存的名字
     *
     * @return
     */
    protected String getSaveFileName() {
        return mDefaultData.getSaveFileName();
    }

    protected String getDataValue(String name) {
        return mDefaultData.getValue(name);
    }

    @Override
    public void onSelectImage(String path) {

    }

    @Override
    public void onCurImageCompleted(String path) {
        onEditCompleted(path);
    }

    //region 编辑事件处理
    protected boolean onStartEdit(IDataProvider pIData, ViewData data, SelectElement pSelectElement) {
        if (pSelectElement == null) {
            return false;
        }
        tmpSelectElement = pSelectElement;
        if (Constants.DEBUG) {
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
                    getSelectImage().setListener(this);
                    getSelectImage().startPhotoCut(file, (int) data.width, (int) data.height, true);
                }
                break;
            case select:
                showComboBox(pIData, pSelectElement);
                break;
            case multi_select:
                showMultiComboBox(pIData, pSelectElement);
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
    //endregion

    //region 编辑UI
    protected ViewGroup.LayoutParams getItemLayoutParams() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, itemHeight);
        lp.gravity = Gravity.CENTER_VERTICAL;
        lp.leftMargin = VUiKit.dpToPx(10);
        lp.rightMargin = VUiKit.dpToPx(10);
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
        lp.leftMargin = VUiKit.dpToPx(10);
        lp.rightMargin = VUiKit.dpToPx(10);
        textView.setLayoutParams(lp);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        return textView;
    }

    protected void showComboBox(IDataProvider pIData, SelectElement pSelectElement) {
        if (pSelectElement == null)
            return;
        String value = pIData.getValue(pSelectElement.getName());
        final Spinner sp = createSpinner(value, pSelectElement.getItems());
        showDialog(pSelectElement.getDesc(), (v, w) -> {
            onEditCompleted(getSpinnerValue(sp));
        }, (v, s) -> {
        }, null, sp);
    }

    protected void showMultiComboBox(IDataProvider pIData, SelectElement pSelectElement) {
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
                if (Constants.DEBUG) {
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
        }, (v, s) -> {
        }, null, views);
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
            editText.setMinLines(10);
        else {
            editText.setSingleLine();
        }
        ViewGroup.LayoutParams lp=getItemLayoutParams();
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        editText.setLayoutParams(lp);
        editText.setText(defalutValue);
        return editText;
    }

    protected void inputText(String desc, String defalutValue, boolean singleLine) {
        final EditText editText = createInputText(defalutValue, singleLine);
        showDialog(desc, (v, w) -> {
            onEditCompleted("" + editText.getText());
        }, (v, s) -> {
        }, null, editText);
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
        showDialog(title, null, layout, ok, cancel, oncancel);
    }

    public void showDialog(String title, String message, final DialogInterface.OnClickListener ok) {
        showDialog(title, message, null, ok, (v, s) -> {
        }, null);
    }

    public void showDialog(String title, String message,
                           final View view,
                           final DialogInterface.OnClickListener ok,
                           final DialogInterface.OnClickListener cancel,
                           final DialogInterface.OnCancelListener onCancel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.BaseDialog);
        if (!TextUtils.isEmpty(title))
            builder.setTitle(title);
        if (!TextUtils.isEmpty(message))
            builder.setMessage(message);
        if (view != null) {
            builder.setView(view);
        }
        builder.setPositiveButton(android.R.string.ok, ok);
        if (cancel != null) {
            builder.setCancelable(false);
            builder.setNegativeButton(android.R.string.cancel, (v, s) -> {
                v.dismiss();
                cancel.onClick(v, s);
            });
        }
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnCancelListener(onCancel);
        dialog.show();
    }
    //endregion

    //region 内容提供
    private final IDataProvider mDefaultData = new IDataProvider(this) {
        @Override
        public boolean onEdit(IKView pIKView) {
            if (pIKView == null) return false;
            return onStartEdit(this, pIKView.getDataItem(), pIKView.getSelectElement());
        }

        @Override
        public boolean save(File file) {
            return SaveUtil.saveSet(this, file.getAbsolutePath());
        }

        @Override
        public boolean load(File file) {
            return SaveUtil.loadSet(this, file.getAbsolutePath());
        }

        @Override
        public void updateValues(Map<String, String> datas) {
            super.updateValues(datas);
        }

        @Override
        public String getCachePath(String name) {
            if (TextUtils.isEmpty(name)) {
                return pathConrollor.getCachePath();
            }
            return new File(pathConrollor.getCachePath(), name).getAbsolutePath();
        }

        @Override
        public String getTempPath(String name) {
            if (TextUtils.isEmpty(name)) {
                return pathConrollor.getTempPath();
            }
            return new File(pathConrollor.getTempPath(), name).getAbsolutePath();
        }
    };
    //endregion

}
