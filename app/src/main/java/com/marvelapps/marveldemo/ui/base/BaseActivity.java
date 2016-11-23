package com.marvelapps.marveldemo.ui.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.marvelapps.marveldemo.utils.UITools;

/**
 * Base class for all of our activities. The purpose here is for easy access to common functions and keep our activity/fragment code
 * clean and readable. I.e. show toasts, popups, init methods etc, friendlier navigation methods etc.
 *
 * It also has helper functions to load our fragments
 *
 * By default it's good practice to create a fragment for each view inside the activity so every UI element is easily re-used and tablet/layout compatible
 *
 * We can also apply things to all activities if we want.
 *
 * Created by Dave on 11/21/2016.
 *
 *
 */

public class BaseActivity extends AppCompatActivity {

    public Fragment fragment;

    public UITools tools;
    public View view;

    /**
     * Helper method to create the main fragment
     * @param savedInstanceState
     * @param layoutResourceActivity
     * @param fragmentContainerID
     * @param fragment
     */
    protected void create(Bundle savedInstanceState, int layoutResourceActivity, int fragmentContainerID, Fragment fragment)
    {
        setContentView(layoutResourceActivity);
        this.fragment = fragment;
        if (fragmentContainerID!=-1 && fragment!=null && savedInstanceState == null) {
            getFragmentManager().beginTransaction().replace(fragmentContainerID, fragment).commit();

        }
        View view = findViewById(android.R.id.content);
        tools = new UITools(view);
        initUI();
    }

    /** Init our UI here - override in the activity class */
    public void initUI() {
        //To override
    }

    /** Our main click handler - it's nice to keep all of the main click handlers together and easily readable */
    public void onClick(View view) {
        //To override
    }

    public void log(String s) {
        //By having a helper method: we can apply the tag in one go and turn off all logs easily, it's also nicer than Log.d(TAG,...)
        Log.d("MVL",s);
    }

}
