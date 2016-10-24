package com.moisesvazquez.feelingsocial.feelingsocial.presentation;

import com.github.pwittchen.reactivenetwork.library.Connectivity;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent;

public interface ShowTweetsUi {

    void showLoading();

    void hideLoading();

    void hideRetry();

    void showIntentProbability(ResultIntent.IntentProbability intentProbability);

    void errorNetwork(Connectivity connectivity);

}
