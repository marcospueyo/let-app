package com.mph.letapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.mph.letapp.R;
import com.mph.letapp.di.activity.ActivityComponent;
import com.mph.letapp.di.activity.DaggerActivity;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;
import com.mph.letapp.presentation.view.TVShowListView;
import com.mph.letapp.presentation.view.component.DividerItemDecoration;
import com.mph.letapp.presentation.view.adapter.TVShowAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TVShowListActivity extends DaggerActivity implements TVShowListView,
        SwipeRefreshLayout.OnRefreshListener {

    @Inject
    TVShowListPresenter mPresenter;

    @BindView(R.id.swipe_refresh_layout) SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.listview) RecyclerView mRecyclerView;

    private TVShowAdapter mAdapter;

    private static final int mPageSize = 20;

    private static final int mScrollThreshold = 3;

    public static Intent newInstance(Context context) {
        return new Intent(context, TVShowListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initListView();
        setForceRefreshListener();
    }

    @Override
    protected void inject(final ActivityComponent activityComponent) {
        activityComponent.inject(this);
    }

    private void initListView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        initializeRecyclerView(layoutManager);
        initializeAdapter();
        initScrollListener(mPageSize, mScrollThreshold, layoutManager);
    }

    private void initializeRecyclerView(final LinearLayoutManager layoutManager) {
        layoutManager.setStackFromEnd(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
    }

    private void initializeAdapter() {
        mAdapter = new TVShowAdapter(this, mPresenter, getImageRequestManager());
        mRecyclerView.setAdapter(mAdapter);
    }

    private RequestManager getImageRequestManager() {
        return Glide.with(this);
    }

    private void initScrollListener(final int elementsPerPage, final int scrollThreshold,
                                    final LinearLayoutManager layoutManager) {
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastVisible = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = mAdapter.getItemCount();
                if (itemCount > (elementsPerPage - 1)
                        && Math.abs(itemCount - lastVisible) < scrollThreshold) {
                    mPresenter.onScrollDown();
                }
            }
        });
    }

    private void setForceRefreshListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.onForceRefresh();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mPresenter.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.resume();
    }

    @Override
    public void showTVShows(List<TVShowViewModel> tvShows) {
        mAdapter.setItemList(tvShows);
    }

    @Override
    public void addTVShows(List<TVShowViewModel> tvShows) {
        mAdapter.addToItemList(tvShows);
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.entities_load_error),
                Snackbar.LENGTH_SHORT).show();
    }
}
