package com.mph.letapp.domain.interactor;

import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.observers.DisposableObserver;

public interface GetSimilarTVShowsInteractor {

    void execute(DisposableObserver<List<TVShow>> observer, String tvShowID,
                 int page, int elementsPerPage);

    void dispose();
}
