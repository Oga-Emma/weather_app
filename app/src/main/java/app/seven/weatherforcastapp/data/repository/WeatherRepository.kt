package app.seven.weatherforcastapp.data.repository

import app.seven.weatherforcastapp.data.dto.toCurrentWeatherData
import app.seven.weatherforcastapp.data.data_source.remote.WeatherApiService
import app.seven.weatherforcastapp.data.dto.WeatherResponseDto
import app.seven.weatherforcastapp.data.dto.toDailyWeatherData
import app.seven.weatherforcastapp.data.dto.toHourlyWeatherData
import app.seven.weatherforcastapp.model.CurrentWeatherData
import app.seven.weatherforcastapp.model.DailyWeatherData
import app.seven.weatherforcastapp.model.HourlyWeatherData
import app.seven.weatherforcastapp.utils.Resource

interface WeatherRepository {
    suspend fun getCurrentWeatherData(lat: Double, lon: Double) : Resource<CurrentWeatherData>
    suspend fun getHourlyWeatherData(lat: Double, lon: Double) : Resource<List<HourlyWeatherData>>
    suspend fun getDailyWeatherData(lat: Double, lon: Double) : Resource<List<DailyWeatherData>>
}

class WeatherRepositoryImpl(private val weatherApiService: WeatherApiService): WeatherRepository{
    var res: WeatherResponseDto? = null
    var latLon: String? = null
    override suspend fun getCurrentWeatherData(lat: Double, lon: Double): Resource<CurrentWeatherData> {
        if(res != null && latLon == "$lat|$lon"){
            return Resource.Success(res!!.current.toCurrentWeatherData())
        }
        val data = weatherApiService.getWeatherData(lat = lat, lon = lon)
        if(data.isSuccessful){
            res = data.body()!!
            latLon = "$lat|$lon"
            return Resource.Success(data.body()!!.current.toCurrentWeatherData())
        }
        return Resource.Error(message = data.message())
    }

    override suspend fun getHourlyWeatherData(lat: Double, lon: Double): Resource<List<HourlyWeatherData>> {
        if(res != null && latLon == "$lat|$lon"){
            return Resource.Success(res!!.hourly.map { it.toHourlyWeatherData() })
        }

        val data = weatherApiService.getWeatherData(lat = lat, lon = lon)
        if(data.isSuccessful){
            res = data.body()!!
            latLon = "$lat|$lon"
            return getHourlyWeatherData(lat, lon)
        }
        return Resource.Error(message = data.message())
    }

    override suspend fun getDailyWeatherData(lat: Double, lon: Double): Resource<List<DailyWeatherData>> {
        if(res != null && latLon == "$lat|$lon"){
            return Resource.Success(res!!.daily.map { it.toDailyWeatherData() })
        }

        val data = weatherApiService.getWeatherData(lat = lat, lon = lon)
        if(data.isSuccessful){
            res = data.body()!!
            latLon = "$lat|$lon"
            return getDailyWeatherData(lat, lon)
        }
        return Resource.Error(message = data.message())
    }


}