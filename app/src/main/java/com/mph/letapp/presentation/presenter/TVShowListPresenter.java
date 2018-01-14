package com.mph.letapp.presentation.presenter;

import com.mph.letapp.presentation.model.TVShowViewModel;

public interface TVShowListPresenter {

    void start();

    void resume();

    void destroy();

    void onForceRefresh();

    void onScrollDown();

    void onTVShowSelected(TVShowViewModel tvShow);

}