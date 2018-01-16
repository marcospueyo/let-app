package com.mph.letapp;

import com.mph.letapp.di.application.module.NetworkModule;
import com.mph.letapp.domain.data.TVShowRepositoryImpl;
import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.domain.data.model.TVShowDao;
import com.mph.letapp.network.mapper.RestTVShowMapper;
import com.mph.letapp.network.model.RestTVShow;
import com.mph.letapp.network.service.TVShowService;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.TestScheduler;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public final class TVShowRepositoryImplTest {

    @SuppressWarnings("unused")
    private static final String TAG = TVShowRepositoryImplTest.class.getSimpleName();

    private TVShowDao mTVShowDao;

    private RestTVShowMapper mMapper;

    private TVShowRepositoryImpl mTVShowRepositoryImpl;

    private TVShowService mTVShowService;

    private Scheduler mBackgroundScheduler;

    private int page;

    private int elementsPerPage;

    private TestObserver<List<TVShow>> testObserver;


    @Before
    public void setUp() throws Exception {
        mTVShowDao = mock(TVShowDao.class);
        mTVShowService = mock(TVShowService.class);
        mMapper = new RestTVShowMapper(NetworkModule.POSTER_BASE_URL,
                NetworkModule.BACKDROP_BASE_URL);
        mBackgroundScheduler = new TestScheduler();
        page = 1;
        elementsPerPage = 10;
        testObserver = new TestObserver<>();

        mTVShowRepositoryImpl = new TVShowRepositoryImpl(mTVShowService, mTVShowDao, mMapper,
                mBackgroundScheduler);
    }

    @Test
    public void properlyLoadLocalTVShows() throws Exception {
        final List<TVShow> mockTVShowList = getMockTVShowList(10);

        when(mTVShowDao.getTVShows(page, elementsPerPage))
                .thenReturn(new Observable<List<TVShow>>() {
                    @Override
                    protected void subscribeActual(Observer<? super List<TVShow>> observer) {
                        observer.onNext(mockTVShowList);
                        observer.onComplete();
                    }
                });

        mTVShowRepositoryImpl.getTVShowPage(page, elementsPerPage).subscribe(testObserver);

        verify(mTVShowDao, times(1))
                .getTVShows(eq(page), eq(elementsPerPage));

        testObserver.assertComplete();
        testObserver.assertValue(mockTVShowList);
    }

    @Test
    public void properlyFetchRemoteTVShows() throws Exception {
        final int nElems = 25;
        final List<RestTVShow> restTVShowList = getMockRemoteTVShowList(nElems);
        final List<TVShow> tvShowList = mMapper.map(restTVShowList);

        when(mTVShowService.getTVShows(page, elementsPerPage))
                .thenReturn(new Observable<List<RestTVShow>>() {
            @Override
            protected void subscribeActual(Observer<? super List<RestTVShow>> observer) {
                observer.onNext(restTVShowList);
                observer.onComplete();
            }
        });

        when(mTVShowDao.getTVShows(page, elementsPerPage)).thenReturn(
                new Observable<List<TVShow>>() {
            @Override
            protected void subscribeActual(Observer<? super List<TVShow>> observer) {
                observer.onNext(tvShowList);
                observer.onComplete();
            }
        });

        mTVShowRepositoryImpl.fetchRemoteTVShows(page, elementsPerPage).subscribe(testObserver);

        verify(mTVShowService, times(1))
                .getTVShows(page, elementsPerPage);
        verify(mTVShowDao, times(1)).insertTVShows(tvShowList);

        testObserver.assertComplete();
    }

    @Test
    public void shouldNotifyLoadError() throws Exception {

    }

    private List<TVShow> getMockTVShowList(int nElems) {
        List<TVShow> list = new ArrayList<>();
        for (int i = 0; i < nElems; i++) {
            TVShow tvShow = new TVShow();
            tvShow.setId("id-" + String.valueOf(i));
            list.add(tvShow);
        }
        return list;
    }

    private List<RestTVShow> getMockRemoteTVShowList(int nElems) {
        List<RestTVShow> list = new ArrayList<>();
        for (int i = 0; i < nElems; i++) {
            RestTVShow repository = new RestTVShow();
            repository.setId("id-" + String.valueOf(i));
            list.add(repository);
        }
        return list;
    }

}


