package com.example.guru_i.searchtweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.guru_i.Downloader.ImageDownloader;
import com.example.guru_i.parcelable.ParcelableTweet;

import java.util.ArrayList;

/**
 * Created by guru_i on 2016-04-23.
 */
public class TweetDetails extends AppCompatActivity {

    private static final String TAG = "TweetDetails";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);
        ParcelableTweet detailedTweet = getIntent().getParcelableExtra("tweetDetails");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText(detailedTweet.getText());

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);

        ArrayList<String> mUrls = detailedTweet.getMediaUrl();
        for (String url : mUrls){
            ImageView mediaView = new ImageView(getApplicationContext());
            mediaView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            mediaView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

            ImageDownloader downloader = new ImageDownloader(mediaView, String.valueOf(detailedTweet.getId()));
            downloader.execute(url);
            scrollView.addView(mediaView);
        }

    }
}
