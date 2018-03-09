package com.newler.temples.base.state;

import com.newler.temples.base.common.IView;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface IStateView extends IView {
    /**
     * 显示主界面
     */
    void showContent();

    /**
     * 加载失败
     */
    void loadedFail();

    /**
     * 重新加载
     */
    void showRetryLoading();

}
