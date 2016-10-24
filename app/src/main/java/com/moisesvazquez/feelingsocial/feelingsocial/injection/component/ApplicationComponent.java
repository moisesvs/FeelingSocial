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

package com.moisesvazquez.feelingsocial.feelingsocial.injection.component;

import android.app.Application;
import android.content.Context;

import com.moisesvazquez.feelingsocial.feelingsocial.FeelingApp;
import com.moisesvazquez.feelingsocial.feelingsocial.data.cache.TweetCache;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.PostExecutionThread;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.ThreadExecutor;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.repository.TweetRepository;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.ApplicationModule;
import com.twitter.sdk.android.core.SessionManager;
import com.twitter.sdk.android.core.TwitterSession;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    // Field injections of any dependencies of the DemoApplication
    void inject(FeelingApp application);

    //Exposed to sub-graphs.
    Application application();
    Context context();
    SessionManager<TwitterSession> sessionManager();
    Retrofit networkManager();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();
    TweetCache tweetCache();
    TweetRepository tweetRepository();

}