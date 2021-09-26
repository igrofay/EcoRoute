package com.eco.route.feature.sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eco.route.data.DataStreet
import com.eco.route.databinding.FragmentBottomDataStreetBinding



class DataStreetFragment : Fragment() {
    private lateinit var binding : FragmentBottomDataStreetBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDataStreetBinding.inflate(inflater, container, false)
        return binding.root
    }
   companion object {
       const val KEY_STREET = "KEY_STREET"
    }
}