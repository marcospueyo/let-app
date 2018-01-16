package com.mph.letapp.network.mapper;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.network.model.RestTVShow;
import com.mph.letapp.utils.Mapper;

public class RestTVShowMapper extends Mapper<RestTVShow, TVShow> {

    @NonNull
    private final String mLogoBaseURL;

    @NonNull
    private final String mBackdropBaseURL;


    public RestTVShowMapper(@NonNull String logoBaseURL, @NonNull String backdropBaseURL) {
        mLogoBaseURL = logoBaseURL;
        mBackdropBaseURL = backdropBaseURL;
    }

    @Override
    public TVShow map(RestTVShow value) {
        TVShow tvShow = new TVShow();
        tvShow.setId(value.getId());
        tvShow.setName(value.getName());
        tvShow.setDescription(value.getOverview());
        tvShow.setOriginalName(value.getOriginalName());
        tvShow.setBackdrop(mBackdropBaseURL + value.getBackdropPath());
        tvShow.setPoster(mLogoBaseURL + value.getPosterPath());
        tvShow.setRating(value.getVoteAverage());
        tvShow.setPopularity(value.getPopularity());
        return tvShow;
    }

    @Override
    public RestTVShow reverseMap(TVShow value) {
        throw new UnsupportedOperationException();
    }
}
