package com.marvelapps.marveldemo.marvelapi.responses;

import com.google.gson.annotations.SerializedName;
import com.marvelapps.marveldemo.marvelapi.models.Comic;

import java.util.ArrayList;

/**
 * Created by Dave on 11/22/2016.
 */

public class ComicsResponseData {


    @SerializedName("count")
    private int count;
    @SerializedName("results")
    private ArrayList<Comic> results;

    public int getCount() {
        return count;
    }
    public void setCount(int code) {
        this.count = code;
    }

    public ArrayList<Comic> getResults() {
        return results;
    }
    public void setResults(ArrayList<Comic> value) {
        this.results = value;
    }
}
