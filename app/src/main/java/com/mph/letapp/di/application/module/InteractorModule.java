package com.mph.letapp.di.application.module;

import com.mph.letapp.domain.interactor.GetSingleTVShowInteractor;
import com.mph.letapp.domain.interactor.GetSingleTVShowInteractorImpl;
import com.mph.letapp.domain.interactor.GetTVShowsInteractor;
import com.mph.letapp.domain.interactor.GetTVShowsInteractorImpl;
import com.mph.letapp.domain.data.TVShowRepository;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;

@Module
public final class InteractorModule {

    @Provides
    @Singleton
    GetTVShowsInteractor provideGetTVShowsInteractor(TVShowRepository TVShowRepository,
                         @Named(ThreadingModule.MAIN_SCHEDULER) Scheduler mainThread,
                         @Named(ThreadingModule.BACKGROUND_SCHEDULER) Scheduler backgroundThread) {
        return new GetTVShowsInteractorImpl(TVShowRepository, mainThread, backgroundThread);
    }

    @Provides
    @Singleton
    GetSingleTVShowInteractor provideGetSingleTVShowInteractor(TVShowRepository tvShowRepository,
                       @Named(ThreadingModule.MAIN_SCHEDULER) Scheduler mainThread,
                       @Named(ThreadingModule.BACKGROUND_SCHEDULER) Scheduler backgroundThread) {
        return new GetSingleTVShowInteractorImpl(tvShowRepository, mainThread, backgroundThread);
    }

    public interface Exposes {

        GetTVShowsInteractor getTVShowsInteractor();

        GetSingleTVShowInteractor getGetSingleTVShowInteractor();
    }
}
