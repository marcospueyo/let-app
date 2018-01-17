package com.mph.letapp.di.activity;

import android.app.Activity;
import android.content.Context;

import com.mph.letapp.domain.interactor.GetSimilarTVShowsInteractor;
import com.mph.letapp.domain.interactor.GetSingleTVShowInteractor;
import com.mph.letapp.domain.interactor.GetTVShowsInteractor;
import com.mph.letapp.presentation.TVShowDetailView;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;
import com.mph.letapp.presentation.navigation.Router;
import com.mph.letapp.presentation.navigation.RouterImpl;
import com.mph.letapp.presentation.presenter.TVShowDetailPresenter;
import com.mph.letapp.presentation.presenter.TVShowDetailPresenterImpl;
import com.mph.letapp.presentation.presenter.TVShowListPresenter;
import com.mph.letapp.presentation.presenter.TVShowListPresenterImpl;
import com.mph.letapp.presentation.view.TVShowListView;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    public static final int ELEMENTS_PER_PAGE = 20;

    private final DaggerActivity daggerActivity;

    public ActivityModule(final DaggerActivity daggerActivity) {
        this.daggerActivity = daggerActivity;
    }

    @Provides
    @ActivityScope
    @ForActivity
    Context provideActivityContext() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return daggerActivity;
    }

    @Provides
    @ActivityScope
    Router provideRouter() {
        return new RouterImpl(daggerActivity);
    }

    @Provides
    @ActivityScope
    TVShowListPresenter provideMainPresenter(TVShowViewModelMapper tvShowViewModelMapper,
                                             Router router,
                                             GetTVShowsInteractor getTVShowsInteractor) {
        final TVShowListPresenter TVShowListPresenter =
                new TVShowListPresenterImpl((TVShowListView) daggerActivity, getTVShowsInteractor,
                        tvShowViewModelMapper, router, ELEMENTS_PER_PAGE);
        daggerActivity.getActivityComponent().inject(TVShowListPresenter);
        return TVShowListPresenter;
    }

    @Provides
    @ActivityScope
    TVShowDetailPresenter provideTVShowDetailPresenter(TVShowViewModelMapper tvShowViewModelMapper,
                                       GetSingleTVShowInteractor getSingleTVShowInteractor,
                                       GetSimilarTVShowsInteractor getSimilarTVShowsInteractor) {
        final TVShowDetailPresenter tvShowDetailPresenter =
                new TVShowDetailPresenterImpl((TVShowDetailView) daggerActivity,
                        tvShowViewModelMapper, getSingleTVShowInteractor,
                        getSimilarTVShowsInteractor, ELEMENTS_PER_PAGE);
        daggerActivity.getActivityComponent().inject(tvShowDetailPresenter);
        return tvShowDetailPresenter;
    }

    public interface Exposes {

        Activity activity();

        @ForActivity
        Context context();

    }
}
