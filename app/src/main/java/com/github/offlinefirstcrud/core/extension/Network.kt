package com.github.offlinefirstcrud.core.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        .or(actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        .or(actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
}
