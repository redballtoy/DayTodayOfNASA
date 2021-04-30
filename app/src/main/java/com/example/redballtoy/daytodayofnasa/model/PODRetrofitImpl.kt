package com.example.redballtoy.daytodayofnasa.model

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

//Implementation of the interface for obtaining a picture
class PODRetrofitImpl {

    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl(): PictureOfTheDayAPI {
        val podRetrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
                .client(createOkHttpClient(PODInterceptor())) //added interceptor client
                .build()
        return podRetrofit.create(PictureOfTheDayAPI::class.java)
    }

    //Retrofit client that allows you to intercept what comes from the network and process or
    //log to debug
    //Interceptor - intercepts the response when it arrives before the response moves on
    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        //standard interceptor for logging
        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return httpClient.build()
    }

    //custom interceptor
    inner class PODInterceptor : Interceptor {

        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

            //needs to be returned after interception so that it can be used further
            //chain of responsibility pattern
            return chain.proceed(chain.request())
        }
    }
}
