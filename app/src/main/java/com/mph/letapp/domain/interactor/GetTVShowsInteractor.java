package com.mph.letapp.domain.interactor;



import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public interface GetTVShowsInteractor {

    void execute(DisposableObserver<List<TVShow>> observer,  boolean forceRefresh,
                 int elementsPerPage, int page);

    void dispose();
}
