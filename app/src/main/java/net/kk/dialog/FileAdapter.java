package net.kk.dialog;

import android.content.Context;
import android.graphics.Color;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.imageeditor.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class FileAdapter extends BaseAdapter {
    private final List<File> mFileList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private IImagerDisplay mIImagerDisplay;
    private boolean hasCreateFolder = false;
    public final static int POS_LAST_DIR = 0;
    private File mDir;
    private File mRoot;
    private File mChoose;

    public FileAdapter(Context context, IImagerDisplay iImagerDisplay) {
        mLayoutInflater = LayoutInflater.from(context);
        mIImagerDisplay = iImagerDisplay;
        mRoot = Environment.getExternalStorageDirectory();
    }

    public void setHasCreateFolder(boolean hasCreateFolder) {
        this.hasCreateFolder = hasCreateFolder;
    }

    public boolean isHasCreateFolder() {
        return hasCreateFolder;
    }

    public void setRoot(File root) {
        mRoot = root;
    }

    public void setChoose(File choose) {
        if (mChoose != null && mChoose.equals(choose)) {
            mChoose = null;
        } else {
            mChoose = choose;
        }
    }

    /***
     * 当前目录
     */
    public File getCurDir() {
        return mDir;
    }

    public File getChoose() {
        return mChoose;
    }

    public boolean setFiles(File dir, File[] files) {
        if (mRoot != null && dir != null
                && mRoot.getAbsolutePath().length() > dir.getAbsolutePath().length()) {
            return false;
        }
        this.mDir = dir;
        mChoose = null;
        mFileList.clear();
        if (files != null) {
            for (File file : files) {
                mFileList.add(file);
            }
            Collections.sort(mFileList, new FileComparator());
        }
        return true;
    }

    @Override
    public int getCount() {
        //lastdir
        return mFileList.size() + 1;
    }

    @Override
    public File getItem(int position) {
        if (position == POS_LAST_DIR) {
            return mDir;
        }
        position = position - 1;
        if (position >= 0 && position < getCount()) {
            return mFileList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_file, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(R.layout.item_file, viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag(R.layout.item_file);
        }
        File file = getItem(position);
        if (position == POS_LAST_DIR) {
            mIImagerDisplay.bind(mDir, viewHolder.icon);
            viewHolder.text.setText(R.string.last_directory);
        } else {
            mIImagerDisplay.bind(file, viewHolder.icon);
            viewHolder.text.setText(file.getName());
        }
        if (mChoose != null && TextUtils.equals(mChoose.getAbsolutePath(), file.getAbsolutePath())) {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.file_choose_color));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    private class ViewHolder {
        private ViewHolder(View view) {
            this.icon = (ImageView) view.findViewById(R.id.file_item_icon);
            this.text = (TextView) view.findViewById(R.id.file_item_text);
//            this.check = (CheckBox) view.findViewById(R.id.file_item_check);
        }

        final ImageView icon;
        final TextView text;
//        final CheckBox check;
    }
}
