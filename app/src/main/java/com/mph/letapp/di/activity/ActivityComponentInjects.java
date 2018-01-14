package com.mph.letapp.di.activity;

import com.mph.letapp.presentation.presenter.TVShowListPresenter;
import com.mph.letapp.presentation.view.activity.TVShowListActivity;


public interface ActivityComponentInjects {

    void inject(TVShowListActivity mainActivity);
    void inject(TVShowListPresenter TVShowListPresenter);
}
