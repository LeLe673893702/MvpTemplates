package com.newler.temples.base.stateview;

import com.newler.temples.base.common.TemplatesView;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public interface StateTemplatesView extends TemplatesView {
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
