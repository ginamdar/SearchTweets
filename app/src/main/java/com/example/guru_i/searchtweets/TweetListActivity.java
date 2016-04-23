package com.example.guru_i.searchtweets;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.guru_i.adaptor.CustomTweetAdaptor;
import com.example.guru_i.parcelable.ParcelableTweet;

import java.util.ArrayList;

/**
 * Created by guru_i on 2016-04-21.
 */
public class TweetListActivity extends ListActivity {
    private final String TAG = "TweetListActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ArrayList<ParcelableTweet> result = getIntent().getParcelableArrayListExtra("result");
        final CustomTweetAdaptor customTweet = new CustomTweetAdaptor(TweetListActivity.this, R.layout.activity_tweet, result);
        ListView list = getListView();
        list.setAdapter(customTweet);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(TweetListActivity.this, "You selected:" + customTweet.getItem(position).toString(), Toast.LENGTH_SHORT).show();
                Intent detailTweetIntent = new Intent(TweetListActivity.this, TweetDetails.class);
                detailTweetIntent.putExtra("tweetDetails", result.get(position));
                startActivity(detailTweetIntent);
            }
        });

        //

        //
    }

}

