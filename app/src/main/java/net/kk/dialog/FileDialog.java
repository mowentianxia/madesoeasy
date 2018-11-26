package net.kk.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.kk.imageeditor.R;

import java.io.File;

public class FileDialog extends AlertDialog implements ListView.OnItemClickListener {
    public enum Mode {
        OpenFile,
        SaveFile,
        ChooseDirectory,
    }

    public interface FileChooseListener {
        void onChoose(DialogInterface dlg, File file);
    }

    private final Mode mMode;
    private final FileAdapter mFileAdapter;
    private FileFilter2 mFileFilter;
    private TextView mPathView;
    private boolean noNeedClose = false;
    private EditText mEditText;
    private FileChooseListener mFileChooseListener;
    private static IImagerDisplay mDefault = new DefaultImagerDisplay();

    public static void setImagerDisplayDefault(IImagerDisplay mDefault) {
        FileDialog.mDefault = mDefault;
    }

    public FileDialog(Context context, Mode mode) {
        this(context, mode, mDefault);
    }

    public FileDialog(Context context, Mode mode, IImagerDisplay iImagerDisplay) {
        this(context, R.style.BaseDialog_NoFrame, mode, iImagerDisplay);
    }

    public FileDialog(Context context, int themeResId, final Mode mode, IImagerDisplay iImagerDisplay) {
        super(context, themeResId);
        mMode = mode;
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_file_browser, null);
        setView(view);
        ListView listView = (ListView) view.findViewById(R.id.file_listview);
        mFileAdapter = new FileAdapter(context, iImagerDisplay);
        listView.setAdapter(mFileAdapter);
        listView.setOnItemClickListener(this);
        mPathView = (TextView) view.findViewById(R.id.file_path);
        view.setMinimumHeight((int) (Resources.getSystem().getDisplayMetrics().heightPixels * 0.75f));
        setTitle(R.string.file_default_title);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        mEditText = (EditText) view.findViewById(R.id.edt_new_folder);
        setButton(AlertDialog.BUTTON_NEGATIVE, context.getString(android.R.string.cancel), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        if (mode == Mode.SaveFile || mode == Mode.ChooseDirectory) {
            view.findViewById(R.id.layout_new_folder).setVisibility(View.VISIBLE);
            setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.create_folder), new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    noNeedClose = true;
                    String name = mEditText.getText().toString();
                    if (mFileAdapter.getCurDir() != null && !TextUtils.isEmpty(name)) {
                        File dir = new File(mFileAdapter.getCurDir(), name);
                        if (dir.exists()) {
                            return;
                        }
                        dir.mkdirs();
                        mEditText.setText(null);
                        setDir(dir);
                    }
                }
            });
        }
        setButton(AlertDialog.BUTTON_POSITIVE, context.getString(android.R.string.ok), new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mFileChooseListener != null) {
                    if (mMode == Mode.ChooseDirectory) {
                        mFileChooseListener.onChoose(dialog, mFileAdapter.getCurDir());
                    } else if (mMode == Mode.SaveFile && !TextUtils.isEmpty(mEditText.getText())) {
                        File file = new File(mFileAdapter.getCurDir(), mEditText.getText().toString());
                        if (!file.exists() || !file.isDirectory()) {
                            mFileChooseListener.onChoose(dialog, file);
                        }
                    } else if (mFileAdapter.getChoose() != null) {
                        mFileChooseListener.onChoose(dialog, mFileAdapter.getChoose());
                    } else {
                        mFileChooseListener.onChoose(dialog, null);
                    }
                } else {
                    dialog.dismiss();
                }
            }
        });
    }

    public void setInputText(String text) {
        mEditText.setText(text);
    }

    public void setFileChooseListener(FileChooseListener fileChooseListener) {
        mFileChooseListener = fileChooseListener;
    }

    public void setPath(File dir, FileFilter2 fileFilter) {
        if (dir == null) {
            return;
        }
        mFileFilter = fileFilter == null ? FileFilter2.DEBUFALT : fileFilter;
        setDir(dir);
    }

    private void setDir(File dir) {
        boolean rs;
        if (dir.isDirectory()) {
            rs = mFileAdapter.setFiles(dir, dir.listFiles(mFileFilter));
        } else {
            rs = mFileAdapter.setFiles(dir.getParentFile(), null);
        }
        if (rs) {
            mPathView.setText(dir.getAbsolutePath());
            mFileAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        File file = position == FileAdapter.POS_LAST_DIR ?
                mFileAdapter.getItem(position).getParentFile() : mFileAdapter.getItem(position);
        if (file != null) {
            if (file.isDirectory()) {
                setPath(file, mFileFilter);
            } else {
                if (mMode != Mode.ChooseDirectory) {
                    mFileAdapter.setChoose(file);
                    mFileAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        if (noNeedClose) {
            noNeedClose = false;
            return;
        }
        super.dismiss();
    }
}
