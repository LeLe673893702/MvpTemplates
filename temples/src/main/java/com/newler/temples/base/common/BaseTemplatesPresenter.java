/**
 * Copyright 2017 JessYan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.newler.temples.base.common;

import android.app.Activity;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.hwangjr.rxbus.RxBus;
import com.newler.temples.integration.RepositoryManager;
import com.newler.temples.utils.Preconditions;
import com.newler.temples.utils.RxUtil;
import com.uber.autodispose.AutoDisposeConverter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * ================================================
 * 基类 TemplatesPresenter
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.4">TemplatesPresenter wiki 官方文档</a>
 * Created by JessYan on 4/28/2016
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */

public class BaseTemplatesPresenter<V extends TemplatesView> implements TemplatesPresenter, LifecycleObserver {
    protected final String TAG = this.getClass().getSimpleName();
    protected CompositeDisposable mCompositeDisposable;

    protected V mRootView;
    protected RepositoryManager mModel;
    /**
     * 如果当前页面同时需要 Model 层和 TemplatesView 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    @Inject
    public BaseTemplatesPresenter(RepositoryManager model, V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", TemplatesView.class.getName());
        this.mModel = model;
        this.mRootView = rootView;
        onStart();
    }

    /**
     * 适用于RxJava解除订阅
     */
    protected <T> AutoDisposeConverter<T> bindLifecycle() {
        if (mRootView instanceof LifecycleOwner) {
            return RxUtil.bindLifecycle((LifecycleOwner) mRootView);
        } else {
            throw new IllegalStateException("view must implement LifecycleOwner");
        }
    }

    /**
     * 如果当前页面不需要操作数据,只需要 TemplatesView 层,则使用此构造函数
     *
     * @param rootView
     */
    public BaseTemplatesPresenter(V rootView) {
        Preconditions.checkNotNull(rootView, "%s cannot be null", TemplatesView.class.getName());
        this.mRootView = rootView;
        onStart();
    }

    public BaseTemplatesPresenter() {
        onStart();
    }


    /**
     * Activity和Fragment中onCreate方法会调用
     * 当Fragment使用时不要在onStart里面加载数据操纵界面，因为在fragment，onCreate方法界面还有没有加载出来，抛空指针异常
     */
    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().addObserver(this);
        }
        //如果要使用 RxBus 请将此方法返回 true
        if (useRxBus()) {
            RxBus.get().register(this);
        }
    }

    /**
     * 在框架中 {@link Activity#onDestroy()} 时会默认调用 {@link TemplatesPresenter#onDestroy()}
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    @Override
    public void onDestroy() {
        if (mRootView != null && mRootView instanceof LifecycleOwner) {
            ((LifecycleOwner) mRootView).getLifecycle().removeObserver(this);
        }
        // 如果要使用 RxBus 请将此方法返回 true
        if (useRxBus()) {
            // RxBus
            RxBus.get().unregister(this);
        }

        this.mModel = null;
        this.mRootView = null;
        this.mCompositeDisposable = null;
    }


    /**
     * 是否使用 {@link RxBus},默认为使用(true)，
     *
     * @return
     */
    public boolean useRxBus() {
        return true;
    }
}
