package com.newler.temples.base.common;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * @author 刺雒
 * @what
 * @date 2018/1/8
 */

public abstract class BaseHolder<T> extends RecyclerView.ViewHolder {
    public BaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        registerEvent();
    }


    public abstract void setData(T item) ;

    public abstract void registerEvent();
}
