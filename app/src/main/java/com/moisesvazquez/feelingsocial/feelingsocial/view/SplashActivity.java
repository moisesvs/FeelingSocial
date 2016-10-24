package com.moisesvazquez.feelingsocial.feelingsocial.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.moisesvazquez.feelingsocial.feelingsocial.FeelingApp;
import com.moisesvazquez.feelingsocial.feelingsocial.R;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.DaggerSplashActivityComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.SplashActivityComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.ActivityModule;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";

    private SplashActivityComponent splashComponent;

    @Inject
    SessionManager<TwitterSession> sessionManager;

    /**
     * Twitter login button
     */
    private TwitterLoginButton loginTwitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        splashComponent = DaggerSplashActivityComponent .builder()
                                                        .applicationComponent(FeelingApp.getInstance().component())
                                                        .activityModule(new ActivityModule(this))
                                                        .build();

        splashComponent.inject(this);

        loginTwitter = (TwitterLoginButton) findViewById(R.id.loginTwitter);

        TwitterSession activeSession = sessionManager.getActiveSession();
        if (activeSession == null) {
            loginTwitter.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    Log.e(TAG, "Login twitter successfull");
                }

                @Override
                public void failure(TwitterException exception) {
                    Log.e(TAG, "Error login Twitter!!!");
                }
            });
        } else {
            navigator.navigateToShowTweets(this);
            finish();
        }
    }

    /**
     * On activity result
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data result
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        loginTwitter.onActivityResult(requestCode, resultCode, data);
        navigator.navigateToShowTweets(this);
        finish();
    }

}
