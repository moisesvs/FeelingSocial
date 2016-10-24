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
package com.moisesvazquez.feelingsocial.feelingsocial.data.mapper;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TweetEntity;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class TweetEntityJsonMapper {

    private final Gson gson;

    @Inject
    public TweetEntityJsonMapper() {
        this.gson = new Gson();
    }

    /**
     * Transform from valid json string to {@link TweetEntity}.
     *
     * @param userJsonResponse A json representing a user profile.
     * @return {@link TweetEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public TweetEntity transformUserEntity(String userJsonResponse) throws JsonSyntaxException {
        try {
            Type       userEntityType = new TypeToken<TweetEntity>() {}.getType();
            TweetEntity userEntity     = this.gson.fromJson(userJsonResponse, userEntityType);

            return userEntity;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }

    /**
     * Transform from valid json string to List of {@link TweetEntity}.
     *
     * @param userListJsonResponse A json representing a collection of users.
     * @return List of {@link TweetEntity}.
     * @throws com.google.gson.JsonSyntaxException if the json string is not a valid json structure.
     */
    public List<TweetEntity> transformUserEntityCollection(String userListJsonResponse)
            throws JsonSyntaxException {

        List<TweetEntity> userEntityCollection;
        try {
            Type listOfUserEntityType = new TypeToken<List<TweetEntity>>() {}.getType();
            userEntityCollection = this.gson.fromJson(userListJsonResponse, listOfUserEntityType);

            return userEntityCollection;
        } catch (JsonSyntaxException jsonException) {
            throw jsonException;
        }
    }
}