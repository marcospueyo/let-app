package com.mph.letapp.presentation;

import com.mph.letapp.presentation.model.TVShowViewModel;

public interface TVShowDetailView {

    void showTVShow(TVShowViewModel tvShow);

    void showProgress();

    void hideProgress();

    void showLoadError();
}
