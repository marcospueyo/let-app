package com.mph.letapp.domain.data.model;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface TVShowDao {

    Observable<List<TVShow>> getTVShows(int page, int elementsPerPage);

    Observable<TVShow> getTVShow(String id);

    void deleteAllTVShows();

    void deleteTVShow(String id);

    void insertTVShow(TVShow tvShow);

    void insertTVShows(Iterable<TVShow> tvShows);

    int getTVShowCount();
}
