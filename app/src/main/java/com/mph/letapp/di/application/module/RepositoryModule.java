package com.mph.letapp.di.application.module;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.TVShowRepositoryImpl;
import com.mph.letapp.domain.data.model.TVShowDao;
import com.mph.letapp.network.service.TVShowService;
import com.mph.letapp.network.mapper.RestTVShowMapper;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

import static com.mph.letapp.di.application.module.ThreadingModule.BACKGROUND_SCHEDULER;


@Module
public final class RepositoryModule {

    @Provides
    @Singleton
    TVShowRepository provideTVShowRepository(TVShowService TVShowService,
                                             TVShowDao TVShowDao,
                                             RestTVShowMapper mapper,
                                             @Named(BACKGROUND_SCHEDULER) Scheduler scheduler) {
        return new TVShowRepositoryImpl(TVShowService, TVShowDao, mapper, scheduler);
    }

    public interface Exposes {

        TVShowRepository tvShowRepository();

    }
}
