package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public class GetSingleTVShowInteractorImpl implements GetSingleTVShowInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSingleTVShowInteractorImpl.class.getSimpleName();


    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    private CompositeDisposable mCompositeDisposable;

    public GetSingleTVShowInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                         @NonNull Scheduler mainThread,
                                         @NonNull Scheduler backgroundThread) {
        mTVShowRepository = tvShowRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public void execute(DisposableObserver<TVShow> disposableObserver, String id,
                        boolean forceRefresh) {
        final Observable<TVShow> observable = mTVShowRepository
                                                    .getTVShow(id)
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
