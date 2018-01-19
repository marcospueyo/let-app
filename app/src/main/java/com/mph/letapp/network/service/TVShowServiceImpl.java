package com.mph.letapp.network.service;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mph.letapp.network.model.RestTVShow;
import com.mph.letapp.network.model.TVShowPageResponse;
import com.mph.letapp.network.service.api.TMDBService;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowServiceImpl implements TVShowService {

    @SuppressWarnings("unused")
    public static final String TAG = "TVShowServiceImpl";

    @NonNull
    private final TMDBService mTMDBService;

    @NonNull
    private final String mApiKey;

    @NonNull
    private final String mLanguage;

    public TVShowServiceImpl(@NonNull TMDBService TMDBService, @NonNull String apiKey,
                             @NonNull String language) {
        mTMDBService = TMDBService;
        mApiKey = apiKey;
        mLanguage = language;
    }

    @Override
    public Observable<List<RestTVShow>> getTVShows(final int page, final int tvShowsPerPage) {
        return Observable.create(new ObservableOnSubscribe<List<RestTVShow>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<RestTVShow>> emitter)
                    throws Exception {
                int servicePage = page + 1 /*9*/; //page numbering is 1-based
                Log.d(TAG, "subscribe: servicePage=" + String.valueOf(servicePage));
                Call<TVShowPageResponse> call = mTMDBService.getTVShows(mApiKey, mLanguage,
                        String.valueOf(servicePage));
                call.enqueue(getTVShowListCallback(emitter));
        }
        });
    }

    @Override
    public Observable<List<RestTVShow>> getSimilarTVShows(final String tvShowID, final int page,
                                                          final int tvShowsPerPage) {
        return Observable.create(new ObservableOnSubscribe<List<RestTVShow>>() {
            @Override
            public void subscribe(final ObservableEmitter<List<RestTVShow>> emitter)
                    throws Exception {
                int servicePage = page + 1; //page numbering is 1-based
                Call<TVShowPageResponse> call = mTMDBService.getSimilarTVShows(tvShowID, mApiKey,
                        mLanguage, String.valueOf(servicePage));
                call.enqueue(getTVShowListCallback(emitter));
            }
        });
    }

    private Callback<TVShowPageResponse> getTVShowListCallback(
            final ObservableEmitter<List<RestTVShow>> emitter) {
        return new Callback<TVShowPageResponse>() {
            @Override
            public void onResponse(Call<TVShowPageResponse> call,
                                   Response<TVShowPageResponse> response) {
                if (response.isSuccessful()) {
                    TVShowPageResponse tvShowPageResponse = response.body();
                    List<RestTVShow> list;
                    if (tvShowPageResponse != null) {
                        list = tvShowPageResponse.getTvshowList();
                    }
                    else {
                        list = Collections.emptyList();
                    }
                    emitter.onNext(list);
                    emitter.onComplete();
                } else {
                    handleFetchFailed(emitter);
                }
            }

            @Override
            public void onFailure(Call<TVShowPageResponse> call, Throwable t) {
                handleFetchFailed(emitter);
            }

        };
    }

    private void handleFetchFailed(final ObservableEmitter<List<RestTVShow>> emitter) {
        emitter.onError(new Throwable("TMDB service fetch failed"));
    }
}
