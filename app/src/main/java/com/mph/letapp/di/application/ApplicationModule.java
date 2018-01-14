package com.mph.letapp.di.application;

import android.content.Context;
import android.content.res.Resources;

import com.mph.letapp.BuildConfig;
import com.mph.letapp.LetApplication;
import com.mph.letapp.di.ForApplication;
import com.mph.letapp.domain.data.model.Models;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.requery.Persistable;
import io.requery.android.sqlite.DatabaseSource;
import io.requery.sql.Configuration;
import io.requery.sql.EntityDataStore;
import io.requery.sql.TableCreationMode;

@Module
public final class ApplicationModule {

    private final LetApplication letApplication;

    public ApplicationModule(final LetApplication letApplication) {
        this.letApplication = letApplication;
    }

    @Provides
    @Singleton
    LetApplication provideLetApplication() {
        return letApplication;
    }

    @Provides
    @Singleton
    @ForApplication
    Context provideContext() {
        return letApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return letApplication.getResources();
    }

    @Provides
    @Singleton
    EntityDataStore<Persistable> provideEntityDataStore() {
        DatabaseSource source = new DatabaseSource(letApplication, Models.DEFAULT, 1);
        if (BuildConfig.DEBUG) {
            source.setTableCreationMode(TableCreationMode.DROP_CREATE);
        }
        Configuration configuration = source.getConfiguration();
        return  new EntityDataStore<>(configuration);
    }


    public interface Exposes {

        LetApplication letApplication();

        @ForApplication
        Context context();

        Resources resources();

        EntityDataStore<Persistable> entityDataStore();
    }
}
