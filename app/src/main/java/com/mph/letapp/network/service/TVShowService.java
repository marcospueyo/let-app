package com.mph.letapp.network.service;


import com.mph.letapp.network.model.RestTVShow;

import java.util.List;

import io.reactivex.Observable;

public interface TVShowService {

    Observable<List<RestTVShow>> getTVShows(int page, int tvShowsPerPage);
}
