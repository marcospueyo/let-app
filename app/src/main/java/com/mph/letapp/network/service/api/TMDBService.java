package com.mph.letapp.network.service.api;

import com.mph.letapp.network.model.TVShowPageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDBService {

    @GET("tv/popular")
    Call<TVShowPageResponse> getTVShows(@Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("page") String page);

    @GET("tv/{show_id}/similar")
    Call<TVShowPageResponse> getSimilarTVShows(@Path("show_id") String tvShowID,
                                               @Query("api_key") String apiKey,
                                               @Query("language") String language,
                                               @Query("page") String page);
}
