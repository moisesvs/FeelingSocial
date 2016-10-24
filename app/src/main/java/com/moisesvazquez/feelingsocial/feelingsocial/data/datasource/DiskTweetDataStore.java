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
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.datasource.TweetDataStore;

import java.util.List;

import rx.Observable;

/**
 * {@link TweetDataStore} implementation based on file system data store.
 */
class DiskTweetDataStore implements TweetDataStore {

    private final TweetCache userCache;

    /**
     * Construct a {@link TweetDataStore} based file system data store.
     *
     * @param userCache A {@link TweetCache} to cache data retrieved from the api.
     */
    DiskTweetDataStore(TweetCache userCache) {
        this.userCache = userCache;
    }

    @Override
    public Observable<List<TweetEntity>> tweetEntityList() {
        //TODO: implement simple cache for storing/retrieving collections of users.
        throw new UnsupportedOperationException("Operation is not available!!!");
    }
}