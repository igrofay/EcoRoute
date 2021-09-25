package com.eco.route.feature.bottom

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eco.route.R
import com.eco.route.databinding.FragmentBottomDataStreetBinding
import com.eco.route.databinding.FragmentBottomSheetBinding
import com.eco.route.databinding.FragmentMapsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior

import com.google.android.material.bottomsheet.BottomSheetDialog




class DataStreetFragment : Fragment() {
    private lateinit var binding : FragmentBottomDataStreetBinding
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDataStreetBinding.inflate(inflater, container, false)
        return binding.root
    }
}