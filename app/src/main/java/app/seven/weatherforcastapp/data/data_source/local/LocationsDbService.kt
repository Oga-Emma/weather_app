package app.seven.weatherforcastapp.data.data_source.local

import android.content.SharedPreferences
import android.util.Log
import app.seven.weatherforcastapp.model.LocationInfo
import app.seven.weatherforcastapp.model.toJson
import app.seven.weatherforcastapp.utils.Constants
import com.google.gson.Gson


interface LocationDbService {
    fun getSavedLocation(): LocationInfo?
    fun saveLocation(locationInfo: LocationInfo)
}

class LocationDbServiceImpl(private val sharedPreferences: SharedPreferences): LocationDbService{
    override fun getSavedLocation(): LocationInfo? {
        val json = sharedPreferences.getString(Constants.LOCATION_KEY, "")

        if(json.isNullOrBlank()) {
            return null
        }

        val gson = Gson()
        return gson.fromJson(json, LocationInfo::class.java)
    }

    override fun saveLocation(locationInfo: LocationInfo) {
        Log.i(this::class.java.simpleName, locationInfo.name)
       sharedPreferences.edit().putString(Constants.LOCATION_KEY, locationInfo.toJson()).apply()
    }

}