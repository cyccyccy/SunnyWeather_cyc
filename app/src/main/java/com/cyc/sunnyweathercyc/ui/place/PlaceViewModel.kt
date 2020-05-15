package com.cyc.sunnyweathercyc.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.cyc.sunnyweathercyc.logic.Repository
import com.cyc.sunnyweathercyc.logic.dao.PlaceDao
import com.cyc.sunnyweathercyc.logic.model.Place

class PlaceViewModel :ViewModel() {
    fun savePlace(place: Place) = Repository.savePlace(place)
    fun getSavedPlace() = Repository.getSavedPlace()
    fun isSavedPlace() = Repository.isPlaceSaved()

    private val searchLiveData = MutableLiveData<String>()
    val placeList = ArrayList<Place>()
    val placeLiveData = Transformations.switchMap(searchLiveData){
        Repository.searchPlace(it)
    }

    fun searchPlaces(query: String){
        searchLiveData.value = query
    }
}