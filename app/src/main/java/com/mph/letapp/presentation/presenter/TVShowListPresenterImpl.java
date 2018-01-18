package com.mph.letapp.presentation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.domain.interactor.GetTVShowsInteractor;
import com.mph.letapp.presentation.mapper.TVShowViewModelMapper;
import com.mph.letapp.presentation.model.TVShowViewModel;
import com.mph.letapp.presentation.navigation.Router;
import com.mph.letapp.presentation.view.TVShowListView;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class TVShowListPresenterImpl implements TVShowListPresenter {

    public static final String TAG = "TVShowListPresenterImpl";

    @NonNull
    private final TVShowListView mView;

    @NonNull
    private final GetTVShowsInteractor mGetTVShowsInteractor;

    @NonNull
    TVShowViewModelMapper mMapper;

    @NonNull
    private final Router mRouter;

    private final int mTVShowsPerPage;

    private int mCurrentPage;

    private boolean mFetchInProcess;

    public TVShowListPresenterImpl(@NonNull TVShowListView view,
                                   @NonNull GetTVShowsInteractor getTVShowsInteractor,
                                   @NonNull TVShowViewModelMapper mapper, @NonNull Router router,
                                   int tvShowsPerPage) {
        mView = view;
        mGetTVShowsInteractor = getTVShowsInteractor;
        mMapper = mapper;
        mRouter = router;
        mTVShowsPerPage = tvShowsPerPage;

        mFetchInProcess = false;
    }


    @Override
    public void start() {
        mView.showProgress();
        cleanLoad(false);
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void onTVShowSelected(TVShowViewModel tvShow) {
        mRouter.openTVShow(tvShow.id());
    }

    @Override
    public void onScrollDown() {
        if (!mFetchInProcess) {
            mView.showProgress();
            loadTVShows(false, true);
        }
    }

    @Override
    public void onForceRefresh() {
        cleanLoad(true);
    }

    private void cleanLoad(boolean forceRefresh) {
        mCurrentPage = 0;
        loadTVShows(forceRefresh, false);
    }


    private void loadTVShows(final boolean forceRefresh, final boolean concatOperation) {
        mFetchInProcess = true;
        final int prevPage = mCurrentPage;

        mGetTVShowsInteractor
                .execute(forceRefresh, mTVShowsPerPage, mCurrentPage)
                .subscribe(new Observer<List<TVShow>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<TVShow> tvShows) {
                mView.hideProgress();
                List<TVShowViewModel> viewModels = mMapper.reverseMap(tvShows);
                if (concatOperation) {
                    mView.addTVShows(viewModels);
                }
                else {
                    mView.showTVShows(viewModels);
                }
                if (tvShows.size() > 0) {
                    mCurrentPage = prevPage + 1;
                }
                mFetchInProcess = false;
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgress();
                mView.showLoadError();
                mFetchInProcess = false;
            }

            @Override
            public void onComplete() {
            }
        });
    }
}
