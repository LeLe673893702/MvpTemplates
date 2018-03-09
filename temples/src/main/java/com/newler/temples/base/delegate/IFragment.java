/**
  * Copyright 2017 JessYan
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *      http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package com.newler.temples.base.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.newler.temples.base.common.BaseFragment;
import com.newler.temples.di.component.AppComponent;
import com.newler.temples.integration.cache.Cache;
import com.newler.temples.integration.cache.LruCache;
import com.newler.temples.integration.store.lifecyclemodel.LifecycleModel;
import com.newler.temples.integration.store.lifecyclemodel.LifecycleModelProviders;

/**
 * ================================================
 * 框架要求框架中的每个 {@link Fragment} 都需要实现此类,以满足规范
 *
 * @see BaseFragment
 * Created by JessYan on 29/04/2017 14:31
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public interface IFragment {

    /**
     * 提供在 {@link Fragment} 生命周期内的缓存容器, 可向此 {@link Fragment} 存取一些必要的数据
     * 此缓存容器和 {@link Fragment} 的生命周期绑定, 如果 {@link Fragment} 在屏幕旋转或者配置更改的情况下
     * 重新创建, 那此缓存容器中的数据也会被清空, 如果你想避免此种情况请使用 {@link LifecycleModel}
     *
     * @see LifecycleModelProviders#of(Fragment)
     * @return like {@link LruCache}
     */
    @NonNull
    Cache<String, Object> provideCache();

    /**
     * 提供 AppComponent(提供所有的单例对象)给实现类,进行 Component 依赖
     *
     * @param appComponent
     */
    void setupFragmentComponent(AppComponent appComponent);

    /**
     * 初始化 View
     */
    void initView();

    /**
     * 获取layout id
     *
     * @params
     * @return
     */
    int getLayoutResId();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    void initData(Bundle savedInstanceState);


   /**
    * 用来注册一些点击事件或者广播等的注册
    *
    * @params
    * @return
    */
    void registerEvent();

    /**
     * 对事件或者广播的解绑
     *
     * @params
     * @return
     */
    void unRegisterEvent();
}
