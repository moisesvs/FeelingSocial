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

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface MachineRestApi {

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link ResultIntent}.
     */
    Observable<List<ResultIntent.IntentProbability>> intentResult(TextList intent);

}