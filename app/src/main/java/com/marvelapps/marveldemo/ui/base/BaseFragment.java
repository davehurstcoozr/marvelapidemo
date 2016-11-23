package com.marvelapps.marveldemo.ui.base;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marvelapps.marveldemo.utils.UITools;

/**
 * Created by Dave on 11/21/2016.
 */

public class BaseFragment extends Fragment implements View.OnClickListener {

    // Common Fragment Properties ---------

    /** Our view */
    public View view;
    /** UI Tools is a great Android helper like JQuery to keep our code clean (less findViewByID) */
    public UITools tools;


    // Methods -----------------------------

    public View inflateAndInitView(int fragementLayoutRes, LayoutInflater inflater, ViewGroup container) {
        this.view = inflater.inflate(fragementLayoutRes, container, false);
        //If needed - deal with savedInstanceStateHere
        //If needed - String param = getActivityExtra("mykey");
        initTools();
        initUI();
        return view;
    }

    public void initTools() {
        this.tools = new UITools(this.view);
    }

    /** Init our UI here - override in the activity class */
    public void initUI() {
        // To override
    }

    // Navigations ----

    public void navigateBack() {    //Makes code a little easier to read and handy if we want to log all navigation events to a file or analytics
        getActivity().finish(); //or override if needed
    }
    public void navigateFinish() {  //Makes code a little easier to read and handy if we want to log all navigation events to a file or analytics
        getActivity().finish(); //or override if needed
    }



    // Clicks ----


    public void onClick(View view) {

        Log.d("ICE", "onClicked "+view.getId());
        // To override
        /*
         switch(view.getId())
        {
            case R.id.btn_next:
                // Code for click
                break;
        }
         */

    }

    public String getActivityExtra(String key) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            return extras.getString(key);
        }
        else {
            return null;
        }
    }

    public long getActivityExtraLong(String key) {
        Bundle extras = getActivity().getIntent().getExtras();
        if (extras != null) {
            return extras.getLong(key);
        }
        else {
            return -1;
        }
    }

    //--- Toasts ------------------------------

    public void showToastQuick(String message)
    {
        if (getActivity()==null || getActivity().getApplicationContext()==null) return;
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
    public void showToastLong(String message)
    {
        if (getActivity()==null || getActivity().getApplicationContext()==null) return;
        Toast.makeText(getActivity().getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }


}
