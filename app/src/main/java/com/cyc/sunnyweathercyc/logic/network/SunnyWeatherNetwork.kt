package com.cyc.sunnyweathercyc.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * 对所有网络请求进行封装
 */
object SunnyWeatherNetwork {
    private val placeService = ServiceCreator.create(PlaceService::class.java)
    private val weatherService = ServiceCreator.create(WeatherService::class.java)

    suspend fun searchPlace(query: String) = placeService.searchPlaces(query).await()
    suspend fun getDailyWeather(lng:String,lat:String) = weatherService.getDailyWeather(lng,lat).await()
    suspend fun getRealtimeWeather(lng: String,lat: String) = weatherService.getRealtimeWeather(lng,lat).await()

    private suspend fun <T> Call<T>.await(): T{
        return suspendCoroutine {
            enqueue(object :Callback<T>{
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null){
                        it.resume(body)
                    }else{
                        it.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }
            })
        }
    }
}