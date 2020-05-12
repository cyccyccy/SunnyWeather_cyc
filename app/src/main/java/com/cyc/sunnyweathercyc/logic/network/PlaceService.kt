package com.cyc.sunnyweathercyc.logic.network

import com.cyc.sunnyweathercyc.SunnyWeatherApplication
import com.cyc.sunnyweathercyc.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *查询城市信息
 */
interface PlaceService {
    @GET("v2/place?token=${SunnyWeatherApplication.TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String) : Call<PlaceResponse>
}