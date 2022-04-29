package com.thelog.retrofitwithfirebase.service;

import com.thelog.retrofitwithfirebase.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @POST("create")
    Call<User> create(@Body User user);

    @GET("get")
    Call<List<User>> get();
}
