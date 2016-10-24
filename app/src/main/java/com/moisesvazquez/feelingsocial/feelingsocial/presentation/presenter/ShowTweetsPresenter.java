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

import android.net.NetworkInfo;
import android.support.annotation.NonNull;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TextList;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.DefaultSubscriber;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.model.TweetDomain;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.usecase.GetIntentListUseCase;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.usecase.UseCase;
import com.moisesvazquez.feelingsocial.feelingsocial.injection.PerActivity;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.ShowTweetsUi;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.mapper.TweetModelDataMapper;
import com.moisesvazquez.feelingsocial.feelingsocial.presentation.model.TweetModel;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observable;
import rx.math.operators.OperatorMinMax;

/**
 * {@link Presenter} that controls communication between views and models of the presentation
 * layer.
 */
@PerActivity
public class ShowTweetsPresenter extends BasePresenter {

    private static final String TAG = "ShowTweetsPresenter";
    private ShowTweetsUi viewActivity;

    private final UseCase              getTweetListUseCase;
    private final UseCase              getIntentListUseCase;
    private final TweetModelDataMapper tweetModelDataMapper;

    @Inject
    public ShowTweetsPresenter(@Named("TweetList") UseCase getTweetListUseCase,
                               @Named("IntentList") UseCase getIntentListUseCase,
                               TweetModelDataMapper tweetModelDataMapper) {
        // show tweets presenter
        this.getTweetListUseCase = getTweetListUseCase;
        this.tweetModelDataMapper = tweetModelDataMapper;
        this.getIntentListUseCase = getIntentListUseCase;
    }

    public void setView(@NonNull ShowTweetsUi activity) {
        this.viewActivity = activity;
    }

    /**
     * Initializes the presenter by start retrieving the tweet list.
     */
    public void initialize() {
        this.loadTweetList();
    }

    /**
     * Loads all tweet.
     */
    private void loadTweetList() {
        this.hideViewRetry();
        this.showViewLoading();
        this.getTweetList();
    }

    @Override
    public void create() {
        super.create();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void destroy() {
        super.destroy();

        this.getTweetListUseCase.unsubscribe();
        this.viewActivity = null;
    }

    private void hideViewRetry() {
        this.viewActivity.hideRetry();
    }

    private void showViewLoading() {
        this.viewActivity.showLoading();
    }

    private void hideViewLoading() {
        this.viewActivity.hideLoading();
    }

    /**
     * Show tweets collection in view
     *
     * @param tweetCollection the tweet collection
     */
    private void showTweetsCollectionInView(List<TweetDomain> tweetCollection) {
        TextList textList = new TextList(tweetCollection.size());

        final Collection<TweetModel> userModelsCollection = this.tweetModelDataMapper.transform(tweetCollection);
        Observable.from(userModelsCollection)
                .forEach(tweet -> textList.add(tweet.getDescription()));

        ((GetIntentListUseCase) (this.getIntentListUseCase)).setTextList(textList);
        getIntentList();
    }

    /**
     * Show intent probability
     *
     * @param maxIntent the tweet probability collection
     */
    private void showIntentProbability(ResultIntent.IntentProbability maxIntent) {
        this.viewActivity.showIntentProbability(maxIntent);
    }

    /**
     * Get tweet list
     */
    private void getTweetList() {
        this.getTweetListUseCase.execute(new TweetListSubscriber());
    }

    /**
     * Get intent list
     */
    private void getIntentList() {
        this.getIntentListUseCase.execute(new IntentListSubscriber());
    }

    /**
     * Tweet list subscriber
     */
    private final class TweetListSubscriber extends DefaultSubscriber<List<TweetDomain>> {

        @Override
        public void onCompleted() {
            ShowTweetsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            ShowTweetsPresenter.this.hideViewLoading();
        }

        @Override
        public void onNext(List<TweetDomain> tweets) {
            ShowTweetsPresenter.this.showTweetsCollectionInView(tweets);
        }
    }

    /**
     * Intent list subscriber
     */
    private final class IntentListSubscriber extends DefaultSubscriber<List<ResultIntent.IntentProbability>> {

        @Override
        public void onCompleted() {
            ShowTweetsPresenter.this.hideViewLoading();
        }

        @Override
        public void onError(Throwable e) {
            ShowTweetsPresenter.this.hideViewLoading();
        }

        @Override
        public void onNext(List<ResultIntent.IntentProbability> intentProbabilities) {
            modeListElement(intentProbabilities);
        }

        /**
         * Extract of the moda list element
         *
         * @param elements the elements
         */
        private void modeListElement(List<ResultIntent.IntentProbability> elements) {
            Observable<List<ResultIntent.IntentProbability>> listObservable = Observable.from(elements)
                    .groupBy(element -> element.getLabel()).flatMap(Observable::toList);

            OperatorMinMax  .max(listObservable, (intentProbabilities, t1) -> intentProbabilities.size() - t1.size())
                            .map(list -> list.get(0))
                            .subscribe(ShowTweetsPresenter.this::showIntentProbability
            );
        }
    }

    /**
     * Network state
     * @param connectivity connectivity state
     */
    @Override
    public void networkState(Connectivity connectivity) {
        super.networkState(connectivity);
        if (connectivity.getState().equals(NetworkInfo.State.DISCONNECTED)) {
            this.viewActivity.errorNetwork(connectivity);
        }
    }
}