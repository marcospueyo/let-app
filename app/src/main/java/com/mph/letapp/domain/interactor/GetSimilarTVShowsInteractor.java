package com.mph.letapp.domain.interactor;

import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;

public interface GetSimilarTVShowsInteractor {

    Observable<List<TVShow>> execute(String tvShowID, int page, int elementsPerPage);
}
