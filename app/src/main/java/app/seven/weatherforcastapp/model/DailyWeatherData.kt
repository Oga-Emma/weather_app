package app.seven.weatherforcastapp.model

import app.seven.weatherforcastapp.data.dto.Temp
import app.seven.weatherforcastapp.data.dto.WeatherResponseDto
import com.google.gson.annotations.SerializedName

data class DailyWeatherData (
    val clouds: Int,
    val dewPoint: Double,
    val dt: Int,
    val humidity: Int,
    val moonPhase: Double,
    val moonrise: Int,
    val moonset: Int,
    val pressure: Int,
    val rain: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<WeatherData>,
    val windDeg: Int,
    val windGust: Double,
    val windSpeed: Double
        )
