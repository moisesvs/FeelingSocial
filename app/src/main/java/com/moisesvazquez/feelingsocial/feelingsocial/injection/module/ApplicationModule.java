/*
 * Copyright (C) 2013 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.moisesvazquez.feelingsocial.feelingsocial.injection.module;

import android.app.Application;
import android.content.Context;

import com.moisesvazquez.feelingsocial.feelingsocial.data.cache.TweetCache;
import com.moisesvazquez.feelingsocial.feelingsocial.data.cache.TweetCacheImpl;
import com.moisesvazquez.feelingsocial.feelingsocial.data.executor.JobExecutor;
import com.moisesvazquez.feelingsocial.feelingsocial.data.repository.TweetDataRepository;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.PostExecutionThread;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.ThreadExecutor;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.TweetRepository;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.UIThread;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A module for Android-specific dependencies which require a or
 * {@link android.app.Application} to create.
 */
@Module
public class ApplicationModule {

    public static final String BASE_URL = "https://api.monkeylearn.com";

    private final Application application;


    public ApplicationModule(Application application) {
        this.application = application;
    }

    /**
     * Expose the application to the graph.
     */
    @Provides
    @Singleton
    Application application() {
        return application;
    }


    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    /**
     * Expose the session manager to the graph.
     */
    @Provides
    @Singleton
    SessionManager<TwitterSession> sessionManagerTwitter() {
        return Twitter.getSessionManager();
    }

    /**
     * Expose the network manager to the graph.
     */
    @Provides
    @Singleton
    Retrofit networkManager() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    /**
     * Expose the tweet cache
     */
    @Provides
    @Singleton
    TweetCache provideTweetCache(TweetCacheImpl tweetCache) {
        return tweetCache;
    }

    /**
     * Expose the tweet repository
     */
    @Provides
    @Singleton
    TweetRepository provideTweetRepository(TweetDataRepository tweetDataRepository) {
        return tweetDataRepository;
    }

}