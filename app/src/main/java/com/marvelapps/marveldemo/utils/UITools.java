package com.marvelapps.marveldemo.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.androidquery.AQuery;

/**
 * Created by Dave on 2015-07-10.
 */
public class UITools extends AQuery {

    public UITools(Activity act) {
        super(act);
    }

    public UITools(View view) {
        super(view);
    }

    public UITools(Context context) {
        super(context);
    }

    public UITools(Activity act, View root){
        super(act, root);
    }




}
