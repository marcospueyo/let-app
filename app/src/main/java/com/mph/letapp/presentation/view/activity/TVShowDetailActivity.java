package com.mph.letapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.mph.letapp.R;
import com.mph.letapp.di.activity.ActivityComponent;
import com.mph.letapp.di.activity.DaggerActivity;
import com.mph.letapp.presentation.TVShowDetailView;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.presenter.TVShowDetailPresenter;

import javax.inject.Inject;

public class TVShowDetailActivity extends DaggerActivity implements TVShowDetailView {

    public static final String SHOW_ID_KEY = "show_id";

    @SuppressWarnings("unused")
    public static final String TAG = TVShowDetailActivity.class.getSimpleName();

    @Inject
    TVShowDetailPresenter mPresenter;

    private String mShowID;

    public static Intent getNewIntent(Context context, String tvShowID) {
        Intent intent = new Intent(context, TVShowDetailActivity.class);
        intent.putExtra(SHOW_ID_KEY, tvShowID);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);
        loadShowDetails();
        initializePresenter();
    }

    @Override
    protected void inject(ActivityComponent activityComponent) {
        activityComponent.inject(this);
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
    public void showTVShow(TVShowViewModel tvShow) {
        setTitle(tvShow.title());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showLoadError() {
        Log.d(TAG, "showLoadError: ");
    }

    private void loadShowDetails() {
        if (getIntent().hasExtra(SHOW_ID_KEY)) {
            mShowID = getIntent().getStringExtra(SHOW_ID_KEY);
        }
    }

    private void initializePresenter() {
        if (mShowID != null && mPresenter != null) {
            mPresenter.initialize(mShowID);
        }
    }
}
