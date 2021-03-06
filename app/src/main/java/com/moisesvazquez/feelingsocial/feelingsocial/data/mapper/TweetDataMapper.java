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
package com.moisesvazquez.feelingsocial.feelingsocial.data.mapper;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TweetEntity;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Mapper class used to transform {@link TweetEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
@Singleton
public class TweetDataMapper {

    @Inject
    public TweetDataMapper() {}

    /**
     * Transform a {@link TweetEntity} into an {@link Tweet}.
     *
     * @param tweetEntity Object to be transformed.
     * @return {@link Tweet} if valid {@link TweetEntity} otherwise null.
     */
    public TweetEntity transform(Tweet tweetEntity) {
        TweetEntity tweet = null;
        if (tweetEntity != null) {
            tweet = new TweetEntity(tweetEntity.getId());
            tweet.setTitle(tweetEntity.user.name);
            tweet.setDescription(tweetEntity.text);
        }

        return tweet;
    }

    /**
     * Transform a List of {@link TweetEntity} into a Collection of {@link User}.
     *
     * @param tweetCollection Object Collection to be transformed.
     * @return {@link User} if valid {@link TweetEntity} otherwise null.
     */
    public List<TweetEntity> transform(Collection<Tweet> tweetCollection) {
        List<TweetEntity> tweetList = null;
        if (tweetCollection != null) {
            tweetList = new ArrayList<>(tweetCollection.size());
            TweetEntity       tweet;
            for (Tweet tweetEntity : tweetCollection) {
                tweet = transform(tweetEntity);
                if (tweet != null) {
                    tweetList.add(tweet);
                }
            }
        }

        return tweetList;
    }
}