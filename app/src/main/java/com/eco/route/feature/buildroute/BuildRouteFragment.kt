package com.eco.route.feature.buildroute

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import com.eco.route.R


class BuildRouteFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                var str1 = rememberSaveable { mutableStateOf("") }
                var str2 = rememberSaveable { mutableStateOf("") }
                Column {
                    BarRoute(str1.value , {str1.value = it} , str2.value , {str2.value = it})
                }
            }
        }
    }

    @Composable
    fun BarRoute(str1: String , input1 : (String) -> Unit , str2: String , input2 : (String) -> Unit ){
        Row {
            Image(painter = painterResource(R.drawable.ic_back), contentDescription = "Назад")
            Column(Modifier.weight(1f)) {
                Row {
                    TextField(value = str1 , onValueChange = input1 )
                }
                Row {
                    //Image(painter = painterResource(), contentDescription = )
                    TextField(value = str2 , onValueChange = input2 )
                }
            }

        }
    }
}