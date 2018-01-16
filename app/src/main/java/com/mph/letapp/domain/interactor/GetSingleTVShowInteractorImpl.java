package com.mph.letapp.domain.interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;

public class GetSingleTVShowInteractorImpl implements GetSingleTVShowInteractor {

    @SuppressWarnings("unused")
    public static final String TAG = GetSingleTVShowInteractorImpl.class.getSimpleName();


    @NonNull
    private final TVShowRepository mTVShowRepository;

    @NonNull
    private final Scheduler mBackgroundThread;

    @NonNull
    private final Scheduler mMainThread;

    public GetSingleTVShowInteractorImpl(@NonNull TVShowRepository tvShowRepository,
                                         @NonNull Scheduler mainThread,
                                         @NonNull Scheduler backgroundThread) {
        Log.d(TAG, "GetSingleTVShowInteractorImpl: created");
        mTVShowRepository = tvShowRepository;
        mBackgroundThread = backgroundThread;
        mMainThread = mainThread;
    }

    @Override
    public Single<TVShow> execute(String id, boolean forceRefresh) {
        return mTVShowRepository
                .getTVShow(id)
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }

    @Override
    public Observable<TVShow> executeObs(String id, boolean forceRefresh) {
        return mTVShowRepository
                .getLocalEntityObs(id)
                .subscribeOn(mBackgroundThread)
                .observeOn(mMainThread);
    }
}
