package com.mph.letapp.network.mapper;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.network.model.RestTVShow;
import com.mph.letapp.utils.Mapper;

public class RestTVShowMapper extends Mapper<RestTVShow, TVShow> {

    @Override
    public TVShow map(RestTVShow value) {
        TVShow tvShow = new TVShow();
        tvShow.setId(value.getId());
        tvShow.setName(value.getName());
        tvShow.setDescription(value.getOverview());
        tvShow.setOriginalName(value.getOriginalName());
        tvShow.setBackdrop(value.getBackdropPath());
        tvShow.setPoster(value.getPosterPath());
        tvShow.setRating(value.getVoteAverage());
        tvShow.setPopularity(value.getPopularity());
        return tvShow;
    }

    @Override
    public RestTVShow reverseMap(TVShow value) {
        throw new UnsupportedOperationException();
    }
}
