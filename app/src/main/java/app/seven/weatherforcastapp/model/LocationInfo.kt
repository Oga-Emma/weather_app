package app.seven.weatherforcastapp.model

import com.google.gson.Gson




class LocationInfo (val name: String, val lat: Double, val lon: Double)

fun LocationInfo.toJson(): String{
    val gson = Gson()
    val json = gson.toJson(this)
    return json
}
