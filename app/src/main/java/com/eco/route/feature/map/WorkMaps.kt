package com.eco.route.feature.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.eco.route.R
import com.eco.route.data.Zone
import com.eco.route.feature.app.App.Companion.appContext
import com.eco.route.feature.app.showToast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider

class WorkMaps(context: Context) : OnMapReadyCallback {
    private var googleMap: GoogleMap? =null
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap?.run {
            setMapStyle(MapStyleOptions.loadRawResourceStyle(appContext , R.raw.style_json))
            uiSettings.isCompassEnabled = true
            isBuildingsEnabled = true
        }

        val poiListener = GoogleMap.OnPoiClickListener {
            showToast(it.name)
        }
        googleMap?.run {
            val markerListener = GoogleMap.OnMarkerClickListener {
                it.remove()
                return@OnMarkerClickListener true
            }
            setOnPoiClickListener(poiListener)
            setOnMarkerClickListener(markerListener)
            setOnMapLongClickListener {
            }
        }




    }

    // рисует круг
    fun addCircle() {
        val seattle = LatLng(47.6062095, -122.3320708)
        val circleOptions = CircleOptions()
        circleOptions.center(seattle)
        circleOptions.radius(8500.0)
        circleOptions.fillColor(  Color.parseColor("#7FFF0000"))
        circleOptions.strokeColor(Color.TRANSPARENT)
        circleOptions.strokeWidth(0f)
        googleMap?.addCircle(circleOptions)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(seattle))
    }

    fun addHeatmap() {
        val issaquah = LatLng(47.5301011, -122.0326191)
        val seattle = LatLng(47.6062095, -122.3320708)
        val bellevue = LatLng(47.6101497, -122.2015159)
        val sammamish = LatLng(47.6162683, -122.0355736)
        val redmond = LatLng(47.6739881, -122.121512)

        var latLngs = listOf<LatLng>(issaquah, redmond, sammamish, bellevue, seattle)

        val colors = intArrayOf(
            Color.rgb(255, 0, 0),  // green
            Color.rgb(255, 0, 0) // red
        )
        val startPoints = floatArrayOf(0.2f, 1f)
        val gradient = Gradient(colors, startPoints)

        val provider = HeatmapTileProvider.Builder()
            .data(latLngs)
            .radius(50)
            .opacity(0.5)
            .gradient(gradient)
            .maxIntensity(0.0)
            .build()



        val overlay = googleMap?.addTileOverlay(TileOverlayOptions().tileProvider(provider))

        moveCamera(seattle)

    }

//    private fun createNewMarkers(markers: List<LatLng>)  {
//        markers.forEach {
////            if(it.coor == "") return@forEach
////            val lat = it.coor.split(", ")
//            val latLng = LatLng(lat[0].toDouble(), lat[1].toDouble())
//            val marker = googleMap.addMarker(
//                MarkerOptions()
//                    .position(latLng)
//                    .icon(bitmapDescriptorFromVector(this, R.drawable.ic_orangemarker))
//                    .title(it.type)
//            ) ?: return
//            marker.tag = it.id
//        }
//    }

    private fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorDrawableResourceId: Int
    ): BitmapDescriptor? {
        val background = ContextCompat.getDrawable(context, com.eco.route.R.drawable.marker_background)
        background!!.setBounds(0, 0, background.intrinsicWidth, background.intrinsicHeight)
        val vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId)
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            background.intrinsicWidth,
            background.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        background.draw(canvas)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    fun moveCamera(latLng: LatLng) {
        googleMap?.moveCamera(CameraUpdateFactory.zoomTo(10F))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLng(latLng))
    }

//    fun addPolyLine(markers: List<PlaceObject>) {
//
//        var plo = PolylineOptions()
//        googleMap.clear()
//        createNewMarkers(listMutant)
//
//        plo.color(Color.BLACK)
//        plo.geodesic(true)
//        plo.startCap(RoundCap())
//        plo.width(8f)
//        plo.jointType(JointType.BEVEL)
//        googleMap.addPolyline(plo)
//    }

    fun addPolygon(zone: Zone) {

        var plo = PolygonOptions()
//            googleMap.clear()
//            createNewMarkers(listMutant)

        zone.sensors.forEach {
            plo.add(LatLng(it[0], it[1]))
        }

        plo.fillColor(Color.parseColor("#7FFF0000"))
        plo.strokeColor(Color.parseColor("#00000000"))
        plo.clickable(true)

        val polygon = googleMap?.addPolygon(plo)
        polygon?.tag = zone.street

        googleMap?.setOnPolygonClickListener {
            showToast(it.id)
        }

    }


}