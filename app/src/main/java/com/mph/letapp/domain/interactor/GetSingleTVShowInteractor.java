package com.mph.letapp.domain.interactor;

import com.mph.letapp.domain.data.model.TVShow;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface GetSingleTVShowInteractor {

    Single<TVShow> execute(String id, boolean forceRefresh);

    Observable<TVShow> executeObs(String id, boolean forceRefresh);
}
