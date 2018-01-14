package com.mph.letapp.di.application.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public final class ThreadingModule {

    static final String MAIN_SCHEDULER = "main_scheduler";

    static final String BACKGROUND_SCHEDULER = "background_scheduler";

    @Provides
    @Singleton
    @Named(MAIN_SCHEDULER)
    public Scheduler provideMainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    @Named(BACKGROUND_SCHEDULER)
    public Scheduler provideBackgroundScheduler() {
        return Schedulers.io();
    }

    public interface Exposes {

        @Named(MAIN_SCHEDULER)
        Scheduler mainScheduler();

        @Named(BACKGROUND_SCHEDULER)
        Scheduler backgroundScheduler();
    }
}
