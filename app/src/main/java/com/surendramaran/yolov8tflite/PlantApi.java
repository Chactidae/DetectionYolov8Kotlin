package com.surendramaran.yolov8tflite;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PlantApi {
    @POST("/plant/save")
    Call<ResponseBody> uploadPlant(@Body RequestBody plantJson);

    @GET("/user/id-by-login")
    Call<Long> getUserIdByLogin(@Query("login") String login);
}
