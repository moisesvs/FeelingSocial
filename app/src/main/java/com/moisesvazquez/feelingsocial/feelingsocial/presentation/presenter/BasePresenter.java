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
package com.moisesvazquez.feelingsocial.feelingsocial.presentation.presenter;

import android.content.Context;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.DefaultSubscriber;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * {@link BasePresenter} that controls communication between views and models of the presentation
 * layer.
 */
public class BasePresenter implements Presenter {

    @Inject
    Context context;

    /**
     * Network subscriber
     */
    private NetworkSubscriber networkSubscriber;

    @Override
    public void create() {
        networkSubscriber = new NetworkSubscriber();
        ReactiveNetwork .observeNetworkConnectivity(context)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(networkSubscriber);

    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        networkSubscriber.unsubscribe();
        networkSubscriber = null;
    }

    @Override
    public void networkState(Connectivity connectivity) {
        // nothing
    }

    /**
     * Network subscriber
     */
    protected final class NetworkSubscriber extends DefaultSubscriber<Connectivity> {

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(Connectivity connectivity) {
            networkState(connectivity);
        }
    }

}