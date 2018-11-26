package com.kk.common.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.kk.common.ui.QuickOnClickListener;

public abstract class BaseFragment extends Fragment implements IQuickBinder {
    protected View mRootView;
    private boolean mPause;
    protected BaseActivity mCompatActivity;
    private boolean mVisible = false;
    private boolean mInitView = false;

    protected abstract int getLayoutId();

    protected abstract void onInitView();

    public boolean isFragmentVisible() {
        return mVisible;
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCompatActivity = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCompatActivity = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPause = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPause = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean isPausing() {
        return mPause;
    }

    public boolean isInitView() {
        return mInitView;
    }

    protected void onInitData() {

    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        mInitView = false;
        onInitView();
        mInitView = true;
        onInitData();
        return mRootView;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        mVisible = isVisibleToUser;
        if (mCompatActivity == null) {
            return;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }


    public void finishActivity() {
        Activity activity = getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    /**
     * @return 是否能结束
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean onBack() {
        return true;
    }

    @Override
    public <T extends View> T bind(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    @Override
    public <T extends CheckBox> T bindCheck(int id, CompoundButton.OnCheckedChangeListener clickListener) {
        T t = bind(id);
        if (t != null && clickListener != null) {
            t.setOnCheckedChangeListener(clickListener);
        }
        return t;
    }

    @Override
    public <T extends View> T bindClick(int id, View.OnClickListener clickListener) {
        T t = bind(id);
        if (t != null && clickListener != null) {
            t.setOnClickListener(clickListener);
        }
        return t;
    }

    @Override
    public <T extends View> T bindClick(int id, QuickOnClickListener clickListener) {
        T t = bind(id);
        if (t != null && clickListener != null) {
            t.setOnClickListener((v) -> clickListener.onClick());
        }
        return t;
    }

    @Override
    public BaseActivity getBaseActivity() {
        return mCompatActivity;
    }
}
