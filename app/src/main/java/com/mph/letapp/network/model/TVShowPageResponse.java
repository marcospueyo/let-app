package com.mph.letapp.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowPageResponse {

    @SerializedName("results")
    private List<RestTVShow> tvshowList;

    public TVShowPageResponse() {
    }

    public List<RestTVShow> getTvshowList() {
        return tvshowList;
    }

    public void setTvshowList(List<RestTVShow> tvshowList) {
        this.tvshowList = tvshowList;
    }
}
