package com.cyc.sunnyweathercyc.logic

import android.content.Context
import androidx.lifecycle.liveData
import com.cyc.sunnyweathercyc.logic.dao.PlaceDao
import com.cyc.sunnyweathercyc.logic.model.Place
import com.cyc.sunnyweathercyc.logic.model.Weather
import com.cyc.sunnyweathercyc.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 * 仓库层代码实现
 */
object Repository {
    fun savePlace(place: Place) = PlaceDao.savePlace(place)
    fun getSavedPlace() = PlaceDao.getSavedPlace()
    fun isPlaceSaved() = PlaceDao.isPlaceSaved()

    fun searchPlace(query :String) = fire(Dispatchers.IO) {
            val placeResponse = SunnyWeatherNetwork.searchPlace(query)
            if (placeResponse.status == "ok"){
                val place = placeResponse.places
                Result.success(place)
            }else{
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
    }

    fun refreshWeather(lng:String,lat:String) = fire(Dispatchers.IO){
            //使用coroutineScope创建协程作用域
            coroutineScope {
                //async函数中发起网络请求，再调用await（）可保证两个网络请求都成功响应后才执行下一步
                val deferredRealtime = async {
                    SunnyWeatherNetwork.getRealtimeWeather(lng,lat)
                }
                val deferredDaily = async {
                    SunnyWeatherNetwork.getDailyWeather(lng,lat)
                }
                val realtimeResponse = deferredRealtime.await()
                val dailyResponse = deferredDaily.await()
                if (realtimeResponse.status == "ok" && dailyResponse.status =="ok"){
                    val weather = Weather(realtimeResponse.result.realtime,dailyResponse.result.daily)
                    Result.success(weather)
                }else{
                    Result.failure(RuntimeException(
                        "realtime response status is ${realtimeResponse.status}\n"+
                        "daily response status is ${dailyResponse.status}"
                    ))
                }
            }
    }

    /**
     * 统一处理try catch
     */
    private fun <T> fire(context: CoroutineContext,block: suspend () ->Result<T>) = liveData<Result<T>>(context){
        val result = try {
            block()
        }catch (e:Exception){
            Result.failure<T>(e)
        }
        emit(result)
    }
}