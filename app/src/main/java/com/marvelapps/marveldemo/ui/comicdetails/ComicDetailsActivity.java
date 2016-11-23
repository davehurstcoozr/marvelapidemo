package com.marvelapps.marveldemo.ui.comicdetails;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.ui.base.BaseActivity;
import com.marvelapps.marveldemo.ui.main.MainFragment;

/**
 * Written by Dave Hurst Nov 2016 - For http://www.aequilibrium.ca/
 *
 * */

public class ComicDetailsActivity extends BaseActivity {

    static final int LAYOUT_ACTIVITY = R.layout.comicdetails_activity;
    static final int FRAGMENT_CONTENT_CONTAINER = R.id.content_container;

    public static final String EXTRA_COMIC_ID = "comicID";
    public static final String EXTRA_COMIC_TITLE = "comicTitle";


    /**
     * Start() is a nice clean and readable way to start our activities with one simple line of code. We can also then easily read in intent extras via nice method params
     * */
    public static Intent start(Activity activityFrom, long comicID, String comicTitle) {
        Intent intent = new Intent(activityFrom, ComicDetailsActivity.class);
        intent.putExtra(EXTRA_COMIC_ID, comicID);
        intent.putExtra(EXTRA_COMIC_TITLE, comicTitle);
        activityFrom.startActivity(intent);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState,LAYOUT_ACTIVITY, FRAGMENT_CONTENT_CONTAINER, new ComicDetailsFragment());
        setContentView(LAYOUT_ACTIVITY);
    }


    /**
     * Init the UI
     */
    @Override
    public void initUI() {
        super.initUI();

        //Add button clicks and UI init here

    }



}
