package com.newler.temples.base.state;

import com.newler.temples.base.common.IPresenter;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface IStatePresenter extends IPresenter {
    /**
     * 加载数据
     */
    void loadData();
}
