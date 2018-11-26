package com.kk.imageeditor.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kk.imageeditor.bean.data.SubItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComboAdapter extends BaseAdapter {
    private final List<SubItem> mItemList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public ComboAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
    }

    public ComboAdapter(Context context,Collection<SubItem> items) {
        this(context);
        setDatas(items);
    }

    public void setDatas(Collection<SubItem> items) {
        mItemList.addAll(items);
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public SubItem getItem(int position) {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public int getIndex(String value) {
        int count = getCount();
        for (int i = 0; i < count; i++) {
            SubItem item = getItem(i);
            if (TextUtils.equals(value, item.getValue())){
                return i;
            }
        }
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            View view = layoutInflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
            textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setSingleLine();
            textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        } else {
            textView = (TextView) convertView;
        }
        SubItem subItem = getItem(position);
        if (TextUtils.isEmpty(subItem.getName())) {
            textView.setText(subItem.getValue());
        } else {
            textView.setText(subItem.getName());
        }
        return textView;
    }
}