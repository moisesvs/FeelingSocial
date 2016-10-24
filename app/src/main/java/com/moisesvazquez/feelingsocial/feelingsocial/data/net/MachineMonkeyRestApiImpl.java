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
package com.moisesvazquez.feelingsocial.feelingsocial.data.net;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TextList;
import com.moisesvazquez.feelingsocial.feelingsocial.data.exception.NetworkConnectionException;
import com.moisesvazquez.feelingsocial.feelingsocial.data.net.operations.IntentMonkeyLearning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * {@link MachineMonkeyRestApiImpl} implementation for retrieving data from the network.
 */
public class MachineMonkeyRestApiImpl extends RestApi implements MachineRestApi {

    /**
     * Client http
     */
    @Inject
    Retrofit clientHttp;

    /**
     * Constructor of the class
     */
    @Inject
    public MachineMonkeyRestApiImpl() {
    }

    /**
     * Intent entities
     *
     * @return the intent entities
     */
    @Override
    public Observable<List<ResultIntent.IntentProbability>> intentResult(TextList intent) {

        if (isThereInternetConnection()) {
            return Observable.fromCallable(() -> callIntentEntity(intent));
        } else {
            return Observable.error(new NetworkConnectionException("There is not network"));
        }

    }

    /**
     * Request call intent
     */
    private List<ResultIntent.IntentProbability> callIntentEntity(TextList intent) throws IOException {

        List<ResultIntent.IntentProbability> resultAverage;
        IntentMonkeyLearning                 service = clientHttp.create(IntentMonkeyLearning.class);
        Call<ResultIntent>                   call    = service.build(intent);
        try {
            Response<ResultIntent> response = call.execute();
            if (response.code() == 200) {
                ResultIntent body = response.body();
                if (body != null) {
                    List<List<ResultIntent.IntentProbability>> listResult = body.getResult();
                    resultAverage = new ArrayList<>(listResult.size());
                    for (List<ResultIntent.IntentProbability> intentProbabilities : listResult) {
                        resultAverage.add(maxElementIntent(intentProbabilities));
                    }

                    return resultAverage;
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return new ArrayList<>(0);

    }

    /**
     * Max element list intent probability
     *
     * @param element
     * @return
     */
    private ResultIntent.IntentProbability maxElementIntent(List<ResultIntent.IntentProbability> element) {
        return Collections.max(element, (intentProbability, t1) -> {
            if (intentProbability.getProbability() == t1.getProbability()) {
                return 0;
            } else if (intentProbability.getProbability() < t1.getProbability()) {
                return -1;
            }
            return 1;
        });
    }

}