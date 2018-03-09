package com.newler.temples.base.state;

import android.view.View;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.newler.temples.R;
import com.newler.temples.base.common.BaseActivity;

/**
 *
 * @author liule
 * @time 2018/3/9
 */
public abstract class BaseStateActivity<SP extends IStatePresenter> extends BaseActivity<SP> implements IStateView {
    private ViewStub contentViewStub, loadedFailViewStub;
    private ProgressBar mProgressBar;
    private TextView tvLoadedFail;

    @Override
    public void initView() {
        contentViewStub = findViewById(R.id.view_stub_content);
        loadedFailViewStub = findViewById(R.id.view_stub_loaded_fail);
        mProgressBar = findViewById(R.id.progress_bar_loading);

        contentViewStub.setLayoutResource(getStateContentLayoutResId());

        mPresenter.loadData();
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
            tvLoadedFail = findViewById(R.id.tv_loaded_fail);
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
