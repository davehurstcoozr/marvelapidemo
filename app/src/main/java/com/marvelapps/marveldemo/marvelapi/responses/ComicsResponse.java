package com.marvelapps.marveldemo.marvelapi.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Dave on 11/22/2016.
 */

public class ComicsResponse {


    @SerializedName("code")
    private int code;
    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private ComicsResponseData data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ComicsResponseData getData() {
        return data;
    }

    public void setStatus(ComicsResponseData data) {
        this.data = data;
    }
}
