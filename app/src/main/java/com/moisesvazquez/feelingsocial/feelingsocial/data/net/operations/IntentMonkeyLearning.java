package com.moisesvazquez.feelingsocial.feelingsocial.data.net.operations;

import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.TextList;
import com.moisesvazquez.feelingsocial.feelingsocial.data.entity.ResultIntent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IntentMonkeyLearning {
    @Headers({  "Authorization:Token bd9e1c5153791b59e65c9b8279930c2a0a576d06",
                "Content-Type: application/json"})
    @POST("/v2/classifiers/cl_Xn8FJhGR/classify/?")
    Call<ResultIntent> build(
//        @Path("classifier_id") String classifier_id,
            @Body TextList intentTweet
    );
}