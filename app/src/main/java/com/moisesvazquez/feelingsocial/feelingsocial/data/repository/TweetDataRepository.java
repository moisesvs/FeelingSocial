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
package com.moisesvazquez.feelingsocial.feelingsocial.data.repository;

import com.moisesvazquez.feelingsocial.feelingsocial.data.datasource.TweetDataStoreFactory;
import com.moisesvazquez.feelingsocial.feelingsocial.data.mapper.TweetEntityDataMapper;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.model.TweetDomain;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.TweetRepository;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.datasource.TweetDataStore;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;

/**
 * {@link TweetRepository} for retrieving user data.
 */
@Singleton
public class TweetDataRepository implements TweetRepository {

    private final TweetDataStoreFactory tweetDataStoreFactory;
    private final TweetEntityDataMapper tweetEntityDataMapper;

    /**
     * Constructs a {@link TweetRepository}.
     *
     * @param dataStoreFactory     A factory to construct different data source implementations.
     * @param userEntityDataMapper {@link TweetEntityDataMapper}.
     */
    @Inject
    public TweetDataRepository(TweetDataStoreFactory dataStoreFactory,
                               TweetEntityDataMapper userEntityDataMapper) {
        this.tweetDataStoreFactory = dataStoreFactory;
        this.tweetEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<List<TweetDomain>> tweets() {
        //we always get all users from the cloud
        final TweetDataStore tweetDataStore = this.tweetDataStoreFactory.createCloudDataStore();
        return tweetDataStore   .tweetEntityList()
                                .map(this.tweetEntityDataMapper::transform);
    }
}