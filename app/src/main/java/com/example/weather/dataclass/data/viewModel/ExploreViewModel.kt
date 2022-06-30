package com.example.weather.dataclass.data.viewModel

import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.dataclass.data.ResponseWeatherApiKey
import com.example.weather.dataclass.data.currentweather.CurrentWeather
import com.example.weather.dataclass.data.nextWeek.ResponseNextWeek
import com.example.weather.network.ApiException
import com.example.weather.network.ApiExceptionNoInternet
import com.example.weather.network.Resource
import kotlinx.coroutines.launch
import xyz.teamprojectx.weather.data.repository.ExploreRepository
import com.example.weather.dataclass.data.location.ResponseLocation
import xyz.teamprojectx.weather.data.response.todayForecast.ResponseOneCall

class ExploreViewModel(val repository: ExploreRepository) : ViewModel() {

    val address: MutableLiveData<com.example.weather.dataclass.data.Address> = MutableLiveData()
    val isDayMode: MutableLiveData<Boolean> = MutableLiveData(false)


    private val weatherResponse: MutableLiveData<Resource<CurrentWeather>> = MutableLiveData()
    fun weather(lat: String, lon: String) = viewModelScope.launch {
        try {

            weatherResponse.value = Resource.Loading()
            val response = repository.weather(lat, lon)
            weatherResponse.value = Resource.Success(response)

        } catch (e: ApiException) {
            weatherResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            weatherResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            weatherResponse.value = Resource.Error(e.message)
        }

    }

    val todayForecastResponse: MutableLiveData<Resource<ResponseOneCall>> = MutableLiveData()
    fun todayForecast(lat: String, lon: String) = viewModelScope.launch {
        try {

            todayForecastResponse.value = Resource.Loading()
            val response = repository.todayForecast(lat, lon)
            todayForecastResponse.value = Resource.Success(response)

        } catch (e: ApiException) {
            todayForecastResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            todayForecastResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            todayForecastResponse.value = Resource.Error(e.message)
        }

    }


    val nextWeekResponse: MutableLiveData<Resource<ResponseNextWeek>> = MutableLiveData()
    fun nextWeek(lat: String, lon: String) = viewModelScope.launch {
        try {

            nextWeekResponse.value = Resource.Loading()
            val response = repository.nextWeek(lat, lon)
            nextWeekResponse.value = Resource.Success(response)

        } catch (e: ApiException) {
            nextWeekResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            nextWeekResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            nextWeekResponse.value = Resource.Error(e.message)
        }

    }


    val locationResponse: MutableLiveData<Resource<ResponseLocation>> = MutableLiveData()
    fun searchLocation(query: String) = viewModelScope.launch {
        try {

            locationResponse.value = Resource.Loading()
            val response = repository.searchLocation(query)
            locationResponse.value = Resource.Success(response)

        } catch (e: ApiException) {
            locationResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            locationResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            locationResponse.value = Resource.Error(e.message)
        }

    }

    /**
     * Login
     * */

    /*  val loginResponse:MutableLiveData<Resource<ResponseLogin>> = MutableLiveData()
      fun login(requestLogin: RequestLogin) = viewModelScope.launch {
          try {

              loginResponse.value = Resource.Loading()
              val response = repository.login(requestLogin)
              loginResponse.value = Resource.Success(response)

          }catch (e: ApiException) {
              loginResponse.value = Resource.Error(e.message, e.errorData)
          } catch (e: ApiExceptionNoInternet) {
              loginResponse.value = Resource.Error(e.message)
          } catch (e: Exception) {
              loginResponse.value = Resource.Error(e.message)
          }
      }
  */

    /**
     * Weather Api key
     * */

    val weatherApiKeyResponse: MutableLiveData<Resource<ResponseWeatherApiKey>> = MutableLiveData()
    fun weatherApiKey() = viewModelScope.launch {
        try {

            weatherApiKeyResponse.value = Resource.Loading()
            val response = repository.weatherApiKey()
            weatherApiKeyResponse.value = Resource.Success(response)

        } catch (e: ApiException) {
            weatherApiKeyResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            weatherApiKeyResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            weatherApiKeyResponse.value = Resource.Error(e.message)
        }
    }


    /**
     * Weather Api key
     * */

/*    val advertisementResponse:MutableLiveData<Resource<ResponseAds>> = MutableLiveData()
    fun advertisement() = viewModelScope.launch {
        try {

            advertisementResponse.value = Resource.Loading()
            val response = repository.advertisement()
            advertisementResponse.value = Resource.Success(response)

        }catch (e: ApiException) {
            advertisementResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            advertisementResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            advertisementResponse.value = Resource.Error(e.message)
        }
    }*/


    /**
     * About
     * */

/*    val aboutResponse:MutableLiveData<Resource<ResponseAbout>> = MutableLiveData()
    fun about() = viewModelScope.launch {
        try {

            aboutResponse.value = Resource.Loading()
            val response = repository.about()
            aboutResponse.value = Resource.Success(response)

        }catch (e: ApiException) {
            aboutResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            aboutResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            aboutResponse.value = Resource.Error(e.message)
        }
   */
}

/**
 * All blogs
 * */
/*
    val blogsResponse:MutableLiveData<Resource<ResponseBlogs>> = MutableLiveData()
    fun blogs() = viewModelScope.launch {
        try {

            blogsResponse.value = Resource.Loading()
            val response = repository.blogs()
            blogsResponse.value = Resource.Success(response)

        }catch (e: ApiException) {
            blogsResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            blogsResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            blogsResponse.value = Resource.Error(e.message)
        }
    }*/

/**
 *One signal api key response
 * */
/*
val oneSignalResponse: MutableLiveData<Resource<ResponseOneSignal>> = MutableLiveData()
fun oneSignal() = viewModelScope.launch {
    try {

        oneSignalResponse.value = Resource.Loading()
        val response = repository.oneSignal()
        oneSignalResponse.value = Resource.Success(response)

    } catch (e: ApiException) {
        oneSignalResponse.value = Resource.Error(e.message, e.errorData)
    } catch (e: ApiExceptionNoInternet) {
        oneSignalResponse.value = Resource.Error(e.message)
    } catch (e: Exception) {
        oneSignalResponse.value = Resource.Error(e.message)
    }
}*/


/**
 * store subscription data
 */
/*    val subscriptionResponse: MutableLiveData<Resource<ResponseSuccess>> = MutableLiveData()
    fun subscription(requestSubscription: RequestSubscription) = viewModelScope.launch {

        try {

            subscriptionResponse.value = Resource.Loading()
            val response = repository.subscription(requestSubscription)
            subscriptionResponse.value = Resource.Success(response)

        }catch (e: ApiException) {
            subscriptionResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            subscriptionResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            subscriptionResponse.value = Resource.Error(e.message)
        }


    }*/


/**
 * store subscription package
 */
/*    val subscriptionPackageResponse: MutableLiveData<Resource<ResponseSuccess>> = MutableLiveData()
    fun subscriptionPackage(requestPackageList: RequestPackageList) = viewModelScope.launch {

        try {

            subscriptionPackageResponse.value = Resource.Loading()
            val response = repository.subscriptionPackage(requestPackageList)
            subscriptionPackageResponse.value = Resource.Success(response)

        }catch (e: ApiException) {
            subscriptionPackageResponse.value = Resource.Error(e.message, e.errorData)
        } catch (e: ApiExceptionNoInternet) {
            subscriptionPackageResponse.value = Resource.Error(e.message)
        } catch (e: Exception) {
            subscriptionPackageResponse.value = Resource.Error(e.message)
        }


    }
    }*/



