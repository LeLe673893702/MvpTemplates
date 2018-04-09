package com.newler.temples.base.listview;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewStub;

import com.newler.temples.R;
import com.newler.temples.base.stateview.BaseStateFragment;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

import java.util.List;

import me.drakeet.multitype.MultiTypeAdapter;

/**
 *  ListFragment
 *
 * @author liule
 * @time 2018/3/9
 */
public abstract class BaseListFragment<LP extends ListTemplatesPresenter> extends BaseStateFragment<LP> implements ListTemplatesView {
    protected SmartRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    private ViewStub headerViewStub;
    private MultiTypeAdapter mAdapter;

    @Override
    public void initView() {
        super.initView();

        mRefreshLayout = getView().findViewById(R.id.refresh_layout);
        headerViewStub = getView().findViewById(R.id.view_stub_header);
        mRecyclerView = getView().findViewById(android.R.id.list);

        if (getLayoutResId() != 0) {
            headerViewStub.setLayoutResource(getHeaderLayoutResId());
            headerViewStub.inflate();
        }

        if (getRefreshContentLayoutResId() != 0) {
            mRefreshLayout.setRefreshContent(LayoutInflater.from(getContext()).inflate(getRefreshContentLayoutResId(), null));
        }

        buildRecycleView();
        buildRefreshLayout();
    }

    @Override
    public int getStateContentLayoutResId() {
        return R.layout.layout_base_list;
    }

    /**
     * 配置refreshLayout属性,默认header设置为MaterialHeader
     */
    protected void buildRefreshLayout() {
        mRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(getContext()).setSpinnerStyle(SpinnerStyle.Scale));

        mRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.refreshData());
        mRefreshLayout.setOnLoadMoreListener(refreshLayout -> mPresenter.loadMoreData());
    }

    /**
     * 配置recycleView属性，默认为竖向列表
     */
    protected void buildRecycleView() {
        mAdapter = new MultiTypeAdapter();

        registerViewBinder(mAdapter);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    }

    @Override
    public void refreshDataSuccess(List<?> datas) {
        mAdapter.setItems(datas);
        mAdapter.notifyDataSetChanged();
        mRefreshLayout.finishRefresh(true);
    }

    @Override
    public void refreshDataFail() {
        mRefreshLayout.finishRefresh(false);
    }

    @Override
    public void loadMoreDataSuccess(List<?> datas, int start, int count) {
        mAdapter.setItems(datas);
        mAdapter.notifyItemRangeInserted(start, count);
        mRefreshLayout.finishLoadMore(true);
    }

    @Override
    public void loadMoreFail() {
        mRefreshLayout.finishLoadMore(false);
    }

    @Override
    public void loadNoMoreData() {
        mRefreshLayout.finishLoadMoreWithNoMoreData();
    }

    @Override
    public void resetNoMoreData() {
        mRefreshLayout.setNoMoreData(true);
    }

    /**
     * 给Adapter设置ViewBinder
     * @param multiTypeAdapter
     *
     */
    protected abstract void registerViewBinder(MultiTypeAdapter multiTypeAdapter);

    /**
     * 获取refreshLayout内部layoutResID
     * @return
     */
    public abstract int getRefreshContentLayoutResId();

    /**
     * 获取layout头部layoutResID
     * @return
     */
    public abstract int getHeaderLayoutResId();
}
