package com.mph.letapp.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.domain.interactor.GetSingleTVShowInteractor;
import com.mph.letapp.presentation.TVShowDetailView;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;
import com.mph.letapp.presentation.navigation.Router;

import org.reactivestreams.Subscription;

import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class TVShowDetailPresenterImpl implements TVShowDetailPresenter {

    @SuppressWarnings("unused")
    public static final String TAG = TVShowDetailPresenterImpl.class.getSimpleName();

    @NonNull
    private final TVShowDetailView mView;

    @NonNull
    TVShowViewModelMapper mMapper;

    @NonNull
    GetSingleTVShowInteractor mGetSingleTVShowInteractor;

    @NonNull
    private final Router mRouter;

    private boolean mFetchInProcess;

    private String mCurrentTVShowID;

    public TVShowDetailPresenterImpl(@NonNull TVShowDetailView view,
                                     @NonNull TVShowViewModelMapper mapper,
                                     @NonNull Router router,
                                     @NonNull GetSingleTVShowInteractor getSingleTVShowInteractor) {
        Log.d(TAG, "TVShowDetailPresenterImpl: created");
        mView = view;
        mMapper = mapper;
        mRouter = router;
        mGetSingleTVShowInteractor = getSingleTVShowInteractor;

        mFetchInProcess = false;
    }

    @Override
    public void initialize(@NonNull String tvShowID) {
        mCurrentTVShowID = tvShowID;
    }

    @Override
    public void start() {
        setViewLoadingState(true);
        loadTVShow();

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onForceRefresh() {

    }

    @Override
    public void onSwipeLeft() {
        Log.d(TAG, "onSwipeLeft: ");
    }

    @Override
    public void onSwipeRight() {
        Log.d(TAG, "onSwipeRight: ");
    }

    private void loadTVShow() {
        setFetchProcessState(true);

        mGetSingleTVShowInteractor
            .execute(mCurrentTVShowID, false)
            .doOnSuccess(new Consumer<TVShow>() {
                @Override
                public void accept(TVShow tvShow) throws Exception {
                    handleFetchCompleted();
                    updateViewWithTVShow(tvShow);
                }
            })
            .doOnError(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    handleFetchCompleted();
                    updateViewWithLoadError();
                }
        }).subscribe();
    }

    private void handleFetchCompleted() {
        setFetchProcessState(false);
        setViewLoadingState(false);
    }

    private void setFetchProcessState(boolean isLoading) {
        mFetchInProcess = isLoading;
    }

    private void setViewLoadingState(boolean isLoading) {
        if (isLoading) {
            mView.showProgress();
        }
        else {
            mView.hideProgress();
        }
    }

    private void updateViewWithTVShow(TVShow tvShow) {
        mView.showTVShow(mMapper.reverseMap(tvShow));
    }

    private void updateViewWithLoadError() {
        mView.showLoadError();
    }
}
