/**
 * Copyright (C) 2014 Twitter Inc and other contributors.
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
package com.moisesvazquez.feelingsocial.feelingsocial;

import android.app.Application;
import android.graphics.Typeface;

import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.ApplicationComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.component.DaggerApplicationComponent;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.module.ApplicationModule;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import javax.inject.Inject;

import io.fabric.sdk.android.Fabric;
import retrofit2.Retrofit;

/**
 * This class represents the Application and extends Application it is used to initiate the
 * application.
 */
public class FeelingApp extends Application {

    private ApplicationComponent applicationComponent;

    @Inject
    Retrofit clientHttp;

    private static FeelingApp     singleton;
    private        Typeface       notoFont;

    public static FeelingApp getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        applicationComponent.inject(this);

        singleton = this;
        extractNoto();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constants.CONSUMER_KEY, Constants.CONSUMER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    private void extractNoto() {
        notoFont = Typeface.createFromAsset(getAssets(), "fonts/NotoSans-Regular.ttf");
    }

    /**
     * Return the type face app
     *
     * @return the type face app
     */
    public Typeface getTypeface() {
        if (notoFont == null) {
            extractNoto();
        }
        return notoFont;
    }

    /**
     * Return component app
     *
     * @return the component app
     */
    public ApplicationComponent component() {
        return applicationComponent;
    }

}