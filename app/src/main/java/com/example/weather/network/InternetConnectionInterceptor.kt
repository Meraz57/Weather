package com.example.weather.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response


class InternetConnectionInterceptor(val context: Context) : Interceptor {



    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            throw ApiExceptionNoInternet("You don't have internet connection.")
        }
        return chain.proceed(chain.request())
    }


//    private  fun isNetworkAvailable() =
//        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).run {
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                    getNetworkCapabilities(activeNetwork)?.run {
//                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
//                                || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
//                                || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
//                    } ?: false
//                } else {
//
//                }
//
//        }


    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }




}

