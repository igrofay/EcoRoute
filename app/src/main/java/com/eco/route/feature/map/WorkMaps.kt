package com.eco.route.feature.map

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.graphics.alpha
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
import kotlin.math.sign

class WorkMaps(context: Context) : OnMapReadyCallback {
    private var googleMap: GoogleMap? =null
    private val moscow = LatLng(55.75,37.6)
    override fun onMapReady(gMap: GoogleMap) {
        googleMap = gMap
        googleMap?.run {
            setMapStyle(MapStyleOptions.loadRawResourceStyle(appContext , R.raw.style_json))
            uiSettings.isCompassEnabled = true
            isBuildingsEnabled = true

        }

        moveCamera(moscow)

        val poiListener = GoogleMap.OnPoiClickListener {
            showToast(it.name)
        }


        googleMap?.run {
            val markerListener = GoogleMap.OnMarkerClickListener {
                it.remove()
                return@OnMarkerClickListener true
            }
//            setOnPoiClickListener(poiListener)
            setOnCircleClickListener {
                showToast(it.tag.toString())
            }
        }




    }

    // рисует круг
    fun addCircle(zone: Zone) {
        zone.sensors.forEach {
            googleMap?.addCircle(
                CircleOptions()
                    .center(LatLng(it[0], it[1]))
                    .radius(1000.0)
                    .fillColor(  Color.parseColor("#00FF0000"))
                    .strokeColor(Color.TRANSPARENT)
                    .strokeWidth(0f)
                    .clickable(true)
            )?.tag = zone.street
        }
    }

    fun clearMap() {
        googleMap?.clear()
    }

    fun addHeatmap(zone: Zone) {

        var latLngs = mutableListOf<LatLng>()

        zone.sensors.forEach {
            latLngs += LatLng(it[0], it[1])
        }

        val colors = intArrayOf(
            checkLevelContamination(zone.contaminationLevel),
            checkLevelContamination(zone.contaminationLevel)
        )
        val startPoints = floatArrayOf(0.1f, 1f)
        val gradient = Gradient(colors, startPoints)

        val provider = HeatmapTileProvider.Builder()
            .data(latLngs)
            .radius(40)
            .opacity(0.6)
            .gradient(gradient)
            .maxIntensity(0.0)
            .build()

        val overlay = googleMap?.addTileOverlay(TileOverlayOptions().tileProvider(provider))

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

        plo.fillColor(checkLevelContamination(zone.contaminationLevel))
        plo.strokeColor(Color.parseColor("#00000000"))
        plo.clickable(true)

        val polygon = googleMap?.addPolygon(plo)
        polygon?.tag = zone.street

        googleMap?.setOnPolygonClickListener {
            showToast(it.tag.toString())
        }

    }

    private fun checkLevelContamination(level: Double) : Int {
        val color = when {
            level<51 -> Color.parseColor("#2f00ff00")
            level<101 -> Color.parseColor("#2fffff00")
            level < 151 -> Color.parseColor("#2fFF8C00")
            level < 201 -> Color.parseColor("#2fdc143c")
            level < 300 -> Color.parseColor("#2f8b00ff")
            else -> Color.parseColor("#2fb00000")
        }
        return color

    }


}