package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;

public class GetTVShowsInteractorImpl extends Interactor<List<TVShow>> implements GetTVShowsInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = GetTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    public GetTVShowsInteractorImpl(@NonNull TVShowRepository TVShowRepository,
                                    @NonNull Scheduler mainThread,
                                    @NonNull Scheduler backgroundThread) {
        super(mainThread, backgroundThread);
        mTVShowRepository = TVShowRepository;
    }

    @Override
    public void execute(DisposableObserver<List<TVShow>> disposableObserver, boolean forceRefresh,
                        int elementsPerPage, int page) {
        Observable<List<TVShow>> observable = handleForceRefresh(forceRefresh)
                .andThen(fetchIfNeeded(forceRefresh, elementsPerPage, page))
                .andThen(mTVShowRepository.getTVShowPage(page, elementsPerPage));
        subscribeObserver(observable, disposableObserver);
    }

    private Completable handleForceRefresh(final boolean forceRefresh) {
        if (forceRefresh) {
            return mTVShowRepository.clearTVShows();
        }
        else {
            return Completable.complete();
        }
    }

    private Completable fetchIfNeeded(boolean forceRefresh,
                                      final int elementsPerPage,
                                      final int page) {
        if (shouldLoadFromRemoteStore(forceRefresh, page, elementsPerPage)) {
            return mTVShowRepository.fetchRemoteTVShows(page, elementsPerPage);
        }
        else {
            return Completable.complete();
        }
    }

    private boolean shouldLoadFromRemoteStore(boolean forceRefresh, int page, int elementsPerPage) {
        return forceRefresh || (mTVShowRepository.localTVShowCount() < elementsPerPage * (page + 1));
    }
}
