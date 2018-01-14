package com.mph.letapp.presentation.mapper;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.utils.Mapper;

public class TVShowViewModelMapper extends Mapper<TVShowViewModel, TVShow> {

    @Override
    public TVShow map(TVShowViewModel value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TVShowViewModel reverseMap(TVShow value) {
        return TVShowViewModel.builder()
                .setId(value.getId())
                .setTitle(value.getName())
                .setDescription(value.getDescription())
                .setImage(value.getPoster())
                .setBanner(value.getBackdrop())
                .setRating(value.getRating())
                .build();
    }
}
