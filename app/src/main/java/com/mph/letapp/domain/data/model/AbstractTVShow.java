package com.mph.letapp.domain.data.model;

import io.requery.Entity;
import io.requery.Key;

@Entity
public abstract class AbstractTVShow {

    @Key
    String id;

    String name;

    String originalName;

    String description;

    String backdrop;

    String poster;

    Double rating;

    Double popularity;

}
