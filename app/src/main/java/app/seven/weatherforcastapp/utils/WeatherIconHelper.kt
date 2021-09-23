package app.seven.weatherforcastapp.utils

import app.seven.weatherforcastapp.R

fun getWeatherIcon(icon: String): Int {
    val title = icon.lowercase()
    when {
        title.contains("cloudy") -> {
            return R.drawable.ic_cloudy
        }
        title.contains("rain") -> {
            return R.drawable.ic_rain
        }
        title.contains("rain") -> {
            return R.drawable.ic_rain
        }
        title.contains("storm") -> {
            return R.drawable.ic_storm
        }
        title.contains("wind") -> {
            return R.drawable.ic_windy
        }
        else -> {
            return R.drawable.ic_cloud_sun
        }
    }
}