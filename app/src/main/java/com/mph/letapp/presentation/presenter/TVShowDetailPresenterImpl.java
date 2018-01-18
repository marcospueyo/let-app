package com.mph.letapp.presentation.presenter;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.domain.interactor.GetSimilarTVShowsInteractor;
import com.mph.letapp.domain.interactor.GetSingleTVShowInteractor;
import com.mph.letapp.presentation.TVShowDetailView;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;
import com.mph.letapp.presentation.model.TVShowViewModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class TVShowDetailPresenterImpl implements TVShowDetailPresenter {

    @SuppressWarnings("unused")
    public static final String TAG = TVShowDetailPresenterImpl.class.getSimpleName();

    private TVShowDetailView mView;

    @NonNull
    TVShowViewModelMapper mMapper;

    @NonNull
    GetSingleTVShowInteractor mGetSingleTVShowInteractor;

    @NonNull
    GetSimilarTVShowsInteractor mGetSimilarTVShowsInteractor;

    private boolean mFetchInProcess;

    private String mOriginalTVShowID;

    private final int mTVShowsPerPage;

    private int mCurrentPage;

    private int mCurrentIndex;

    private List<TVShowViewModel> mTVShows;

    public TVShowDetailPresenterImpl(@NonNull TVShowDetailView view,
                                     @NonNull TVShowViewModelMapper mapper,
                                     @NonNull GetSingleTVShowInteractor getSingleTVShowInteractor,
                                     @NonNull GetSimilarTVShowsInteractor
                                             getSimilarTVShowsInteractor,
                                     int tvShowsPerPage) {
        mView = view;
        mMapper = mapper;
        mGetSingleTVShowInteractor = getSingleTVShowInteractor;
        mGetSimilarTVShowsInteractor = getSimilarTVShowsInteractor;
        mTVShowsPerPage = tvShowsPerPage;

        mFetchInProcess = false;
        mCurrentPage = 0;
        mCurrentIndex = 0;
        mTVShows = new ArrayList<>();
    }

    @Override
    public void initialize(@NonNull String tvShowID) {
        mOriginalTVShowID = tvShowID;
    }

    @Override
    public void start() {
        loadTVShow();

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {
        mGetSimilarTVShowsInteractor.dispose();
        mView = null;
    }

    @Override
    public void onForceRefresh() {

    }

    @Override
    public void onSwipeLeft() {
        showPreviousSimilarTVShow();
    }

    @Override
    public void onSwipeRight() {
        if (!mFetchInProcess && mustFetchMoreTVShows()) {
            loadSimilarTVShows();
        }
        else {
            showNextSimilarTVShow();
        }
    }

    private boolean mustFetchMoreTVShows() {
        return (mCurrentIndex + 1) >= mTVShows.size();
    }

    private void handleFetchedData(List<TVShow> list) {
        saveFetchedPage(mMapper.reverseMap(list));
        if (list.size() > 0) {
            mCurrentPage++;
            showNextSimilarTVShow();
        }
        else {
            showNoSimilarTVShows();
        }
    }

    private void saveFetchedPage(List<TVShowViewModel> list) {
        mTVShows.addAll(list);
    }

    private void saveOriginalTVShow(TVShowViewModel tvShow) {
        if (mTVShows.size() > 0) {
            mTVShows.set(0, tvShow);
        }
        else {
            mTVShows.add(tvShow);
        }
    }

    private void showPreviousSimilarTVShow() {
        if (mCurrentIndex > 0) {
            updateViewWithTVShow(mTVShows.get(--mCurrentIndex));
            if (mCurrentIndex == 0) {
                mView.hideAvailablePreviousShow();
            }
        }
    }

    private void showNextSimilarTVShow() {
        if (mTVShows.size() > (mCurrentIndex + 1)) {
            if (mCurrentIndex == 0) {
                mView.showAvailablePreviousShow();
            }
            updateViewWithTVShow(mTVShows.get(++mCurrentIndex));
        }
    }

    private void showOriginalTVShow() {
        if (mTVShows.size() > 0) {
            mCurrentIndex = 0;
            mCurrentPage = 0;

            mView.hideAvailablePreviousShow();
            updateViewWithTVShow(mTVShows.get(0));
        }
    }

    private void loadTVShow() {
        handleFetchStarted();

        mGetSingleTVShowInteractor
            .execute(mOriginalTVShowID, false)
            .doOnSuccess(new Consumer<TVShow>() {
                @Override
                public void accept(TVShow tvShow) throws Exception {
                    handleFetchEnded();
                    saveOriginalTVShow(mMapper.reverseMap(tvShow));
                    showOriginalTVShow();
                }
            })
            .doOnError(new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    handleFetchEnded();
                    updateViewWithLoadError();
                }
        }).subscribe();
    }

    private void loadSimilarTVShows() {
        setFetchProcessState(true);
        mGetSimilarTVShowsInteractor.execute(new SimilarTVShowsObserver(), mOriginalTVShowID,
                mTVShowsPerPage, mCurrentPage);
    }

    private void handleFetchStarted() {
        setFetchProcessState(true);
        setViewLoadingState(true);
    }

    private void handleFetchEnded() {
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

    private void updateViewWithTVShow(TVShowViewModel tvShow) {
        mView.showTVShow(tvShow);
    }

    private void updateViewWithLoadError() {
        mView.showLoadError();
    }

    private void showNoSimilarTVShows() {
        mView.showNoSimilarShows();
    }

    private final class SimilarTVShowsObserver extends DisposableObserver<List<TVShow>> {

        @Override
        public void onNext(List<TVShow> tvShows) {
            TVShowDetailPresenterImpl.this.handleFetchEnded();
            TVShowDetailPresenterImpl.this.handleFetchedData(tvShows);
        }

        @Override
        public void onError(Throwable e) {
            TVShowDetailPresenterImpl.this.handleFetchEnded();
            TVShowDetailPresenterImpl.this.updateViewWithLoadError();
        }

        @Override
        public void onComplete() {

        }
    }
}
