package com.mph.letapp.domain.data;

import android.support.annotation.NonNull;

import com.mph.letapp.domain.data.model.TVShowDao;
import com.mph.letapp.domain.data.model.TVShow;
import com.mph.letapp.network.mapper.RestTVShowMapper;
import com.mph.letapp.network.service.TVShowService;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;


public class TVShowRepositoryImpl implements TVShowRepository {

    @NonNull
    private final TVShowService mTVShowService;

    @NonNull
    private TVShowDao mTVShowDao;

    @NonNull
    private final RestTVShowMapper mMapper;

    @NonNull
    private final Scheduler mBackgroundThread;

    public TVShowRepositoryImpl(@NonNull TVShowService TVShowService,
                                @NonNull TVShowDao TVShowDao,
                                @NonNull RestTVShowMapper mapper,
                                @NonNull Scheduler backgroundThread) {
        mTVShowService = TVShowService;
        mTVShowDao = TVShowDao;
        mMapper = mapper;
        mBackgroundThread = backgroundThread;
    }

    private Function<List<TVShow>, List<TVShow>> saveFetchedEntities() {
        return new Function<List<TVShow>, List<TVShow>>() {
            @Override
            public List<TVShow> apply(List<TVShow> tvShows) throws Exception {
                saveFetchedEntities(tvShows);
                return tvShows;
            }
        };
    }

    private Observable<List<TVShow>> fetchFromRemoteStore(final int page, final int maxCount) {
        return mTVShowService
                .getTVShows(page, maxCount)
                .map(mMapper.map());
    }

    @Override
    public Observable<List<TVShow>> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable<List<TVShow>> getTVShowPage(int page, int maxCount) {
        return getLocalEntitiesObservable(page, maxCount);
    }

    @Override
    public Observable<List<TVShow>> getSimilarTVShows(String tvShowID, int page, int maxCount) {
        return mTVShowService
                .getSimilarTVShows(tvShowID, page, maxCount)
                .map(mMapper.map())
                .map(saveFetchedEntities());
    }

    @Override
    public Completable fetchRemoteTVShows(final int page, final int maxCount) {
        return Completable.fromObservable(
                fetchFromRemoteStore(page, maxCount)
                        .map(saveFetchedEntities()));
    }

    @Override
    public Completable clearTVShows() {
        return Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                    deleteAllEntities();
            }
        });
    }

    @Override
    public int localTVShowCount() {
        return mTVShowDao.getTVShowCount();
    }

    private Observable<List<TVShow>> getLocalEntitiesObservable(final int page,
                                                                final int elementsPerPage) {
        return mTVShowDao.getTVShows(page, elementsPerPage);
    }

    public Observable<TVShow> getTVShow(String id) {
        return mTVShowDao.getTVShow(id);
    }

    private void saveFetchedEntities(Iterable<TVShow> tvShows) {
        mTVShowDao.insertTVShows(tvShows);
    }

    private void deleteAllEntities() {
        mTVShowDao.deleteAllTVShows();
    }
}
