package com.mph.letapp;

import com.mph.letapp.domain.data.TVShowRepository;
import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.domain.interactor.GetTVShowsInteractorImpl;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class GetTVShowsInteractorImplTest {

    private TVShowRepository mTVShowRepository;

    private Scheduler mMainScheduler;

    private Scheduler mBackgroundScheduler;

    private GetTVShowsInteractorImpl mGetRepositoriesInteractor;

    private TestTVShowsObserver mTestTVShowsObserver;

    @Before
    public void setUp() throws Exception {
        mTVShowRepository = mock(TVShowRepository.class);
        mTestTVShowsObserver = new TestTVShowsObserver();
        mMainScheduler = Schedulers.trampoline();
        mBackgroundScheduler = Schedulers.trampoline();
        mGetRepositoriesInteractor = new GetTVShowsInteractorImpl(mTVShowRepository,
                mMainScheduler, mBackgroundScheduler);

        when(mTVShowRepository.getTVShowPage(anyInt(), anyInt()))
                .thenReturn(new Observable<List<TVShow>>() {
                    @Override
                    protected void subscribeActual(Observer<? super List<TVShow>> observer) {
                        observer.onNext(new ArrayList<TVShow>());
                        observer.onComplete();
                    }
                });

        when(mTVShowRepository.clearTVShows()).thenReturn(Completable.complete());
        when(mTVShowRepository.fetchRemoteTVShows(anyInt(), anyInt())).thenReturn(Completable.complete());

    }

    @Test
    public void shouldDeleteRepos() throws Exception {
        when(mTVShowRepository.localTVShowCount()).thenReturn(0);

        mGetRepositoriesInteractor.execute(mTestTVShowsObserver, true, 10,
                0);
        verify(mTVShowRepository).clearTVShows();
    }

    @Test
    public void shouldLoadFromRepositoryOnRefresh() throws Exception {
        when(mTVShowRepository.localTVShowCount()).thenReturn(0);

        int elementsPerPage = 10;
        int page = 0;

        mGetRepositoriesInteractor.execute(mTestTVShowsObserver, true, elementsPerPage,
                page);
        verify(mTVShowRepository, times(1))
                .fetchRemoteTVShows(eq(page), eq(elementsPerPage));
    }

    @Test
    public void shouldLoadLocalRepositories() throws Exception {
        when(mTVShowRepository.localTVShowCount()).thenReturn(500);

        int elementsPerPage = 10;
        int page = 2;

        mGetRepositoriesInteractor.execute(mTestTVShowsObserver, false, elementsPerPage,
                page);
        verify(mTVShowRepository, never()).fetchRemoteTVShows(anyInt(), anyInt());
        verify(mTVShowRepository, times(1))
                .getTVShowPage(eq(page), eq(elementsPerPage));
    }

    private final class TestTVShowsObserver extends DisposableObserver<List<TVShow>> {

        @Override
        public void onNext(List<TVShow> tvShows) {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onComplete() {

        }
    }
}
