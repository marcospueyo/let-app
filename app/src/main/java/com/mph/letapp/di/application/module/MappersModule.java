package com.mph.letapp.di.application.module;

import com.mph.letapp.network.mapper.RestTVShowMapper;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class MappersModule {

    @Provides
    @Singleton
    TVShowViewModelMapper provideTVShowViewModelMapper() {
        return new TVShowViewModelMapper();
    }

    @Provides
    @Singleton
    RestTVShowMapper provideRestTVShowMapper() {
        return new RestTVShowMapper(NetworkModule.POSTER_BASE_URL, NetworkModule.BACKDROP_BASE_URL);
    }

    public interface Exposes {
        TVShowViewModelMapper tvShowViewModelMapper();
        RestTVShowMapper restTVShowMapper();
    }
}
