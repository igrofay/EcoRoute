package com.eco.route.feature.app

import android.widget.Toast

fun showToast(message: Int) {
    Toast.makeText(App.appContext, message, Toast.LENGTH_LONG).show()
}

fun showToast(message: String) {
    Toast.makeText(App.appContext, message, Toast.LENGTH_LONG).show()
}

