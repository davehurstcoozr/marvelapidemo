package com.marvelapps.marveldemo.marvelapi.callbacks;

import com.marvelapps.marveldemo.marvelapi.models.Comic;

import java.util.ArrayList;

/**
 * Created by Dave on 11/22/2016.
 */

public interface CallbackGetComic {

    void onResult(boolean ok, Comic comic, String errorMessage);
}
