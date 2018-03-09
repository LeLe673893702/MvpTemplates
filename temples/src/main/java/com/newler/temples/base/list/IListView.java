package com.newler.temples.base.list;

import com.newler.temples.base.state.IStatePresenter;
import com.newler.temples.base.state.IStateView;

import java.util.List;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface IListView extends IStateView {
    /**
     * 刷新数据成功
     * @param datas 刷新出来的数据
     */
    void refreshDataSuccess(List<?> datas);

    /**
     * 加载更多数据成功
     * @param datas 加载出来的数据
     * @param start 插入起始位置
     * @param count 插入数量
     */
    void loadMoreDataSuccess(List<?> datas, int start, int count);

    /**
     * 加载不出数据了
     */
    void loadNoMoreData();

    /**
     * 重新恢复到没有更多数据状态
     */
    void resetNoMoreData();

    /**
     * 刷新失败
     */
    void refreshDataFail();

    /**
     * 加载更多失败
     */
    void loadMoreFail();
}
