package com.example.quizbee.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QuizModule {
    @SerializedName("_id")
    private String id;
    private Module module;
    private ArrayList<Question> questions;

}
