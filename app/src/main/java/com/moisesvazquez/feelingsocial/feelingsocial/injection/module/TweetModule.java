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
package com.moisesvazquez.feelingsocial.feelingsocial.injection.module;

import com.moisesvazquez.feelingsocial.feelingsocial.data.net.TwitterRestApi;
import com.moisesvazquez.feelingsocial.feelingsocial.data.net.TwitterSdkRestApiImpl;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.usecase.GetTweetListUseCase;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.usecase.UseCase;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides user related collaborators.
 */
@Module
public class TweetModule {

    public TweetModule() {}

    @Provides
    @PerActivity
    @Named("TweetList")
    UseCase provideGetTweetListUseCase(GetTweetListUseCase getTweetList) {
        return getTweetList;
    }

    @Provides
    @PerActivity
    TwitterRestApi provideRestApi(TwitterSdkRestApiImpl restApi) {
        return restApi;
    }
}