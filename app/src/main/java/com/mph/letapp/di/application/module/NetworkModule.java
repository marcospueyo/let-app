package com.mph.letapp.di.application.module;

import com.mph.letapp.network.service.api.TMDBService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public final class NetworkModule {

    private static final String API_URL = "https://api.themoviedb.org/3/";

    public static final String BACKDROP_BASE_URL = "https://image.tmdb.org/t/p/w300";

    public static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w154";

    @Provides
    @Singleton
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    TMDBService provideTMDBService(Retrofit retrofit) {
        return retrofit.create(TMDBService.class);
    }

    public interface Exposes {
        TMDBService tmdbService();
    }
}
