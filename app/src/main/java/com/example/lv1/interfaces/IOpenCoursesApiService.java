package com.example.lv1.interfaces;

import com.example.lv1.models.APIResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IOpenCoursesApiService {
    @GET("/v1/courses")
    Call<APIResponse> getCourses();
}
