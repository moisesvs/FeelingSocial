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
package com.moisesvazquez.feelingsocial.feelingsocial.domain.usecase;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TextList;
import com.moisesvazquez.feelingsocial.feelingsocial.data.net.MachineRestApi;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.PostExecutionThread;
import com.moisesvazquez.feelingsocial.feelingsocial.domain.executor.ThreadExecutor;
import com.twitter.sdk.android.core.models.Tweet;

import javax.inject.Inject;

import rx.Observable;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link Tweet}.
 */
public class GetIntentListUseCase extends UseCase {

    private final MachineRestApi machineRepository;

    private TextList textList;

    @Inject
    public GetIntentListUseCase(MachineRestApi userRepository,
                                ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        this.machineRepository = userRepository;
    }

    /**
     * Set text list
     *
     * @param textList
     */
    public void setTextList(TextList textList) {
        this.textList = textList;
    }

    @Override
    public Observable buildUseCaseObservable() {
        return this.machineRepository.intentResult(textList);
    }

}