package com.marvelapps.marveldemo.marvelapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Dave on 11/21/2016.
 */

public class MarvelThumbnail {

    @SerializedName("path")
    @Expose
    public String path;

    @SerializedName("extension")
    @Expose
    public String extension;
}
