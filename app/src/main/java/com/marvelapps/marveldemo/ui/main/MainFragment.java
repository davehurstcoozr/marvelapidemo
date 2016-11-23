package com.marvelapps.marveldemo.ui.main;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marvelapps.marveldemo.R;
import com.marvelapps.marveldemo.marvelapi.MarvelAPIHelper;
import com.marvelapps.marveldemo.marvelapi.callbacks.CallbackGetComics;
import com.marvelapps.marveldemo.marvelapi.models.Comic;
import com.marvelapps.marveldemo.ui.base.BaseFragment;
import com.marvelapps.marveldemo.ui.comicdetails.ComicDetailsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;


/**
 * Created by Dave on 11/21/2016.
 */

public class MainFragment extends BaseFragment {

    static final int LAYOUT_FRAGMENT = R.layout.main_fragment;

    static private MarvelAPIHelper marvelAPIHelper = MarvelAPIHelper.getInstance();
    static private ImageLoader imageLoader = ImageLoader.getInstance();

    // Properties ----
    public ArrayList<Comic> comicsList = null;
    public ViewPager comicsViewPager;
    private ComicListAdapter comicListAdapter = null;



    // ----------------

    public MainFragment() {}

    //---------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateAndInitView(LAYOUT_FRAGMENT, inflater, container);
    }

    /** Init UI for this fragment */
    @Override
    public void initUI() {
        super.initUI();

        //Add button clicks and UI init here
        //tools.id(R.id.btnToolbarBack).clicked(this);

        showToastQuick("Welcome to the Marvel Demo App!");

        loadMarvelComicsFromAPI();

        initCarousel();

    }

    // Main View Click Handlers -----------------------

    @Override
    public void onClick(View view) {
        super.onClick(view);
        /*
         switch(view.getId())
        {
            case R.id.btn_next:
                // Code for click
                break;
        }
         */
    }


    // Marvel Comics API

    public void loadMarvelComicsFromAPI() {

        marvelAPIHelper.getComicsAsync(new CallbackGetComics() {
            @Override
            public void onResult(boolean ok, ArrayList<Comic> data, String errorMessage) {
                if (!ok || data==null) {
                    showToastQuick("Sorry - Comics are not available right now. Please check your connection.");
                    return;
                }

                comicsList = data;
                refreshComicsList();
            }
        });
    }

    public void refreshComicsList() {
        if (comicsList==null || comicsList.size()==0) return;
        //--- Show the comics now

        comicListAdapter.setComicsList(comicsList);

    }


    public void initCarousel() {

        comicListAdapter = new ComicListAdapter(getActivity());
        ViewPager comicsViewPager = (ViewPager)tools.id(R.id.view_pager).getView();
        comicsViewPager.setAdapter(comicListAdapter);
        //comicsViewPager.setCurrentItem(getArguments().getInt(Constants.Extra.IMAGE_POSITION, 0));



    }

    public void selectedComicItem(Comic comic) {
        if (comic==null) return;
        //showToastQuick("Selected Comic Item "+comic.id+" "+comic.title);

        final Activity activity = getActivity();
        ComicDetailsActivity.start(activity, comic.id, comic.title);
    }


    // ----

    public class ComicListAdapter extends PagerAdapter {

       // private static final String[] IMAGE_URLS = Constants.IMAGES;

        private LayoutInflater inflater;
        private DisplayImageOptions options;

        ComicListAdapter(Context context) {
            inflater = LayoutInflater.from(context);

            options = new DisplayImageOptions.Builder()
                    //.showImageForEmptyUri(R.drawable.ic_empty)
                    //.showImageOnFail(R.drawable.ic_error)
                    .resetViewBeforeLoading(true)
                    .cacheOnDisk(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    //.bitmapConfig(Bitmap.Config.RGB_565)
                    //.considerExifParams(true)
                    .displayer(new FadeInBitmapDisplayer(300))
                    .build();
        }


        // Properties

        public ArrayList<Comic> comicsList = null;

        // Methods

        public void setComicsList(ArrayList<Comic> items) {
            this.comicsList = items;
            this.notifyDataSetChanged();    //Update now the data has changed
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            if (comicsList==null) return 0;
            return comicsList.size();
        }


        @Override
        public Object instantiateItem(ViewGroup view, int position) {
            View imageLayout = inflater.inflate(R.layout.item_pager_image, view, false);
            assert imageLayout != null;

            //Get the item model
            Comic item = comicsList.get(position);

            final ProgressBar spinner = (ProgressBar) imageLayout.findViewById(R.id.loading);


            //Set title
            TextView textView = (TextView) imageLayout.findViewById(R.id.text1);
            if (textView!=null) {
                textView.setText(item.title);
            }

            //Set image
            ImageView imageView = (ImageView) imageLayout.findViewById(R.id.image);
            String url = item.getThumbnailImageUrl();
            imageView.setTag(position);
            ImageLoader.getInstance().displayImage(url, imageView, options, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    spinner.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    spinner.setVisibility(View.GONE);
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int item = (int)v.getTag();
                    if (item>=0) {
                        Comic comic = comicsList.get(item);
                        selectedComicItem(comic);
                    }

                }
            });

            view.addView(imageLayout, 0);
            return imageLayout;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }



}
