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

public class GetSingleTVShowInteractorImpl extends Interactor<TVShow>
        implements GetSingleTVShowInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSingleTVShowInteractorImpl.class.getSimpleName();


    @NonNull
    private final TVShowRepository mTVShowRepository;


    public GetSingleTVShowInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                         @NonNull Scheduler mainThread,
                                         @NonNull Scheduler backgroundThread) {
        super(mainThread, backgroundThread);
        mTVShowRepository = tvShowRepository;
    }

    @Override
    public void execute(DisposableObserver<TVShow> disposableObserver, String id,
                        boolean forceRefresh) {
        final Observable<TVShow> observable = mTVShowRepository
                                                    .getTVShow(id);
        subscribeObserver(observable, disposableObserver);

    }
}
