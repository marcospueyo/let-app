package com.mph.letapp.di.activity;

import com.mph.letapp.presentation.presenter.TVShowDetailPresenter;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;
import com.mph.letapp.presentation.view.activity.TVShowDetailActivity;
import com.mph.letapp.presentation.view.activity.TVShowListActivity;


public interface ActivityComponentInjects {

    void inject(TVShowListActivity mainActivity);
    void inject(TVShowListPresenter TVShowListPresenter);

    void inject(TVShowDetailActivity tvShowDetailActivity);
    void inject(TVShowDetailPresenter tvShowDetailPresenter);
}
