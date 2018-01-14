package com.mph.letapp.domain.interactor;



import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;

public interface GetTVShowsInteractor {

    Observable<List<TVShow>> execute(boolean forceRefresh, int elementsPerPage, int page);
}
