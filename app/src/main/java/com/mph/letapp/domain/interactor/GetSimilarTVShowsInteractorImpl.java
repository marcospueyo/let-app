package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class GetSimilarTVShowsInteractorImpl extends Interactor<List<TVShow>>
        implements GetSimilarTVShowsInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSimilarTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    public GetSimilarTVShowsInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                           @NonNull Scheduler mainThread,
                                           @NonNull Scheduler backgroundThread) {
        super(mainThread, backgroundThread);
        mTVShowRepository = tvShowRepository;
    }

    @Override
    public void execute(DisposableObserver<List<TVShow>> disposableObserver,
                        String tvShowID, int page, int elementsPerPage) {
        final Observable<List<TVShow>> observable = mTVShowRepository
                .getSimilarTVShows(tvShowID, page, elementsPerPage);
        subscribeObserver(observable, disposableObserver);
    }
}
