package com.marvelapps.marveldemo.marvelapi;


import android.util.Log;

import com.marvelapps.marveldemo.marvelapi.callbacks.CallbackGetComic;
import com.marvelapps.marveldemo.marvelapi.callbacks.CallbackGetComics;
import com.marvelapps.marveldemo.marvelapi.models.Comic;
import com.marvelapps.marveldemo.marvelapi.responses.ComicsResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dave on 11/21/2016.
 *
 * Currently for speed we are using http://arnaudpiroelle.github.io/marvel-api/
 * But we can also easily use RetroFit and write some classes for ourselves to access just the parts of the API we need
 *
 * Sample: https://gateway.marvel.com:443/v1/public/comics?apikey=433fb0a262118e0c2b64944db5b761b8
 */

public class MarvelAPIHelper {

    public MarvelAPIService marvelAPIService;
    public String apiKeyPublic;
    public String apiKeyPrivate;
    public long timestamp;
    public String hash;

    // Singleton -----------------------
    private volatile static MarvelAPIHelper instance;

    /** Returns singleton class instance */
    public static MarvelAPIHelper getInstance() {
        if (instance == null) {
            synchronized (MarvelAPIHelper.class) {
                if (instance == null) {
                    instance = new MarvelAPIHelper();
                }
            }
        }
        return instance;
    }

    // -----------------------------------

    public void initAPI(String apiKeyPublic, String apiKeyPrivate) {
        this.apiKeyPublic = apiKeyPublic;
        this.apiKeyPrivate = apiKeyPrivate;
        refreshHash();  // generate hash using timestamp and API keys - See https://developer.marvel.com/documentation/authorization


    }

    public void refreshHash() {
        this.timestamp = new Date().getTime();
        this.hash = MarvelHashGenerator.generate(String.valueOf(timestamp), apiKeyPrivate, apiKeyPublic);
    }
    public MarvelAPIService createServiceIfNeeded() {

        if (marvelAPIService==null) {
            marvelAPIService = MarvelAPIService.retrofit.create(MarvelAPIService.class);
        }
        return marvelAPIService;

    }


    public void getComicsAsync(final CallbackGetComics callback) {
        createServiceIfNeeded();
        refreshHash();
        //-----
       Call<ComicsResponse> call = marvelAPIService.getComics(apiKeyPublic, hash, timestamp);
        call.enqueue(new Callback<ComicsResponse>() {
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {

                if (!response.isSuccessful()) {
                    String s = "";
                    try {
                        s = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.w("APP","getComics PROBLEM "+s);
                    if (callback!=null) {
                        callback.onResult(false, null, s);
                    }
                    return;
                }

                try {
                    ComicsResponse comicsResponse = response.body();
                    Log.d("APP","getComics OK");
                    //SUCCESS
                    if (callback!=null) {
                        callback.onResult(true, comicsResponse.getData().getResults(), null);
                    }
                }
                catch (Exception err) {
                    Log.w("APP","getComics PROBLEM "+err.getLocalizedMessage());
                    if (callback!=null) {
                        callback.onResult(false, null, err.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Log.w("APP","getComics FAILED");
                if (callback!=null) {
                    callback.onResult(false, null, t.getLocalizedMessage());
                }
            }
        });
    }



    public void getComicByIDAsync(long id, final CallbackGetComic callback) {
        createServiceIfNeeded();
        refreshHash();
        //-----
        Call<ComicsResponse> call = marvelAPIService.getComicByID(id, apiKeyPublic, hash, timestamp);
        call.enqueue(new Callback<ComicsResponse>() {
            @Override
            public void onResponse(Call<ComicsResponse> call, Response<ComicsResponse> response) {

                if (!response.isSuccessful()) {
                    String s = "";
                    try {
                        s = response.errorBody().string();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.w("APP","getComicsByID PROBLEM "+s);
                    if (callback!=null) {
                        callback.onResult(false, null, s);
                    }
                    return;
                }

                try {
                    ComicsResponse comicsResponse = response.body();
                    ArrayList<Comic> results = comicsResponse.getData().getResults();
                    if (results!=null && results.size()>0) {
                        Log.d("APP","getComicsByID OK");
                        //SUCCESS
                        if (callback!=null) {
                            callback.onResult(true, results.get(0), null);
                        }
                    }
                    else {
                        Log.d("APP","getComicsByID NOT FOUND");
                        //SUCCESS
                        if (callback!=null) {
                            callback.onResult(true, null, "Not Found");
                        }
                    }
                    
                }
                catch (Exception err) {
                    Log.w("APP","getComicsByID PROBLEM "+err.getLocalizedMessage());
                    if (callback!=null) {
                        callback.onResult(false, null, err.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ComicsResponse> call, Throwable t) {
                Log.w("APP","getComicsByID FAILED");
                if (callback!=null) {
                    callback.onResult(false, null, t.getLocalizedMessage());
                }
            }
        });
    }


}
