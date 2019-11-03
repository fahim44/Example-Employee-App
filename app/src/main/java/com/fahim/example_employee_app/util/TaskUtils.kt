package com.fahim.example_employee_app.util

import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject


class TaskUtils @Inject constructor(private val context: Context) {

    fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo!!.isConnected
    }
}