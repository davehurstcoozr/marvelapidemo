package com.marvelapps.marveldemo.uicomponents;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.utils.FontCache;

/**
 * Created by Dave
 */
public class TextViewAndFont extends TextView {

    public static int FONT_RES_STRING = R.string.font_main;

    public TextViewAndFont(Context context) {
        super(context);
        setDefaultTypeface();
    }

    public TextViewAndFont(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDefaultTypeface();
    }

    public TextViewAndFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setDefaultTypeface();
    }

    public void setDefaultTypeface() {
        if (!this.isInEditMode()) {
            FontCache.setFont(this, getContext().getString(FONT_RES_STRING));
        }

    }

    /*
    @Override
    public void setTypeface(Typeface tf, int style) {
        if (tf!=null) {
            super.setTypeface(tf, style);
            return;
        }

        if (!this.isInEditMode()) {
            if (tf==null) {
                FontCache.setFont(this, getContext().getString(R.string.font_button_default));
            } else {

        }
    }*/
}
