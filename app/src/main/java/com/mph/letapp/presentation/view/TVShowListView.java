package com.mph.letapp.presentation.view;

import com.mph.letapp.presentation.model.TVShowViewModel;

import java.util.List;

public interface TVShowListView {

    void showTVShows(List<TVShowViewModel> tvShows);

    void addTVShows(List<TVShowViewModel> tvShows);

    void showProgress();

    void hideProgress();

    void showLoadError();
}
