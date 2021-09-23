package app.seven.weatherforcastapp.model

import app.seven.weatherforcastapp.data.dto.WeatherResponseDto
import com.google.gson.annotations.SerializedName

data class CurrentWeatherData(
    val clouds: Int,
    val dewPoint: Double,
    val dt: Int,
    val feelsLike: Double,
    val humidity: Int,
    val pressure: Int,
    val sunrise: Int,
    val sunset: Int,
    val temp: Double,
    val visibility: Int,
    val weather: List<WeatherData>,
    val windDeg: Int,
    val windGust: Double,
    val windSpeed: Double

)
