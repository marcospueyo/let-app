package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class GetTVShowsInteractorImpl implements GetTVShowsInteractor {

    @SuppressWarnings("unused")
    private static final String TAG = GetTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    private CompositeDisposable mCompositeDisposable;

    public GetTVShowsInteractorImpl(@NonNull TVShowRepository TVShowRepository,
                                    @NonNull Scheduler mainThread,
                                    @NonNull Scheduler backgroundThread) {
        mTVShowRepository = TVShowRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public void execute(DisposableObserver<List<TVShow>> disposableObserver, boolean forceRefresh,
                        int elementsPerPage, int page) {
        Observable<List<TVShow>> observable = handleForceRefresh(forceRefresh)
                .andThen(fetchIfNeeded(forceRefresh, elementsPerPage, page))
                .andThen(mTVShowRepository.getTVShowPage(page, elementsPerPage))
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
        final DisposableObserver observer = observable.subscribeWith(disposableObserver);
        getCompositeDisposable().add(observer);
    }

    @Override
    public void dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();
        }
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

    private CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }
}
