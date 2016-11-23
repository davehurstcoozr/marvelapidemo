package com.marvelapps.marveldemo.ui.comicdetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidquery.AQuery;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.databinding.ComicdetailsFragmentBinding;
import com.marvelapps.marveldemo.marvelapi.MarvelAPIHelper;
import com.marvelapps.marveldemo.marvelapi.callbacks.CallbackGetComic;
import com.marvelapps.marveldemo.marvelapi.models.Comic;
import com.marvelapps.marveldemo.ui.base.BaseFragment;
import com.nostra13.universalimageloader.core.ImageLoader;


/**
 * Created by Dave on 11/21/2016.
 */

public class ComicDetailsFragment extends BaseFragment {

    static final int LAYOUT_FRAGMENT = R.layout.comicdetails_fragment;

    static private MarvelAPIHelper marvelAPIHelper = MarvelAPIHelper.getInstance();
    static private ImageLoader imageLoader = ImageLoader.getInstance();

    // Properties ----

    ComicdetailsFragmentBinding binding;    //data binding

    public Comic comic;
    public long comicID;



    // ----------------

    public ComicDetailsFragment() {}

    //---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflateAndInitView(LAYOUT_FRAGMENT, inflater, container);

        //Custom init for data binding
        binding = DataBindingUtil.inflate(inflater, LAYOUT_FRAGMENT, container, false);
        this.view = binding.getRoot();
        //---
        initTools();
        initUI();
        return view;

    }

    /** Init UI for this fragment */
    @Override
    public void initUI() {
        super.initUI();

        //Add button clicks and UI init here
        tools.id(R.id.btn_back).clicked(this);

        //----

        comic = new Comic();   //Model for us to use for easy data binding
        //Get params passed in from activity extras
        comicID = getActivityExtraLong(ComicDetailsActivity.EXTRA_COMIC_ID);
        //comicModel.title = getActivityExtra(ComicDetailsActivity.EXTRA_COMIC_TITLE);

        //showToastQuick("Details of comic: "+comicID+" "+comicTitle);

        //Load the comic from the API again to make sure we have the latest details, and it also allows us to make sure that this activity can be run independently
        loadComicFromAPI();

    }

    public void setComicAndRefreshUI(Comic oComic) {
        comic = oComic;
        binding.setComic(comic);    //data binding will refresh most of the UI for us

        if (comic==null) return;

        //Refresh the image
        String imageUrl = comic.getLargeImageUrl();
        tools.id(R.id.img_background).image(imageUrl,false,false,0,0,null,AQuery.FADE_IN);
        tools.id(R.id.img_main).image(imageUrl,false,false,0,0,null,AQuery.FADE_IN);

        //Animations
        YoYo.with(Techniques.SlideInDown)
                .duration(1000)
                .playOn(this.view);


    }


    // Marvel Comics API - Get the Comic Details ------------------------

    public void loadComicFromAPI() {

        marvelAPIHelper.getComicByIDAsync(comicID, new CallbackGetComic() {
            @Override
            public void onResult(boolean ok, Comic oComic, String errorMessage) {
                if (!ok || oComic==null) {
                    showToastQuick("Sorry - This comic are not available right now. Please check your connection.");
                    return;
                }

                setComicAndRefreshUI(oComic);
            }
        });
    }

    // Main View Click Handlers -----------------------

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch(view.getId())
        {
            case R.id.btn_back:
                navigateBack();
                break;
        }

    }



}
