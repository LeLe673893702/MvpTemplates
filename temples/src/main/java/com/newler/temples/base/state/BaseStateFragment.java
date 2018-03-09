package com.newler.temples.base.state;


import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newler.temples.R;
import com.newler.temples.base.common.BaseFragment;

/**
 * 状态fragment
 * @author liule
 * @time 2018/3/9
 */
public abstract class BaseStateFragment<SP extends IStatePresenter> extends BaseFragment<SP> implements IStateView {

    private ViewStub contentViewStub, loadedFailViewStub;
    private ProgressBar mProgressBar;
    private TextView tvLoadedFail;

    @Override
    public void initView() {
        contentViewStub = getView().findViewById(R.id.view_stub_content);
        loadedFailViewStub = getView().findViewById(R.id.view_stub_loaded_fail);
        mProgressBar = getView().findViewById(R.id.progress_bar_loading);

        contentViewStub.setLayoutResource(getStateContentLayoutResId());
    }

    @Override
    public int getLayoutResId() {
        return R.layout.layout_base_state;
    }


    @Override
    public void showContent() {
        contentViewStub.inflate();
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRetryLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
        tvLoadedFail.setVisibility(View.INVISIBLE);
    }

    @Override
    public void loadedFail() {
        if (tvLoadedFail == null) {
            loadedFailViewStub.inflate();
            tvLoadedFail = getView().findViewById(R.id.tv_loaded_fail);
            tvLoadedFail.setOnClickListener(view -> mPresenter.loadData());
        }
    }

    /**
     * 获取加载成功后主界面的layoutId
     *
     * @return
     */
    public abstract int getStateContentLayoutResId();
}
