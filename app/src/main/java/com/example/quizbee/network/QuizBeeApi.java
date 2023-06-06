package com.example.quizbee.network;

import com.example.quizbee.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizBeeApi {

    public QuizBeeApiService createQuizBeeService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuizBeeApiService service = retrofit.create(QuizBeeApiService.class);
        return service;
    }
}
