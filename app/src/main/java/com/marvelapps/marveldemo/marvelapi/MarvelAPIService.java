package com.marvelapps.marveldemo.marvelapi;

import android.database.Observable;

import com.marvelapps.marveldemo.marvelapi.responses.ComicsResponse;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;



/**
 * Created by Dave on 11/22/2016.
 */

public interface MarvelAPIService {

    String ID = "id";
    String NAME = "name";
    String API_KEY = "apikey";
    String HASH = "hash";
    String TIMESTAMP = "ts";

    //-------------------------------------------------------------

    /** Useful to keep this also in the same interface to avoid complicating things - it's easy to find and change (later move the value to strings res) */
    public static final Retrofit retrofit = new Retrofit.Builder()
            // http://gateway.marvel.com:80/v1/public/comics?apikey=PUBLIC_API_KEY&hash=???&ts=???
            .baseUrl("http://gateway.marvel.com:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //-------------------------------------------------------------


    @GET("v1/public/comics")
    Call<ComicsResponse> getComics(
            //@Query(NAME) String query,
            @Query(API_KEY) String publicKey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) long timestamp
    );

    @GET("v1/public/comics/{id}")
    Call<ComicsResponse> getComicByID(
            //@Query(NAME) String query,
            @Path(ID) long id,
            @Query(API_KEY) String publicKey,
            @Query(HASH) String hash,
            @Query(TIMESTAMP) long timestamp
    );


}
