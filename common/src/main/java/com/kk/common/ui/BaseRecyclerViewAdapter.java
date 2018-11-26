package com.kk.common.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("UnnecessaryBoxing")
public abstract class BaseRecyclerViewAdapter<T extends BaseRecyclerViewHolder, D> extends RecyclerView.Adapter<T> {
    private static final boolean DEBUG = false;//Constants.DEBUG;
    private static final String TAG = "AppLauncherAdapter";
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private final List<D> mAppDataList = new ArrayList<>();

    private boolean mEditMode;
    private final List<Integer> mChooses = new ArrayList<>();

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return mContext;
    }

    protected LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    public boolean isEditMode() {
        return mEditMode;
    }

    public void setEditMode(boolean editMode) {
        mEditMode = editMode;
        mChooses.clear();
    }

    public List<D> getItems() {
        return mAppDataList;
    }

    @NonNull
    @Override
    public abstract T onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull T holder, int position);

    @Override
    public int getItemCount() {
        return mAppDataList.size();
    }

    public boolean hasItem(D data) {
        return getPosition(data) >= 0;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean refresh(D model) {
        int index = getPosition(model);
        if (index >= 0) {
            notifyItemChanged(index);
            return true;
        }
        return false;
    }

    private int getPosition(D data) {
        for (int i = 0; i < mAppDataList.size(); i++) {
            D d = mAppDataList.get(i);
            if (d.equals(data)) {
                return i;
            }
        }
        return -1;
    }

    public void add(D appData) {
        add(appData, false);
    }

    public void add(D appData, boolean notify) {
        add(-1, appData, notify);
    }

    public void add(int pos, D appData, boolean notify) {
        if (pos >= 0) {
            mAppDataList.add(pos, appData);
            if (notify) {
                notifyItemInserted(pos);
            }
        } else {
            mAppDataList.add(appData);
            if (notify) {
                notifyItemInserted(mAppDataList.size() - 1);
            }
        }
    }

    public int getChooseCount() {
        return mChooses.size();
    }

    protected boolean isChoose(int pos) {
        return mChooses.contains(Integer.valueOf(pos));
    }

    protected boolean removeChoose(int pos) {
        return mChooses.remove(Integer.valueOf(pos));
    }

    private void addChooses(List<Integer> chooses) {
        mChooses.addAll(chooses);
    }

    protected void addChoose(int pos) {
        if (isChoose(pos)) {
            return;
        }
        mChooses.add(Integer.valueOf(pos));
    }

    public void set(Collection<D> appDatas) {
        if (appDatas != mAppDataList) {
            mAppDataList.clear();
            addAll(appDatas);
        }
    }

    public void addAll(Collection<D> appDatas) {
        if (appDatas != null) {
            mAppDataList.addAll(appDatas);
        }
    }

    public D getItem(int pos) {
        return mAppDataList.get(pos);
    }

    private List<Integer> getChooses() {
        return mChooses;
    }

    public List<D> removeAllChoose() {
        if (DEBUG) {
            Log.d(TAG, "remove:" + getChooses());
        }
        List<D> deletes = new ArrayList<>();
        for (Integer id : mChooses) {
            D appData = mAppDataList.get(id);
//            BitmapUtils.dispose(appData.getIcon());
            deletes.add(appData);
        }
        mAppDataList.removeAll(deletes);
        mChooses.clear();
        return deletes;
    }

    public void cancelAllChoose() {
        mChooses.clear();
    }

    protected boolean canChoose(int position) {
        return true;
    }

    public void chooseAll(ChooseProxy proxy) {
        mChooses.clear();
        for (int i = 0; i < getItemCount(); i++) {
            if (!canChoose(i)) {
                continue;
            }
            if (proxy != null) {
                if (!proxy.canChoose(i)) {
                    continue;
                }
            }
            mChooses.add(i);
        }
    }

    public interface ChooseProxy {
        boolean canChoose(int pos);
    }

    public D remove(int pos) {
        return remove(pos, false);
    }

    public void remove(D appData) {
        remove(appData, false);
    }

    public void remove(D appData, boolean notify) {
        int pos = getPosition(appData);
        if (pos >= 0) {
            remove(pos, notify);
        }
    }

    public D remove(int pos, boolean notify) {
        D d = null;
        if (pos < getItemCount()) {
            d = mAppDataList.remove(pos);
            if (notify) {
                notifyItemRemoved(pos);
            }
        }
        return d;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    protected boolean canMove(int from) {
        return true;
    }

    @SuppressWarnings("UnusedReturnValue")
    public boolean move(int from, int to) {
        if (!canMove(from) || !canMove(to)) {
            return false;
        }
        D item = mAppDataList.remove(from);
        notifyItemMoved(from, to);
        mAppDataList.add(to, item);
        if (DEBUG) {
            Log.i(TAG, mChooses + " move " + from + "->" + to);
        }
        List<Integer> oldChoose = new ArrayList<>();
        //123456
        if (from < to) {
            if (removeChoose(from)) {
                oldChoose.add(to);
                if (DEBUG) {
                    Log.d(TAG, "changed choose " + from + "->" + to);
                }
            }
            for (int i = from + 1; i <= to; i++) {
                //2->6
                if (removeChoose(i)) {
                    oldChoose.add((i - 1));
                    if (DEBUG) {
                        Log.d(TAG, "changed choose " + i + "->" + (i - 1));
                    }
                }
            }
        } else {
            //6->2
            if (removeChoose(from)) {
                oldChoose.add(to);
                if (DEBUG) {
                    Log.d(TAG, "changed choose " + from + "->" + to);
                }
            }
            for (int i = to; i < from; i++) {
                if (removeChoose(i)) {
                    oldChoose.add((i + 1));
                    if (DEBUG) {
                        Log.d("kk", "changed choose " + i + "->" + (i + 1));
                    }
                }
            }
        }
        addChooses(oldChoose);
        return true;
    }
}
