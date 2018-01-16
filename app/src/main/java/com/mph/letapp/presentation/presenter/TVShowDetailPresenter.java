package com.mph.letapp.presentation.presenter;

public interface TVShowDetailPresenter {

    void start();

    void resume();

    void destroy();

    void onForceRefresh();

    void onSwipeLeft();

    void onSwipeRight();

    void initialize(String tvShowID);
}
