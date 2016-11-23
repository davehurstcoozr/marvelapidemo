package com.marvelapps.marveldemo.ui.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.ui.base.BaseActivity;

/**
 * Written by Dave Hurst Nov 2016 - For http://www.aequilibrium.ca/
 *
 * */

public class MainActivity extends BaseActivity {

    static final int LAYOUT_ACTIVITY = R.layout.main_activity;
    static final int FRAGMENT_CONTENT_CONTAINER = R.id.content_container;


    /**
     * Start() is a nice clean and readable way to start our activities with one simple line of code. We can also then easily read in intent extras via nice method params
     * */
    public static Intent start(Activity activityFrom) {
        Intent intent = new Intent(activityFrom, MainActivity.class);
        //intent.putExtra("itemID", itemID); //Add params for the activity here if needed
        activityFrom.startActivity(intent);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        create(savedInstanceState,LAYOUT_ACTIVITY, FRAGMENT_CONTENT_CONTAINER, new MainFragment());
        setContentView(LAYOUT_ACTIVITY);
    }


    /**
     * Init the UI
     */
    @Override
    public void initUI() {
        super.initUI();

        //Add button clicks and UI init here


        animateBackgroundIn();
    }

    private void animateBackgroundIn() {
        //YoYo.with(Techniques.SlideInDown)
        //        .duration(10000)
        //        .playOn(findViewById(R.id.img_background));
    }


}
