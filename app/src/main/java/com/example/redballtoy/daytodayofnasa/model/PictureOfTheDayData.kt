package com.example.redballtoy.daytodayofnasa.model

//sealed class with subclasses describing the query result
sealed class PictureOfTheDayData {
    //if success get response data
    data class Success(val serverResponseData: PODServerResponseData) : PictureOfTheDayData()
    data class Error(val error: Throwable) : PictureOfTheDayData()
    data class Loading(val progress: Int?) : PictureOfTheDayData()
}
