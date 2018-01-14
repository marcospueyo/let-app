package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

public class GetTVShowsInteractorImpl implements GetTVShowsInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = GetTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    public GetTVShowsInteractorImpl(@NonNull TVShowRepository TVShowRepository,
                                    @NonNull Scheduler mainThread,
                                    @NonNull Scheduler backgroundThread) {
        mTVShowRepository = TVShowRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public Observable<List<TVShow>> execute(final boolean forceRefresh,
                                            final int elementsPerPage, final int page) {
        // TODO: Use a disposable observer to avoid memory leaks
        return  handleForceRefresh(forceRefresh)
                .andThen(fetchIfNeeded(forceRefresh, elementsPerPage, page))
                .andThen(mTVShowRepository.getTVShowPage(page, elementsPerPage))
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
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
