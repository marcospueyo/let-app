package com.mph.letapp.presentation.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class TVShowViewModel {

    public abstract String id();
    public abstract String title();
    public abstract String description();
    public abstract String image();
    public abstract String banner();
    public abstract Double rating();


    public static TVShowViewModel create(String id, String title, String description,
                                             String image, String banner, Double rating) {
        return builder()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setImage(image)
                .setBanner(banner)
                .setRating(rating)
                .build();
    }

    public static Builder builder() {
        return new AutoValue_TVShowViewModel.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setId(String value);
        public abstract Builder setTitle(String value);
        public abstract Builder setDescription(String value);
        public abstract Builder setImage(String value);
        public abstract Builder setBanner(String value);
        public abstract Builder setRating(Double value);

        public abstract TVShowViewModel build();
    }
}
