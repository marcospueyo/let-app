package com.mph.letapp.network.model;

import com.google.gson.annotations.SerializedName;

public class RestTVShow {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("backdrop_path")
    private String backdropPath;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private Double voteAverage;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private Double popularity;

    public RestTVShow() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview == null ? "" : overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }
}
