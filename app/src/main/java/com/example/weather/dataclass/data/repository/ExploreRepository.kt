package xyz.teamprojectx.weather.data.repository

import android.content.Context
import coil.memory.MemoryCache.Key.Companion.invoke
import com.example.weather.network.API
import com.example.weather.network.OpenWeatherApi
import com.example.weather.network.SafeApiCall
import com.example.weather.ui.mSharePrefarence
import com.example.weather.util.Const

class ExploreRepository(val context: Context) : SafeApiCall() {

    private var apiKey = ""
    private var token = ""

    init {
        mSharePrefarence.init(context)
        apiKey = mSharePrefarence.getApiKey() ?: ""
        token = mSharePrefarence.getToken() ?: ""
    }

    suspend fun weather(lat: String, lon: String) =
        apiRequest {
            OpenWeatherApi.invoke(context).weather(
                lat, lon, Const.CELSIUS, apiKey = mSharePrefarence.getApiKey() ?: ""
            )
        }

    suspend fun todayForecast(lat: String, lon: String) =
        apiRequest {
            OpenWeatherApi.invoke(context).todayForecast(
                lat, lon, Const.CELSIUS, apiKey = mSharePrefarence.getApiKey() ?: ""
            )
        }

    suspend fun nextWeek(lat: String, lon: String) =
        apiRequest {
            OpenWeatherApi.invoke(context).nextWeek(
                lat, lon, Const.CELSIUS, apiKey = mSharePrefarence.getApiKey() ?: ""
            )
        }

    suspend fun searchLocation(query: String) =
        apiRequest {
            OpenWeatherApi.invoke(context).searchLocation(
                query, apiKey = mSharePrefarence.getApiKey() ?: ""
            )
        }

//    suspend fun login(requestLogin: RequestLogin) =
//        apiRequest { Api.invoke(context).login(requestLogin) }

    suspend fun weatherApiKey() =
        apiRequest { API.invoke(context).weatherApiKey() }

    /*  suspend fun advertisement() =
          apiRequest { API.invoke(context).advertisement() }*/

    /*   suspend fun about() =
           apiRequest { API.invoke(context).about() }*/

/*    suspend fun blogs() =
        apiRequest { API.invoke(context).blogs() }*/

    suspend fun oneSignal() =
        apiRequest { API.invoke(context).oneSignal() }
/*
    suspend fun subscription(requestSubscription: RequestSubscription) =
        apiRequest { API.invoke(context).subscription(token,requestSubscription) }

    suspend fun subscriptionPackage(requestPackageList: RequestPackageList) =
        apiRequest { API.invoke(context).subscriptionPackage( requestPackageList) }*/


}