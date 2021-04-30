package com.example.redballtoy.daytodayofnasa.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//call Interface for receiving picture
interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String,
                            @Query("date")  date: String
                           ): Call<PODServerResponseData>
}
