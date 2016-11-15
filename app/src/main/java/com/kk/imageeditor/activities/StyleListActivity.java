package com.kk.imageeditor.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.imageeditor.R;
import com.kk.imageeditor.bean.StyleInfo;
import com.kk.imageeditor.controllor.ControllorManager;
import com.kk.imageeditor.controllor.PathConrollor;
import com.kk.imageeditor.draw.Drawer;
import com.kk.imageeditor.utils.BitmapUtil;
import com.kk.imageeditor.utils.VUiKit;

import java.util.ArrayList;
import java.util.List;

public class StyleListActivity extends BaseActivity {
    public static final int STYLE_BACK = 0x1000 + 4;
    private StyleAdapter mStyleAdapter;
    private PathConrollor pathConrollor;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylelist);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_style);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mStyleAdapter = new StyleAdapter();
        mRecyclerView.setAdapter(mStyleAdapter);
        enableBackHome();
        pathConrollor = ControllorManager.get().getPathConrollor();
        load();
    }

    private void load() {
        ProgressDialog dialog = ProgressDialog.show(this, null, getString(R.string.load_style_list));
        VUiKit.defer().when(() -> {
            return Drawer.getStyleList(pathConrollor.getStylePath(), ".xml", ".zip");
        }).done((list) -> {
            mStyleAdapter.attach(list);
            dialog.dismiss();
        });
    }

    private class StyleAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private final List<StyleInfo> StyleInfos = new ArrayList<>();
        LayoutInflater mLayoutInflater;

        public StyleAdapter() {
            mLayoutInflater = LayoutInflater.from(StyleListActivity.this);
        }

        public void attach(List<StyleInfo> infos) {
            StyleInfos.clear();
            if (infos != null) {
                StyleInfos.addAll(infos);
            }
            notifyDataSetChanged();
        }

        public StyleInfo getItem(int pos) {
            return StyleInfos.get(pos);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View convertView = mLayoutInflater.inflate(R.layout.item_style, parent, false);
            return new MyViewHolder(convertView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.refresh(StyleInfos.get(position));
        }

        @Override
        public int getItemCount() {
            return StyleInfos.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView headImageView;
        private TextView headTitleView;
        private TextView headAuthorView;
        private TextView headVerView;
        private View mParent;

        MyViewHolder(View view) {
            super(view);
            mParent = view;
            headImageView = (ImageView) view.findViewById(R.id.imageView);
            headTitleView = (TextView) view.findViewById(R.id.titleView);
            headAuthorView = (TextView) view.findViewById(R.id.authorView);
            headVerView = (TextView) view.findViewById(R.id.versionView);
        }

        void refresh(StyleInfo info) {
            if (info == null) {
                return;
            }
            if (headImageView != null) {
                Bitmap icon = BitmapUtil.getBitmapFormZip(info.getDataPath(), info.getIcon(), 0, 0);
                headImageView.setImageBitmap(icon);
            }
            if (headVerView != null) {
                headVerView.setText("" + info.getVersion());
            }
            if (headTitleView != null) {
                headTitleView.setText(info.getName());
            }
            if (headAuthorView != null) {
                headAuthorView.setText(info.getAuthor());
            }
            mParent.setOnClickListener((v) -> {
                setResult(STYLE_BACK, new Intent().putExtra(Intent.EXTRA_TEXT, info.getStylePath()));
                finish();
            });
        }
    }
}
