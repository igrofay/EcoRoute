package com.eco.route.feature.sheet

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.eco.route.data.DataStreet
import com.eco.route.databinding.FragmentBottomDataStreetBinding



class DataStreetFragment : Fragment() {
    val dataStreet : DataStreet? by lazy {
        arguments?.getParcelable(KEY_STREET)
    }
    private lateinit var binding : FragmentBottomDataStreetBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomDataStreetBinding.inflate(inflater, container, false)
        binding.street.text = dataStreet?.name
        binding.levelContamination.text = dataStreet?.values?.aqi?.let {
            checkLevelContaminationReturnText(
                it
            )
        }
        dataStreet?.values?.aqi?.let {
            checkLevelContamination(
                it
            )
        }?.let { binding.levelContamination.setTextColor(it) }

        binding.temperature.text = dataStreet?.values?.temperature.toString()
        binding.humidity.text = dataStreet?.values?.humidity.toString()
        binding.los.text = dataStreet?.values?.los.toString()
        binding.dustPm1.text = dataStreet?.values?.dustPm1.toString()
        binding.dustPm25.text = dataStreet?.values?.dustPm25.toString()
        binding.dustPm10.text = dataStreet?.values?.dustPm10.toString()
        binding.formaldehyde.text = dataStreet?.values?.formaldehyde.toString()
        binding.aqi.text = dataStreet?.values?.aqi.toString()
        binding.pressure.text = dataStreet?.values?.pressure.toString()
        binding.co2.text = dataStreet?.values?.co2.toString()

        return binding.root
    }
   companion object {
       const val KEY_STREET = "KEY_STREET"
    }



    private fun checkLevelContamination(level: Double) : Int {
        val color = when {
            level<51 -> Color.parseColor("#00ff00")
            level<101 -> Color.parseColor("#ffff00")
            level < 151 -> Color.parseColor("#FF8C00")
            level < 201 -> Color.parseColor("#dc143c")
            level < 300 -> Color.parseColor("#8b00ff")
            else -> Color.parseColor("#b00000")
        }
        return color
    }

    private fun checkLevelContaminationReturnText(level: Double) : String {
        val text = when {
            level<51 -> "Низкий"
            level<101 -> "Умеренный"
            level < 151 -> "Нездоровый для чувствительных групп"
            level < 201 -> "Нездоровый"
            level < 300 -> "Очень нездоровый"
            else -> "Опасный"
        }
        return text
    }

}