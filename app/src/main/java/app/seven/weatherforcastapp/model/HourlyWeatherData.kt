package app.seven.weatherforcastapp.model

import app.seven.weatherforcastapp.data.dto.Weather
import com.google.gson.annotations.SerializedName

class HourlyWeatherData (
    val clouds: Int,
    val dewPoint: Double,
    val dt: Int,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val visibility: Int,
    val weather: List<WeatherData>,
    val windDeg: Int,
    val windGust: Double,
    val windSpeed: Double
 ){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HourlyWeatherData

        if (clouds != other.clouds) return false
        if (dewPoint != other.dewPoint) return false
        if (dt != other.dt) return false
        if (humidity != other.humidity) return false
        if (pressure != other.pressure) return false
        if (temp != other.temp) return false
        if (visibility != other.visibility) return false
        if (weather != other.weather) return false
        if (windDeg != other.windDeg) return false
        if (windGust != other.windGust) return false
        if (windSpeed != other.windSpeed) return false

        return true
    }

    override fun hashCode(): Int {
        var result = clouds
        result = 31 * result + dewPoint.hashCode()
        result = 31 * result + dt
        result = 31 * result + humidity
        result = 31 * result + pressure
        result = 31 * result + temp.hashCode()
        result = 31 * result + visibility
        result = 31 * result + weather.hashCode()
        result = 31 * result + windDeg
        result = 31 * result + windGust.hashCode()
        result = 31 * result + windSpeed.hashCode()
        return result
    }
}