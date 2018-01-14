package com.mph.letapp.di.activity;

import android.app.Activity;
import android.content.Context;

import com.mph.letapp.domain.interactor.GetTVShowsInteractor;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;
import com.mph.letapp.presentation.navigation.Router;
import com.mph.letapp.presentation.navigation.RouterImpl;
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
    TVShowListPresenter provideMainPresenter(TVShowViewModelMapper TVShowViewModelMapper,
                                             Router router,
                                             GetTVShowsInteractor getTVShowsInteractor) {
        final TVShowListPresenter TVShowListPresenter =
                new TVShowListPresenterImpl((TVShowListView) daggerActivity, getTVShowsInteractor,
                        TVShowViewModelMapper, router, ELEMENTS_PER_PAGE);
        daggerActivity.getActivityComponent().inject(TVShowListPresenter);
        return TVShowListPresenter;
    }

    public interface Exposes {

        Activity activity();

        @ForActivity
        Context context();

    }
}
