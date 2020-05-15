package com.manwinder.thescoreinterviewapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.manwinder.thescoreinterviewapp.App

object NetworkUtil {
    fun isNetworkConnected(): Boolean {
        val cm = App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT < 23) {
            return cm.activeNetworkInfo?.isConnected ?: false
        } else {
            cm.activeNetwork?.let {
                val networkCapabilities = cm.getNetworkCapabilities(it) ?: return false
                return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
            }
        }
        return false
    }
}