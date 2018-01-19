package com.mph.letapp.domain.data;


import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface TVShowRepository {

    Observable<List<TVShow>> getAll();

    Observable<TVShow> getTVShow(String id);

    Observable<List<TVShow>> getTVShowPage(int page, int maxCount);

    Observable<List<TVShow>> getSimilarTVShows(String tvShowID, int page, int maxCount);

    Completable fetchRemoteTVShows(int page, int maxCount);

    Completable clearTVShows();

    int localTVShowCount();

}
