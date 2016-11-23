package com.marvelapps.marveldemo.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.Hashtable;


public class FontCache {

    private static Hashtable<String, Typeface> fontCache = new Hashtable<String, Typeface>();

    public static void setFont(Button view, String name) { setFont(view, name, "fonts"); }
    public static void setFont(Button view, String name, String customSubfolder) {
        Typeface font = get(customSubfolder+"/"+name, view.getContext());
        if (font!=null) {
            view.setTypeface(font);
        }
    }
    public static void setFont(TextView view, String name) { setFont(view, name, "fonts"); }
    public static void setFont(TextView view, String name, String customSubfolder) {
        Typeface font = get(customSubfolder+"/"+name, view.getContext());
        if (font!=null) {
            view.setTypeface(font);
        }
    }

    public static Typeface get(String name, Context context) {

        try {
            if (fontCache.containsKey(name)){
                return fontCache.get(name);
            }
            else {
                Log.d("ICE", "FontCache - CREATE NEW TYPEFACE: "+name);
                Typeface tf = Typeface.createFromAsset(context.getAssets(), name);
                fontCache.put(name, tf);
                return tf;
            }
        }
        catch (Exception err) {
            err.printStackTrace();
            return null;
        }


    }
}
