package com.cyc.sunnyweathercyc.logic

import androidx.lifecycle.liveData
import com.cyc.sunnyweathercyc.logic.model.Place
import com.cyc.sunnyweathercyc.logic.network.SunnyWeatherNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import java.lang.RuntimeException


object Repository {
    fun searchPlace(query :String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = SunnyWeatherNetwork.searchPlace(query)
            if (placeResponse.status == "ok"){
                val place = placeResponse.places
                Result.success(place)
            }else{
                Result.failure<List<Place>>(RuntimeException("response status is ${placeResponse.status}"))
            }
        }catch (e :Exception){
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}