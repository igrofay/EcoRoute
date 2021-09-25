package com.eco.route.feature.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eco.route.data.Repository
import com.eco.route.data.Zone
import com.eco.route.feature.app.showToast
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val repository = Repository()
    private val geoData : MutableLiveData<List<Zone>> = MutableLiveData()
    fun startEcoMap(){
        for (i in 1..5){
            viewModelScope.launch {
                val response = repository.getZones(i)
                if(response.isSuccessful){
                    val listZone = response.body()?.let { geoData.value?.plus(it.toTypedArray()) ?: it }
                    listZone?.let {
                        geoData.value = listZone
                    }
                }
            }
        }

    }
}