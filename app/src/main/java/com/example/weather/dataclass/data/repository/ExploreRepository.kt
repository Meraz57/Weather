package xyz.teamprojectx.weather.data.repository

import android.content.Context
import xyz.teamprojectx.weather.data.network.Api
import xyz.teamprojectx.weather.data.network.OpenWeatherApi
import xyz.teamprojectx.weather.data.network.SafeApiCall
import xyz.teamprojectx.weather.data.request.RequestLogin
import xyz.teamprojectx.weather.data.request.RequestPackageList
import xyz.teamprojectx.weather.data.request.RequestSubscription
import xyz.teamprojectx.weather.util.Const
import xyz.teamprojectx.weather.util.mSharePrefarence

class ExploreRepository(val context: Context) : SafeApiCall() {

    private var apiKey = ""
    private var token = ""

    init {
        mSharePrefarence.init(context)
        apiKey = mSharePrefarence.getApiKey() ?: ""
        token = mSharePrefarence.getToken() ?: ""
    }

    suspend fun weather(lat:String,lon:String) =
        apiRequest { OpenWeatherApi.invoke(context).weather(
            lat,lon,Const.CELSIUS, apiKey =  mSharePrefarence.getApiKey()?:""
        ) }

    suspend fun todayForecast(lat:String,lon:String) =
        apiRequest { OpenWeatherApi.invoke(context).todayForecast(
            lat,lon,Const.CELSIUS, apiKey = mSharePrefarence.getApiKey()?:""
        ) }

    suspend fun nextWeek(lat:String,lon:String) =
        apiRequest { OpenWeatherApi.invoke(context).nextWeek(
            lat,lon,Const.CELSIUS, apiKey = mSharePrefarence.getApiKey()?:""
        ) }

    suspend fun searchLocation(query:String) =
        apiRequest { OpenWeatherApi.invoke(context).searchLocation(
            query, apiKey = mSharePrefarence.getApiKey()?:""
        ) }

    suspend fun login(requestLogin: RequestLogin) =
        apiRequest { Api.invoke(context).login(requestLogin) }

    suspend fun weatherApiKey()=
        apiRequest { Api.invoke(context).weatherApiKey() }

    suspend fun advertisement() =
        apiRequest { Api.invoke(context).advertisement() }

    suspend fun about() =
        apiRequest { Api.invoke(context).about() }

    suspend fun blogs() =
        apiRequest { Api.invoke(context).blogs() }

    suspend fun oneSignal() =
        apiRequest { Api.invoke(context).oneSignal() }

    suspend fun subscription(requestSubscription: RequestSubscription) =
        apiRequest { Api.invoke(context).subscription(token,requestSubscription) }

    suspend fun subscriptionPackage(requestPackageList: RequestPackageList) =
        apiRequest { Api.invoke(context).subscriptionPackage( requestPackageList) }


}