package com.marvelapps.marveldemo.marvelapi.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.marvelapps.marveldemo.constants.Consts;
import com.marvelapps.marveldemo.utils.Common;

import java.util.ArrayList;

/**
 * Created by Dave on 11/22/2016.
 */

public class Comic {

    @SerializedName("id")
    @Expose
    public long id;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("format")
    @Expose
    public String format;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("pageCount")
    @Expose
    public int pageCount;

    @SerializedName("thumbnail")
    @Expose
    public MarvelThumbnail thumbnail;

    @SerializedName("images")
    @Expose
    public ArrayList<MarvelImage> images;



    //Helper methods

    /**
     * Get first thumbnail of the comic
     * @return
     */
    public String getThumbnailImageUrl() {
        //https://developer.marvel.com/documentation/images
        if (thumbnail==null || Common.isEmpty(thumbnail.path)) return null;
        return thumbnail.path + "/" + Consts.MARVELAPI_THUMB_IMAGE_TYPE + "." + thumbnail.extension;
    }

    /**
     * Get first thumbnail of the comic
     * @return
     */
    public String getLargeImageUrl() {
        //https://developer.marvel.com/documentation/images
        if (images==null || images.size()==0) return null;
        MarvelImage img = images.get(0);
        return img.path + "/" + Consts.MARVELAPI_THUMB_IMAGE_TYPE + "." + img.extension;
    }


}
