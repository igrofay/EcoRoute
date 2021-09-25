package com.eco.route.feature.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.eco.route.R
import com.eco.route.databinding.FragmentMapsBinding
import com.eco.route.feature.app.App
import com.eco.route.feature.app.showToast

import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior



class MapsFragment : Fragment() {
    private val workMaps by lazy { WorkMaps(requireContext()) }
    private val model: MapViewModel by activityViewModels()
    private lateinit var binding: FragmentMapsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (ActivityCompat.checkSelfPermission(
                App.appContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                App.appContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            startMap()
        }
    }

    private fun startMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(workMaps)
        model.geoLiveData.observe( viewLifecycleOwner ){ it ->
            it.forEach{
                workMaps.addPolygon(it)
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                startMap()
            } else {
                showToast("Большинство функций не будут работать")
            }
        }
}