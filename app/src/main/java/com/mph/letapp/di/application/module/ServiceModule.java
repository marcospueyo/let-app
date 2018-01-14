package com.mph.letapp.di.application.module;

import com.mph.letapp.network.service.TVShowService;
import com.mph.letapp.network.service.TVShowServiceImpl;
import com.mph.letapp.network.service.api.TMDBService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
public final class ServiceModule {

    private static final String API_KEY = "2164e55b33196c93e39d82297e5219ad";

    private static final String LANG = "en-US";

    @Provides
    @Singleton
    TVShowService provideRepositoryService(TMDBService TMDBService) {
        return new TVShowServiceImpl(TMDBService, API_KEY, LANG);
    }

    public interface Exposes {
        TVShowService repositoryService();
    }
}
