package app.seven.weatherforcastapp.data.dto


import app.seven.weatherforcastapp.model.CurrentWeatherData
import app.seven.weatherforcastapp.model.DailyWeatherData
import app.seven.weatherforcastapp.model.HourlyWeatherData
import app.seven.weatherforcastapp.model.WeatherData
import com.google.gson.annotations.SerializedName

data class WeatherResponseDto(
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>,
    @SerializedName("hourly")
    val hourly: List<Hourly>,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
) {
    data class Current(
        @SerializedName("clouds")
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("sunrise")
        val sunrise: Int,
        @SerializedName("sunset")
        val sunset: Int,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double
    )

    data class Daily(
        @SerializedName("clouds")
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("moon_phase")
        val moonPhase: Double,
        @SerializedName("moonrise")
        val moonrise: Int,
        @SerializedName("moonset")
        val moonset: Int,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("rain")
        val rain: Double,
        @SerializedName("sunrise")
        val sunrise: Int,
        @SerializedName("sunset")
        val sunset: Int,
        @SerializedName("temp")
        val temp: Temp,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double
    )

    data class Hourly(
        @SerializedName("clouds")
        val clouds: Int,
        @SerializedName("dew_point")
        val dewPoint: Double,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("pop")
        val pop: Double,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind_deg")
        val windDeg: Int,
        @SerializedName("wind_gust")
        val windGust: Double,
        @SerializedName("wind_speed")
        val windSpeed: Double
    )
}

data class Temp(
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("min")
    val min: Double,
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("night")
    val night: Double
)


data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)


fun List<Weather>.toWeatherDataList(): List<WeatherData> {
    return this.map { it -> WeatherData(
        description = it.description,
        icon = it.icon,
        id = it.id,
        main = it.main,
    ) }
}

fun Weather.toWeatherData(): WeatherData {
    return WeatherData(
        description = this.description,
        icon = this.icon,
        id = this.id,
        main = this.main,
    )
}

fun WeatherResponseDto.Current.toCurrentWeatherData(): CurrentWeatherData {
    return CurrentWeatherData(
        clouds = this.clouds,
        dewPoint = this.dewPoint,
        dt = this.dt,
        feelsLike = this.feelsLike,
        humidity = this.humidity,
        pressure = this.pressure,
        sunrise = this.sunrise,
        sunset = this.sunset,
        temp = this.temp,
        visibility = this.visibility,
        weather = this.weather.toWeatherDataList(),
        windDeg = this.windDeg,
        windGust = this.windGust,
        windSpeed = this.windSpeed,
    )
}


fun WeatherResponseDto.Daily.toDailyWeatherData(): DailyWeatherData {
    return DailyWeatherData(
        clouds = this.clouds,
        dewPoint = this.dewPoint,
        dt = this.dt,
        humidity = this.humidity,
        moonPhase = this.moonPhase,
        moonrise = this.moonrise,
        moonset = this.moonset,
        pressure = this.pressure,
        rain = this.rain,
        sunrise = this.sunrise,
        sunset = this.sunset,
        temp = this.temp,
        weather = this.weather.toWeatherDataList(),
        windDeg = this.windDeg,
        windGust = this.windGust,
        windSpeed = this.windSpeed,
    )
}

fun WeatherResponseDto.Hourly.toHourlyWeatherData(): HourlyWeatherData {
    return HourlyWeatherData(
        clouds = this.clouds,
                dewPoint = this.dewPoint,
                dt = this.dt,
                humidity = this.humidity,
                pressure = this.pressure,
                temp = this.temp,
                visibility = this.visibility,
                weather = this.weather.toWeatherDataList(),
                windDeg = this.windDeg,
                windGust = this.windGust,
                windSpeed = this.windSpeed,
    )
}