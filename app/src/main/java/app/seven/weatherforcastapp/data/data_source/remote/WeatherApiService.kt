package app.seven.weatherforcastapp.data.data_source.remote

import app.seven.weatherforcastapp.data.dto.WeatherResponseDto
import app.seven.weatherforcastapp.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

import okhttp3.logging.HttpLoggingInterceptor

interface WeatherApi {
//    appid=4af31a9b61ea86768f63d44e023a4a62
    @GET("data/2.5/onecall?units=metric")
    suspend fun getWeatherData(@Query("lat") lat: Double, @Query("lon") lon: Double) : Response<WeatherResponseDto>

    companion object {
        var retrofitService: WeatherApi? = null
        fun getInstance() : WeatherApi {
            if (retrofitService == null) {

                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)

                val client: OkHttpClient = OkHttpClient.Builder()
                    .addNetworkInterceptor(object: Interceptor {
                        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

                            val url = chain.request().url.newBuilder()
                                .addQueryParameter("appid", Constants.API_KEY)
                                .build()

                            return chain.proceed(chain.request().newBuilder()
                                .url(url).build())
                        }

                    })
                    .addInterceptor(logging)
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()


                retrofitService = retrofit.create(WeatherApi::class.java)
            }
            return retrofitService!!
        }

    }
}

interface WeatherApiService {
    suspend fun getWeatherData(lat: Double, lon: Double) : Response<WeatherResponseDto>
}

class WeatherApiServiceImpl(private val weatherApi: WeatherApi): WeatherApiService{
    override suspend fun getWeatherData(lat: Double, lon: Double) = weatherApi.getWeatherData(lat, lon)

}