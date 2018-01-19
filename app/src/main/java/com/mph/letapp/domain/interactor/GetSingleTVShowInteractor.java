package com.mph.letapp.domain.interactor;

import com.mph.letapp.domain.data.model.TVShow;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;

public interface GetSingleTVShowInteractor {

    void execute(DisposableObserver<TVShow> observer, String id, boolean forceRefresh);

    void dispose();

}
