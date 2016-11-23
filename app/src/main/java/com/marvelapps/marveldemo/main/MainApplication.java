package com.marvelapps.marveldemo.main;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.marvelapi.MarvelAPIHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Dave on 11/21/2016.
 */

public class MainApplication extends Application {

    private static Context mContext;

    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        //---

        initUniversalImageLoader();
        initMarvelAPI();

    }

    private void initUniversalImageLoader() {

        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(displayImageOptions)
                .build();



        ImageLoader.getInstance().init(config);
        //In views use: ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
        //imageLoader.displayImage(imageUri, imageView);
    }

    private void initMarvelAPI() {
        Log.d("APP","initMarvelAPI");
        MarvelAPIHelper.getInstance().initAPI(getResources().getString(R.string.marvel_api_key_public), getResources().getString(R.string.marvel_api_key_private));
    }
}
