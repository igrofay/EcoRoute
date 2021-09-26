package com.eco.route.feature.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eco.route.data.Repository
import com.eco.route.data.Zone
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel : ViewModel() {
    private val repository = Repository()
    private val _geoData: MutableLiveData<List<Zone>> = MutableLiveData()
    val geoLiveData: LiveData<List<Zone>>
            get() = _geoData


    fun startEcoMap(){
        for (i in 1..5){
            repository.getZones(i).enqueue(object : Callback<List<Zone>>{
                override fun onResponse(
                    call: Call<List<Zone>>,
                    response: Response<List<Zone>>
                ) {
                    val listZone = response.body()?.let { _geoData.value?.plus(it.toTypedArray()) ?: it }
                    listZone?.let {
                        _geoData.value = listZone
                    }
                }

                override fun onFailure(call: Call<List<Zone>>, t: Throwable) {
                }

            })
        }

    }
}