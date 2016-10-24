package com.moisesvazquez.feelingsocial.feelingsocial.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.moisesvazquez.feelingsocial.feelingsocial.FeelingApp;
import com.moisesvazquez.feelingsocial.feelingsocial.R;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent.IntentProbability;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.DaggerShowTweetsComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.ShowTweetsComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.ActivityModule;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.TweetModule;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.ShowTweetsUi;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.presenter.ShowTweetsPresenter;
import com.moisesvazquez.feelingsocial.feelingsocial.utils.NotificationUtil;

import javax.inject.Inject;

public class ShowTweetsActivity extends BaseActivity implements ShowTweetsUi {

    private static final String TAG = "ShowTweetsActivity";

    private ShowTweetsComponent tweetsComponent;

    IntentProbability modaIntentTweet;

    /**
     * Presenter show tweets
     */
    @Inject
    ShowTweetsPresenter userListPresenter;

    /**
     * Get calling intent
     *
     * @param context the context
     * @return
     */
    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, ShowTweetsActivity.class);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweetsComponent = DaggerShowTweetsComponent.builder()
                .applicationComponent(FeelingApp.getInstance().component())
                .activityModule(new ActivityModule(this))
                .tweetModule(new TweetModule())
                .build();

        tweetsComponent.inject(this);

        setContentView(R.layout.activity_showtweets);

        userListPresenter.create();
        userListPresenter.setView(this);

        Button retrieveTweets = (Button) findViewById(R.id.retrieveTweets);
        retrieveTweets.setOnClickListener(view -> {
            userListPresenter.initialize();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        userListPresenter.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        userListPresenter.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userListPresenter.destroy();
    }

    // methods Show Tweets UI
    @Override
    public void showLoading() {
        // show loading
    }

    @Override
    public void hideLoading() {
        // hide loading
    }

    @Override
    public void showIntentProbability(IntentProbability intentProbability) {
        if (intentProbability != null) {
            NotificationUtil.showSimpleNotificationMode(this,
                                                        intentProbability.getLabel(),
                                                        "Probabilidad: " + intentProbability.getProbability());
        }
    }

    @Override
    public void errorNetwork(Connectivity connectivity) {
        NotificationUtil.showSimpleNotificationMode(this,
                                                    "Network error " + connectivity.getName(),
                                                    "Not connection - Please, I try to reconnect your mobile phone");

    }

    @Override
    public void hideRetry() {
        // hide retry
    }

}