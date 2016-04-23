package com.example.guru_i.searchtweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.guru_i.parcelable.ParcelableTweet;
import com.example.guru_i.validator.TextValidator;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.MediaEntity;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.SearchService;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity{
    private final String TAG = "SearchActivity";
    private TwitterApiClient client = null;
    private SearchService searchService = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Intent authTokenIntent = getIntent();
        String token = authTokenIntent.getStringExtra("token");
        Log.d(TAG, "Token:" + token);
        final EditText searchTerm = (EditText)findViewById(R.id.searchTerm);
        searchTerm.requestFocus();
//        InputMethodManager manager = (InputMethodManager)getSystemService(this.INPUT_METHOD_SERVICE);
//        manager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        searchTerm.addTextChangedListener(new TextValidator(searchTerm) {
            @Override
            public void validate(TextView textView, String text) {
                Log.d(TAG, "text:" + text);
                if (text.isEmpty() || text.length() == 0){
                    Toast.makeText(getApplicationContext(), "Empty search criteria", Toast.LENGTH_SHORT).show();
                    textView.setError("please enter some criteria to search");
                }else{
                    textView.setError(null);
                }
            }
        });
        client = TwitterCore.getInstance().getApiClient();
        searchService = client.getSearchService();

        Button searchTermButton = (Button)findViewById(R.id.search_term_button);
        assert searchTermButton != null;
        searchTermButton.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (searchTerm.getText().toString().isEmpty()){
                            return;
                        }
                        final String term = searchTerm.getText().toString();
                        searchService.tweets(term, null, "en", null, "recent", 100, null,null, null, true, new Callback<Search>() {
                            @Override
                            public void success(Result<Search> result) {
                                ArrayList<ParcelableTweet> tweets = new ArrayList<ParcelableTweet>();
                                for (Tweet tweet: result.data.tweets) {
                                    if (tweet.entities != null && tweet.entities.media != null){
                                        ArrayList<String> tmpMediaUrls = new ArrayList<String>();
                                        for (int i = 0; i < tweet.entities.media.size(); i++) {
                                            MediaEntity mediaEntity = tweet.entities.media.get(i);
                                            tmpMediaUrls.add(mediaEntity.mediaUrl);
                                        }
                                        ParcelableTweet pTweet = new ParcelableTweet();
                                        pTweet.setId(tweet.getId());
                                        pTweet.setText(tweet.text);
                                        pTweet.setProfileUrl(tweet.user.profileImageUrl);
                                        pTweet.setMediaUrl(tmpMediaUrls);
                                        tweets.add(pTweet);
                                    }
                                }
                                if (tweets.size() > 0) {
                                    Intent tweetListIntent = new Intent(SearchActivity.this, TweetListActivity.class);
                                    tweetListIntent.putParcelableArrayListExtra("result", tweets);
                                    startActivity(tweetListIntent);
                                }else{
                                    Toast.makeText(SearchActivity.this, "No results found, please try another criteria", Toast.LENGTH_LONG).show();
                                    searchTerm.selectAll();
                                    searchTerm.requestFocus();
                                }
                            }

                            @Override
                            public void failure(TwitterException e) {
                                Log.e(TAG, e.toString());
                            }
                        });
                    }
                }
        );
    }

    protected void onSearchClick(){

    }

}
