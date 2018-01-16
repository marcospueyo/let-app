package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetSimilarTVShowsInteractorImpl implements GetSimilarTVShowsInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSimilarTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    public GetSimilarTVShowsInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                           @NonNull Scheduler mainThread,
                                           @NonNull Scheduler backgroundThread) {
        this.mTVShowRepository = tvShowRepository;
        this.mBackgroundThread = backgroundThread;
        this.mMainThread = mainThread;
    }

    @Override
    public Observable<List<TVShow>> execute(String tvShowID, int page, int elementsPerPage) {
        return mTVShowRepository
                .getSimilarTVShows(tvShowID, page, elementsPerPage)
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }
}
