package app.seven.weatherforcastapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.seven.weatherforcastapp.data.data_source.local.LocationDbService
import app.seven.weatherforcastapp.model.CurrentWeatherData
import app.seven.weatherforcastapp.data.repository.WeatherRepository
import app.seven.weatherforcastapp.model.DailyWeatherData
import app.seven.weatherforcastapp.model.HourlyWeatherData
import app.seven.weatherforcastapp.model.LocationInfo
import app.seven.weatherforcastapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val weatherRepository: WeatherRepository, val locationDbService: LocationDbService) : ViewModel() {
    private val _currentWeatherLiveData = MutableLiveData<Resource<CurrentWeatherData>>()
    val currentWeatherLiveData: LiveData<Resource<CurrentWeatherData>> = _currentWeatherLiveData
    var currentWeatherJob: Job? = null

    private val _hourlyWeatherLiveData = MutableLiveData<Resource<List<HourlyWeatherData>>>()
    val hourlyWeatherLiveData: LiveData<Resource<List<HourlyWeatherData>>> = _hourlyWeatherLiveData
    var hourlyWeatherJob: Job? = null

    private val _dailyWeatherLiveData = MutableLiveData<Resource<List<DailyWeatherData>>>()
    val dailyWeatherLiveData: LiveData<Resource<List<DailyWeatherData>>> = _dailyWeatherLiveData
    var dailyWeatherJob: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun getCurrentWeatherData() {
        if(currentWeatherJob == null) {
            _currentWeatherLiveData.postValue(Resource.Loading())

            currentWeatherJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                Log.d(this::class.java.simpleName, "Starting...")
                val response = weatherRepository.getCurrentWeatherData(getLocation()!!.lat, getLocation()!!.lon)
                withContext(Dispatchers.Main) {
                    _currentWeatherLiveData.postValue(response)
                }
            }
        }
    }

    fun getHourlyWeatherData() {
        if(hourlyWeatherJob == null) {
            _hourlyWeatherLiveData.postValue(Resource.Loading())

            hourlyWeatherJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                val response = weatherRepository.getHourlyWeatherData(getLocation()!!.lat, getLocation()!!.lon)
                withContext(Dispatchers.Main) {
                    _hourlyWeatherLiveData.postValue(response)
                }
            }
        }
    }

    fun getDailyWeatherData() {
        if(dailyWeatherJob == null) {
            _dailyWeatherLiveData.postValue(Resource.Loading())

            dailyWeatherJob = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
                Log.d(this::class.java.simpleName, "Starting...")
                val response = weatherRepository.getDailyWeatherData(getLocation()!!.lat, getLocation()!!.lon)
                withContext(Dispatchers.Main) {
                    _dailyWeatherLiveData.postValue(response)
                }
            }
        }
    }

    fun getLocation(): LocationInfo? {
        return locationDbService.getSavedLocation()
    }

    fun saveLocation(locationInfo: LocationInfo) {
        if(currentWeatherJob != null){
            currentWeatherJob?.cancel()
            currentWeatherJob = null
        }

        if(dailyWeatherJob != null){
            dailyWeatherJob?.cancel()
            dailyWeatherJob = null
        }

        if(hourlyWeatherJob != null){
            hourlyWeatherJob?.cancel()
            hourlyWeatherJob = null
        }
        return locationDbService.saveLocation(locationInfo)
    }

    private fun onError(message: String) {
        _currentWeatherLiveData.postValue(Resource.Error(message))
    }

    override fun onCleared() {
        super.onCleared()
        currentWeatherJob?.cancel()
    }

}

class HomeViewModelFactory(private val weatherRepository: WeatherRepository, private val locationDbService: LocationDbService): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(weatherRepository = weatherRepository, locationDbService = locationDbService) as T
    }

}