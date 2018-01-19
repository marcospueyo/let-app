package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class GetSimilarTVShowsInteractorImpl implements GetSimilarTVShowsInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSimilarTVShowsInteractorImpl.class.getSimpleName();

    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    private CompositeDisposable mCompositeDisposable;

    public GetSimilarTVShowsInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                           @NonNull Scheduler mainThread,
                                           @NonNull Scheduler backgroundThread) {
        mTVShowRepository = tvShowRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public void execute(DisposableObserver<List<TVShow>> disposableObserver,
                        String tvShowID, int page, int elementsPerPage) {
        final Observable<List<TVShow>> observable = mTVShowRepository
                .getSimilarTVShows(tvShowID, page, elementsPerPage)
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

    private CompositeDisposable getCompositeDisposable() {
        if (mCompositeDisposable == null || mCompositeDisposable.isDisposed()) {
            mCompositeDisposable = new CompositeDisposable();
        }
        return mCompositeDisposable;
    }
}
