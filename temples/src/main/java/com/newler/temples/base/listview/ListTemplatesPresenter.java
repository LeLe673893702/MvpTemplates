package com.newler.temples.base.listview;

import com.newler.temples.base.stateview.StateTemplatesPresenter;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface ListTemplatesPresenter extends StateTemplatesPresenter {
    /**
     * 刷新数据
     */
    void refreshData();

    /**
     * 加载更多数据
     */
    void loadMoreData();
}
