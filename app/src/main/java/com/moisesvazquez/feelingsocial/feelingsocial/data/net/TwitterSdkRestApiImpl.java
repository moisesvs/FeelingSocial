/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.moisesvazquez.feelingsocial.feelingsocial.data.net;

import android.content.Context;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TweetEntity;
import com.moisesvazquez.feelingsocial.feelingsocial.data.exception.NetworkConnectionException;
import com.moisesvazquez.feelingsocial.feelingsocial.data.mapper.TweetDataMapper;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import rx.Observable;

/**
 * {@link TwitterSdkRestApiImpl} implementation for retrieving data from the network.
 */
public class TwitterSdkRestApiImpl extends RestApi implements TwitterRestApi {

    /**
     * The context app
     */
    @Inject
    Context context;

    /**
     * The session manager
     */
    @Inject
    SessionManager<TwitterSession> sessionManager;

    /**
     * Tweet data mapper
     */
    TweetDataMapper transform;

    /**
     * Constructor of the class
     */
    @Inject
    public TwitterSdkRestApiImpl(TweetDataMapper transform) {
        this.transform = transform;
    }

    /**
     * Get tweets
     *
     * @return the tweets
     */
    public Observable<List<TweetEntity>> tweetsEntities() {

        if (isThereInternetConnection()) {
            return Observable.fromCallable(this::callTweetEntity)
                    .map(tweetList -> transform.transform(tweetList));

        } else {
            return Observable.error(new NetworkConnectionException("There is not network"));
        }

    }

    /**
     * Request body
     */
    private List<Tweet> callTweetEntity() throws IOException {

        Call<List<Tweet>> call = TwitterCore.getInstance()
                .getApiClient()
                .getStatusesService()
                .userTimeline(sessionManager.getActiveSession().getUserId(),
                              sessionManager.getActiveSession().getUserName(),
                              20, //the number of tweets we want to fetch
                              null,
                              null,
                              null,
                              true,
                              null,
                              true);

        return call.execute().body();
    }

    /**
     * Max element list intent probability
     *
     * @param element
     * @return
     */
    private ResultIntent.IntentProbability maxElementIntent(List<ResultIntent.IntentProbability> element) {
        return Collections.max(element, (intentProbability, t1) -> {
            if (intentProbability.getProbability() == t1.getProbability()) {
                return 0;
            } else if (intentProbability.getProbability() < t1.getProbability()) {
                return -1;
            }
            return 1;
        });
    }
}