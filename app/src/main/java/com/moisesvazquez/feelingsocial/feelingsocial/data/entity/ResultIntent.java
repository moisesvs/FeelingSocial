package com.moisesvazquez.feelingsocial.feelingsocial.data.entity;

import java.util.List;

/**
 * Created by moises on 13/10/16.
 */
public class ResultIntent {
    //    {"result": [
//    [ // Text 1
//    {"probability": 0.246, "label": "Health & Medicine"},
//    {"probability": 0.654, "label": "Alternative Medicine"}
//    ], [ // Text 2
//    {"probability": 0.172, "label": "Humanities"}
//    ]
//            ]}
    List<List<IntentProbability>> result;

    public List<List<IntentProbability>> getResult() {
        return result;
    }

    public static class IntentProbability {
        double probability;
        String label;

        public double getProbability() {
            return probability;
        }

        public String getLabel() {
            return label;
        }
    }
}
