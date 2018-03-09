package com.newler.temples.base.list;

import com.newler.temples.base.state.IStatePresenter;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface IListPresenter extends IStatePresenter {
    /**
     * 刷新数据
     */
    void refreshData();

    /**
     * 加载更多数据
     */
    void loadMoreData();
}
