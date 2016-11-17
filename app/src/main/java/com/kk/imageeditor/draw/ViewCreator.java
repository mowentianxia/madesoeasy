package com.kk.imageeditor.draw;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kk.imageeditor.Constants;
import com.kk.imageeditor.bean.Style;
import com.kk.imageeditor.bean.enums.LayoutType;
import com.kk.imageeditor.bean.view.ImageInfo;
import com.kk.imageeditor.bean.view.LayoutInfo;
import com.kk.imageeditor.bean.view.TextInfo;
import com.kk.imageeditor.bean.view.ViewInfo;
import com.kk.imageeditor.view.IKView;
import com.kk.imageeditor.view.KFrameLayout;
import com.kk.imageeditor.view.KImageView;
import com.kk.imageeditor.view.KLinearLayout;
import com.kk.imageeditor.view.KTextView;

import java.util.List;


public class ViewCreator {
    Context context;

    public ViewCreator(Context context) {
        this.context = context;
    }

    public View draw(Style pStyleInfo, ViewGroup viewGroup, IDataProvider IDataor) {
        if(Constants.DEBUG){
            Log.i("kk","scale="+pStyleInfo.getScale());
        }
        View view = drawViewGroup(pStyleInfo, IDataor, pStyleInfo.getLayoutInfo());
        ViewGroup.LayoutParams params = ScaleHelper.getLayoutParams((IKView) view, viewGroup, pStyleInfo.getScale());
        if (Constants.DEBUG)
            Log.v("kk", "params="+ params.getClass()+" "+params.width+"/"+params.height);
        viewGroup.addView(view, params);
        return view;
    }

    private ViewGroup drawViewGroup(Style pStyleInfo, final IDataProvider IDataor, LayoutInfo pLayoutInfo) {
        if (pLayoutInfo == null) {
            return null;
        }
        ViewGroup viewGroup;
        if (pLayoutInfo.getLayoutType() == LayoutType.frame) {
            viewGroup = drawFrameLayout(pStyleInfo, IDataor, pLayoutInfo);
        } else {
            viewGroup = drawLinearLayout(pStyleInfo, IDataor, pLayoutInfo);
        }
        List<ViewInfo> Infos = pLayoutInfo.getViews();
        for (ViewInfo Info : Infos) {
            View view = null;
            if (Info instanceof LayoutInfo) {
                view = drawViewGroup(pStyleInfo, IDataor, (LayoutInfo) Info);
                if (Constants.DEBUG)
                    Log.v("kk", "create layout "+view.getClass());
            } else if (Info instanceof ImageInfo) {
                view = drawImage(pStyleInfo, IDataor, (ImageInfo) Info);
                if (Constants.DEBUG)
                    Log.v("kk", "create imageview");
            } else if (Info instanceof TextInfo) {
                view = drawText(pStyleInfo, IDataor, (TextInfo) Info);
                if (Constants.DEBUG)
                    Log.v("kk", "create textview");
            }
            if (view != null) {
                ViewGroup.LayoutParams params = ScaleHelper.getLayoutParams((IKView) view, viewGroup, pStyleInfo.getScale());
                if (Constants.DEBUG)
                    Log.v("kk", "params="+ params.getClass()+" "+params.width+"/"+params.height);
                viewGroup.addView(view, params);
                view.invalidate();
            }
        }
        return viewGroup;
    }

    private ViewGroup drawFrameLayout(Style pStyleInfo, final IDataProvider IDataor, LayoutInfo item) {
        KFrameLayout view = new KFrameLayout(context);
        view.update(IDataor.get(item));
        view.bind(pStyleInfo.getDataElement(item.getClickName()), item);
        bind(view, IDataor);
        return view;
    }

    private ViewGroup drawLinearLayout(Style pStyleInfo, final IDataProvider IDataor, LayoutInfo item) {
        KLinearLayout view = new KLinearLayout(context);
        view.update(IDataor.get(item));
        view.bind(pStyleInfo.getDataElement(item.getClickName()), item);
        bind(view, IDataor);
        return view;
    }

    private ImageView drawImage(Style pStyleInfo, final IDataProvider IDataor, ImageInfo pImageInfo) {
        KImageView imageView = new KImageView(context);
        imageView.update(IDataor.get(pImageInfo));
        imageView.bind(pStyleInfo.getDataElement(pImageInfo.getClickName()), pImageInfo);
        bind(imageView, IDataor);
        return imageView;
    }

    private View drawText(Style pStyleInfo, final IDataProvider IDataor, TextInfo pTextInfo) {
        KTextView textView = new KTextView(context);
        textView.update(IDataor.get(pTextInfo));
        textView.bind(pStyleInfo.getDataElement(pTextInfo.getClickName()), pTextInfo);
        bind(textView, IDataor);
        return textView;
    }

    private void bind(IKView pIKView, final IDataProvider IDataor) {
        if (pIKView == null || pIKView.getView() == null) return;
        if (pIKView.getSelectElement() != null) {
            pIKView.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v instanceof IKView) {
                        IKView ikView = (IKView) v;
                        if (IDataor != null) {
                            IDataor.onEdit(ikView);
                        }
                    }
                }
            });
        }
    }
}