package com.marvelapps.marveldemo.marvelapi.callbacks;

import com.marvelapps.marveldemo.marvelapi.models.Comic;
import com.marvelapps.marveldemo.marvelapi.responses.ComicsResponse;

import java.util.ArrayList;

/**
 * Created by Dave on 11/22/2016.
 */

public interface CallbackGetComics {

    void onResult(boolean ok, ArrayList<Comic> comicsList, String errorMessage);
}
