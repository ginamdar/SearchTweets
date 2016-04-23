package com.example.guru_i.adaptor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.guru_i.Downloader.ImageCache;
import com.example.guru_i.Downloader.ImageDownloader;
import com.example.guru_i.parcelable.ParcelableTweet;
import com.example.guru_i.searchtweets.R;

import java.util.ArrayList;

/**
 * Created by guru_i on 2016-04-22.
 */
public class CustomTweetAdaptor extends ArrayAdapter<ParcelableTweet> {
    private static final String TAG = "CustomTweetAdaptor";
    private final Activity context;
    private final ArrayList<ParcelableTweet> tweets;

    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private ImageCache imageCache = new ImageCache(cacheSize);

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            imageCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return imageCache.get(key);
    }

    public void loadBitmap(String key, ImageView avatar, String url) {
//        final Bitmap bitmap = getBitmapFromMemCache(key);
//        if (bitmap != null) {
//            Log.d(TAG, "from cache:" + url);
//            avatar.setImageBitmap(bitmap);
//        } else {
//            ImageDownloader downloader = new ImageDownloader(avatar, key);
//            downloader.execute(url);
//        }
        ImageDownloader downloader = new ImageDownloader(avatar, key);
        downloader.execute(url);
    }

    public CustomTweetAdaptor(Activity context, int resource, ArrayList<ParcelableTweet> tweets) {
        super(context, resource, tweets);
        this.context = context;
        this.tweets = tweets;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        ParcelableTweet parcelableTweet = this.tweets.get(position);
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.activity_tweet, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.text);
        txtTitle.setText(parcelableTweet.getText());

        ImageView avatar = (ImageView) rowView.findViewById(R.id.avatarView);
        loadBitmap(String.valueOf(parcelableTweet.getId()), avatar, parcelableTweet.getProfileUrl());
        return rowView;
    }


    //Inner Class!?

}
