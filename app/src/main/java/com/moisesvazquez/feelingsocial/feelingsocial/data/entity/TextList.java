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
/**
 * Factory that creates different implementations of {@link TweetDataStore}.
 */
package com.moisesvazquez.feelingsocial.feelingsocial.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity list to
 */
public class TextList {
//    {
//        "text_list": [
//        "RT @amuda: Mas rentable unir que dividir https://t.co/eXuroXY6zL",
//                "RT @LopezChicote: La cultura del esfuerzo desaparece si atribuyen a otros tu trabajo. Orgulloso de este equipazo!!! #bbvawallet https://t.c…",
//                "RT @abdonrd: BBVA is using Polymer! #io16 https://t.co/oR8Lr8MBaj",
//                "RT @KristianT: Action speaks louder than words @LopezChicote of @bbva demonstrates #HCE live at @Visa #HCE summit #MWC15 http://t.co/zhOLvT…",
//                "RT @Norib: “Lo siento, dejo la profesión, dejo la informática, y me dedico a otra cosa” http://t.co/k4SfCTXz9X vía @jgarzas Por que las cos…",
//                "RT @movilok: Todo listo. ¡Comenzamos! http://t.co/JFUPVJe1kZ"
//        ]
//    }

    @SerializedName("text_list")
    List<String> textList;

    public TextList(int size) {
        this.textList = new ArrayList<String>(size);
    }

    /**
     * Add tweet to intent tweet
     *
     * @param tweet
     */
    public void add(String tweet) {
        this.textList.add(tweet);
    }
}
