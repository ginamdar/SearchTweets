package com.example.guru_i.searchtweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity:";


    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "MeOKHahNaxmdES1hFGAqdEh5u";
    private static final String TWITTER_SECRET = "K73aUFSZM1IZbMznRPEvsO0IcBWl1ohDeOdHjv76f6KisS2NcM";
    private TwitterLoginButton loginButton = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TwitterAuthToken authToken;
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_main);

        TwitterSession session = getActiveSession();
        if (session == null || session.getAuthToken().isExpired()){
            loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {

                @Override
                public void success(Result<TwitterSession> result) {
                    TwitterSession session = getActiveSession();
                    TwitterAuthToken authToken = session.getAuthToken();
                    startSearchActivity(authToken.token, authToken.secret);
                }

                @Override
                public void failure(TwitterException e) {
                    Log.e(TAG, "Failed:" + e.getMessage());
                }
            });
        }else {
            startSearchActivity(session.getAuthToken().token, session.getAuthToken().secret);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Pass the activity result to the login button.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }

    private TwitterSession getActiveSession(){
        return Twitter.getSessionManager().getActiveSession();
    }

    private void startSearchActivity(String authToken, String secret){
        Intent searchActivity = new Intent(MainActivity.this, SearchActivity.class);
        searchActivity.putExtra("token", authToken);
        searchActivity.putExtra("secret", secret);
        startActivity(searchActivity);
    }

}
