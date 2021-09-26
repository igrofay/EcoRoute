package com.eco.route.feature.sheet

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.marginBottom
import androidx.fragment.app.Fragment
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import com.eco.route.R
import com.eco.route.data.DataStreet
import com.eco.route.feature.app.showToast
import com.eco.route.feature.sheet.DataStreetFragment.Companion.KEY_STREET
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*




fun BottomSheetBehavior<LinearLayout>.setStandardBehavior(subscriber:View){

    isFitToContents = false
    isGestureInsetBottomIgnored = true
    halfExpandedRatio = 0.25f
    addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
        override fun onStateChanged(bottomSheet: View, newState: Int) {

            when(newState){
                STATE_COLLAPSED->{
                    subscriber.translationY = 0.0f
                    subscriber.parentForAccessibility
                }
                STATE_HALF_EXPANDED -> {
                    subscriber.translationY = subscriber.marginBottom/2 - (bottomSheet.parent as View).height * halfExpandedRatio
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
        }

    })
}


fun BottomSheetBehavior<LinearLayout>.openDataStreet(dataStreet: DataStreet, fragment: NavHostFragment){
    state = STATE_EXPANDED
    NavHostFragment.findNavController(
        fragment
    ).navigate(R.id.dataStreetFragment ,
        Bundle().apply {
            putParcelable(KEY_STREET , dataStreet)
        })
}
