package com.eco.route.feature.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eco.route.data.Repository
import com.eco.route.data.Zone
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val repository = Repository()
    private val _geoData: MutableLiveData<List<Zone>> = MutableLiveData()
    val geoLiveData: LiveData<List<Zone>>
            get() = _geoData


    fun startEcoMap(){
        for (i in 1..5){
            viewModelScope.launch {
                val response = repository.getZones(i)
                if(response.isSuccessful){
                    val listZone = response.body()?.let { _geoData.value?.plus(it.toTypedArray()) ?: it }
                    listZone?.let {
                        _geoData.value = listZone
                    }
                }
            }
        }

    }
}