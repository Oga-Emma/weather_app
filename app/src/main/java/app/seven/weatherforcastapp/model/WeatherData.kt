package app.seven.weatherforcastapp.model

import app.seven.weatherforcastapp.data.dto.Weather
import com.google.gson.annotations.SerializedName

data class WeatherData (
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

