package com.mph.letapp.domain.interactor;
/* Created by macmini on 19/01/2018. */


import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;

public abstract class Interactor<T> {

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    private CompositeDisposable mCompositeDisposable;

    Interactor(@NonNull Scheduler mainThread,
                      @NonNull Scheduler backgroundThread) {
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    void subscribeObserver(Observable<T> observable,
                                     DisposableObserver<T> disposableObserver) {
        observable
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
        final DisposableObserver observer = observable.subscribeWith(disposableObserver);
        getCompositeDisposable().add(observer);
    }

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

