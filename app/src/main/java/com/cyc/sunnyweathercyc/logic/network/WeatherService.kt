package com.cyc.sunnyweathercyc.logic.network

import com.cyc.sunnyweathercyc.SunnyWeatherApplication
import com.cyc.sunnyweathercyc.logic.model.DailyResponse
import com.cyc.sunnyweathercyc.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * 1.获取实时天气信息
 * 2.获取未来天气信息
 */
interface WeatherService {
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeWeather(@Path("lng") lng:String,@Path("lat") lat : String): Call<RealtimeResponse>
    @GET("v2.5/${SunnyWeatherApplication.TOKEN}/{lng},{lat}/daily.json")
    fun getDailyWeather(@Path("lng") lng:String,@Path("lat") lat : String) :Call<DailyResponse>
}