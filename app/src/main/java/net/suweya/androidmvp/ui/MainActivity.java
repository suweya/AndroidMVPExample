package net.suweya.androidmvp.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import net.suweya.androidmvp.R;
import net.suweya.androidmvp.base.BaseActivity;
import net.suweya.androidmvp.entities.DataEntity;
import net.suweya.androidmvp.presenter.MainPresenter;
import net.suweya.androidmvp.ui.adapter.SimpleAdapter;
import net.suweya.androidmvp.ui.interfaces.IMainView;
import net.suweya.recyclerviewex.EndlessRecyclerView;
import net.suweya.recyclerviewex.RecycleViewEx;

public class MainActivity extends BaseActivity<IMainView, MainPresenter> implements IMainView {

    private RecycleViewEx mRecycleViewEx;
    private SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecycleViewEx = (RecycleViewEx) findViewById(R.id.list);
        mRecycleViewEx.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.refreshData();
            }
        });
        mRecycleViewEx.setOnLoadNextPageListener(new EndlessRecyclerView.OnLoadNextPageListener() {
            @Override
            public void loadNextPage(int page) {
                Log.d("MainActivity", "load next page : " + page);
                mPresenter.loadNextPageData(page);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycleViewEx.setLayoutManager(manager);
        mRecycleViewEx.setPageIndex(2);

        mRecycleViewEx.post(new Runnable() {
            @Override
            public void run() {
                mRecycleViewEx.setRefreshing(true);
            }
        });
        mPresenter.refreshData();
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    public void hideRefreshLoading() {
        mRecycleViewEx.setRefreshing(false);
    }

    @Override
    public void onRefreshSuccess(DataEntity entity) {
        if (mAdapter == null) {
            mAdapter = new SimpleAdapter(entity.data);
            mRecycleViewEx.setAdapter(mAdapter);
        } else {
            mAdapter.onRefresh(entity.data);
        }
        mRecycleViewEx.setStateOnRefreshSuccess();
    }

    @Override
    public void onDataLoadSuccess(DataEntity entity) {
        if (mAdapter != null) {
            mAdapter.addAll(entity.data);
        }
        mRecycleViewEx.loadMoreComplete();
    }

    @Override
    public void onDataLoadError(String message) {
        mRecycleViewEx.loadMoreError();
    }

    @Override
    public void onRefreshError(String message) {
        mRecycleViewEx.setStateOnRefreshError();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
