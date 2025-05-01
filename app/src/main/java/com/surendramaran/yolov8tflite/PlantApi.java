package com.surendramaran.yolov8tflite;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PlantApi {
    @POST("/plant/save")
    Call<ResponseBody> uploadPlant(@Body RequestBody plantJson);
}
