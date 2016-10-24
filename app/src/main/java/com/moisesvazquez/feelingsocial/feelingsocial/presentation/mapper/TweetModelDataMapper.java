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
package com.moisesvazquez.feelingsocial.feelingsocial.presentation.mapper;

import com.moisesvazquez.feelingsocial.feelingsocial.domain.model.TweetDomain;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.PerActivity;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.model.TweetModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Mapper class used to transform {@link TweetDomain} (in the domain layer) to {@link TweetModel} in the
 * presentation layer.
 */
@PerActivity
public class TweetModelDataMapper {

    @Inject
    public TweetModelDataMapper() {}

    /**
     * Transform a {@link TweetDomain} into an {@link TweetModel}.
     *
     * @param tweet Object to be transformed.
     * @return {@link TweetModel}.
     */
    public TweetModel transform(TweetDomain tweet) {
        if (tweet == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }

        TweetModel tweetModel = new TweetModel(tweet.getId());
        tweetModel.setTitle(tweet.getTitle());
        tweetModel.setDescription(tweet.getDescription());
        return tweetModel;
    }

    /**
     * Transform a Collection of {@link TweetDomain} into a Collection of {@link TweetModel}.
     *
     * @param tweetsCollection Objects to be transformed.
     * @return List of {@link TweetModel}.
     */
    public List<TweetModel> transform(List<TweetDomain> tweetsCollection) {
        List<TweetModel> tweetModelsCollection;
        if ((tweetsCollection != null) && (!tweetsCollection.isEmpty())) {
            tweetModelsCollection = new ArrayList<>();
            for (TweetDomain tweet : tweetsCollection) {
                tweetModelsCollection.add(transform(tweet));
            }
        } else {
            tweetModelsCollection = Collections.emptyList();
        }

        return tweetModelsCollection;
    }
}