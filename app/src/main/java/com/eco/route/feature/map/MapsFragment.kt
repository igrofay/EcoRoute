package com.eco.route.feature.map

import android.Manifest
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.get
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.activityViewModels
import com.eco.route.R
import com.eco.route.data.Zone
import com.eco.route.databinding.FragmentMapsBinding
import com.eco.route.feature.app.App
import com.eco.route.feature.app.showToast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.eco.route.data.DataStreet

import com.eco.route.feature.sheet.openDataStreet
import com.eco.route.feature.sheet.setStandardBehavior
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapsFragment : Fragment() {

    private val workMaps by lazy { WorkMaps(requireContext()){
        model.openDataStreet(it).enqueue(object : Callback<DataStreet>{
            override fun onResponse(call: Call<DataStreet>, response: Response<DataStreet>) {
                response.body()?.let { data ->
                    BottomSheetBehavior.
                    from<LinearLayout>(binding.root.findViewById(R.id.bottomSheet)).openDataStreet(data,
                        binding.root.findViewById<FragmentContainerView>(R.id.fragmentContainerView).getFragment()!!
                    )
                }
            }
            override fun onFailure(call: Call<DataStreet>, t: Throwable) {}

        })
    } }


    private val model: MapViewModel by activityViewModels()
    private lateinit var binding: FragmentMapsBinding
    private var  isClick = false
    private val observer = Observer<List<Zone>>{
            it->
        it.forEach{
            Handler().post {
                workMaps.addHeatmap(it)
                workMaps.addCircle(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapsBinding.inflate(inflater , container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        BottomSheetBehavior.from<LinearLayout>(view.findViewById(R.id.bottomSheet)).setStandardBehavior(binding.containerView)
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
        binding.ecoMapBtn.setOnClickListener {
            if (isClick){
                workMaps.clearMap()
                model.geoLiveData.removeObserver(observer)
                binding.outline.visibility = View.INVISIBLE
            }else{
                if(model.geoLiveData.value == null) model.startEcoMap()
                model.geoLiveData.observe( viewLifecycleOwner, observer )
                binding.outline.visibility = View.VISIBLE
            }
            isClick = !isClick
        }
        binding.buildRoute.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.buildRouteFragment)
        }
    }

    private fun startMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(workMaps)
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