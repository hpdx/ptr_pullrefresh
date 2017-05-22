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
