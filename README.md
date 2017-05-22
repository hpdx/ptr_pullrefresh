# ptr_pullrefresh
Android上下拉刷新库ptr:[https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh](https://github.com/liaohuqiu/android-Ultra-Pull-To-Refresh)

## 使用：
在使用到fresco-helper库的Module中的build.gradle文件里，添加以下依赖：
```
 allprojects {
    repositories {
        jcenter()

        maven {
            url 'https://dl.bintray.com/hpdx/maven/'
        }
    }
 }

compile 'com.anbetter:ptr_pullrefresh:1.0.0'
```

在XML中添加
```
<in.srain.cube.views.ptr.PtrFrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
    android:id="@+id/ptr_refresh_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    cube_ptr:ptr_duration_to_close="200"
    cube_ptr:ptr_duration_to_close_header="600"
    cube_ptr:ptr_keep_header_when_refresh="true"
    cube_ptr:ptr_pull_to_fresh="false"
    cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
    cube_ptr:ptr_resistance="1.7">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:scrollbars="vertical"/>

</in.srain.cube.views.ptr.PtrFrameLayout>
```

示例代码：
```
package com.dating.pulltorefresh;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends AppCompatActivity {

    private PtrFrameLayout mPtrFrameLayout;
    private RecyclerView mRecyclerView;
    private PtrHeaderView mPtrHeaderView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_refresh_layout);
        mPtrHeaderView = new PtrHeaderView(this);
        mPtrFrameLayout.setHeaderView(mPtrHeaderView);
        mPtrFrameLayout.addPtrUIHandler(mPtrHeaderView);

        // 设置下拉刷新监听
        mPtrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                loadData();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    public void loadData() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ......
                onRefreshComplete();
            }
        }, 2000);
    }

    /**
     * 下拉刷新结束后调用该方法
     */
    public void onRefreshComplete() {
        mPtrFrameLayout.refreshComplete();
    }

    /**
     * 下拉刷新，会显示下拉的状态
     */
    public void autoRefresh() {
        if(mPtrFrameLayout != null) {
            mPtrFrameLayout.autoRefresh();
        }
    }

    /**
     * 若想禁用下拉刷新功能，请重写此方法
     * 示例：
     *  mPtrFrameLayout.setEnabled(false);
     */
    protected void setRefreshEnabled() {
        mPtrFrameLayout.setEnabled(true);
    }

}

```