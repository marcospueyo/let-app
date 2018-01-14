package com.mph.letapp.domain.data.model;

import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


public class TVShowDaoImpl implements TVShowDao {

    @NonNull
    private EntityDataStore<Persistable> mDataStore;

    public TVShowDaoImpl(@NonNull EntityDataStore<Persistable> mDataStore) {
        this.mDataStore = mDataStore;
    }

    @Override
    public Observable<List<TVShow>> getTVShows(final int page, final int elementsPerPage) {
        return Observable.create(new ObservableOnSubscribe<List<TVShow>>() {
            @Override
            public void subscribe(ObservableEmitter<List<TVShow>> e) throws Exception {
                e.onNext(mDataStore
                            .select(TVShow.class)
                            .orderBy(TVShow.POPULARITY.desc())
                            .limit(elementsPerPage)
                            .offset(page * elementsPerPage)
                            .get()
                            .toList());
                e.onComplete();
            }
        });
    }



    @Override
    public TVShow getTVShow(String id) {
        return mDataStore
                .select(TVShow.class)
                .where(TVShow.ID.eq(id))
                .get()
                .firstOrNull();
    }

    @Override
    public void deleteAllTVShows() {
        mDataStore.delete(TVShow.class).get().value();
    }

    @Override
    public void deleteTVShow(String id) {
        mDataStore
                .delete(TVShow.class)
                .where(TVShow.ID.eq(id))
                .get()
                .value();
    }

    @Override
    public void insertTVShow(TVShow tvShow) {
        mDataStore.insert(tvShow);
    }

    @Override
    public void insertTVShows(Iterable<TVShow> tvShows) {
        for (TVShow repository : tvShows) {
            deleteTVShow(repository.getId());
        }
        mDataStore.insert(tvShows);
    }

    @Override
    public int getTVShowCount() {
        return mDataStore.count(TVShow.class).get().value();
    }
}
