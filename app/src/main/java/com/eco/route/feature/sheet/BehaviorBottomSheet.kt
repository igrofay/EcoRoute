package com.eco.route.feature.sheet

import android.view.View
import android.widget.LinearLayout
import androidx.core.view.marginBottom
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HALF_EXPANDED

fun BottomSheetBehavior<LinearLayout>.setBehavior(subscriber:View){
    isFitToContents = false
    halfExpandedRatio = 0.25f
    addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            
            when(newState){
                STATE_COLLAPSED->{
                    subscriber.translationY = 0.0f
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