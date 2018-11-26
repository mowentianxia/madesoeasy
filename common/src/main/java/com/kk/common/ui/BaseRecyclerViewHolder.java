package com.kk.common.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseRecyclerViewHolder extends RecyclerView.ViewHolder {
    public BaseRecyclerViewHolder(View itemView) {
        super(itemView);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T $(int id){
        return (T)itemView.findViewById(id);
    }
}
