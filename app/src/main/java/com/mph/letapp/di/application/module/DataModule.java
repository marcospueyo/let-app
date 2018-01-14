package com.mph.letapp.di.application.module;

import com.mph.letapp.domain.data.model.TVShowDao;
import com.mph.letapp.domain.data.model.TVShowDaoImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.sql.EntityDataStore;


@Module
public final class DataModule {

    @Provides
    @Singleton
    TVShowDao provideTVShowDao(EntityDataStore<Persistable> dataStore) {
        return new TVShowDaoImpl(dataStore);
    }

    public interface Exposes {

        TVShowDao tvShowDao();

    }
}
