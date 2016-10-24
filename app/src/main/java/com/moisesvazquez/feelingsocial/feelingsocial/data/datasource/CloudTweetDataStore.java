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
package com.moisesvazquez.feelingsocial.feelingsocial.data.datasource;

import com.moisesvazquez.feelingsocial.feelingsocial.data.cache.TweetCache;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TweetEntity;
import com.moisesvazquez.feelingsocial.feelingsocial.data.net.RestApi;
import com.moisesvazquez.feelingsocial.feelingsocial.data.net.TwitterRestApi;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.datasource.TweetDataStore;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;

/**
 * {@link TweetDataStore} implementation based on connections to the api (Cloud).
 */
class CloudTweetDataStore implements TweetDataStore {

    private final TwitterRestApi restApi;
    private final TweetCache     tweetCache;

    private final Action1<TweetEntity> saveToCacheAction = userEntity -> {
        if (userEntity != null) {
            CloudTweetDataStore.this.tweetCache.put(userEntity);
        }
    };

    /**
     * Construct a {@link TweetDataStore} based on connections to the api (Cloud).
     *
     * @param restApi    The {@link RestApi} implementation to use.
     * @param tweetCache A {@link TweetCache} to cache data retrieved from the api.
     */
    CloudTweetDataStore(TwitterRestApi restApi, TweetCache tweetCache) {
        this.restApi = restApi;
        this.tweetCache = tweetCache;
    }

    @Override
    public Observable<List<TweetEntity>> tweetEntityList() {
        return this.restApi.tweetsEntities();
    }

}