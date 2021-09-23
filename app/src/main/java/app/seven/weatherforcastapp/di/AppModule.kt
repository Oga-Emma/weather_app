package app.seven.weatherforcastapp.di

import android.content.Context
import android.content.SharedPreferences
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.data.data_source.local.LocationDbService
import app.seven.weatherforcastapp.data.data_source.local.LocationDbServiceImpl
import app.seven.weatherforcastapp.data.data_source.remote.WeatherApi
import app.seven.weatherforcastapp.data.data_source.remote.WeatherApiService
import app.seven.weatherforcastapp.data.data_source.remote.WeatherApiServiceImpl
import app.seven.weatherforcastapp.data.repository.WeatherRepository
import app.seven.weatherforcastapp.data.repository.WeatherRepositoryImpl
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun weatherApi(): WeatherApi {
        return WeatherApi.getInstance()
    }

    @Singleton
    @Provides
    fun weatherApiService(weatherApi: WeatherApi): WeatherApiService {
        return WeatherApiServiceImpl(weatherApi)
    }

    @Singleton
    @Provides
    fun provideRepository(weatherApiService: WeatherApiService
    ): WeatherRepository{
        return WeatherRepositoryImpl(weatherApiService)
    }


    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )


    @Singleton
    @Provides
    fun provideSharedPreferenceInstance(
        @ApplicationContext context: Context
    ) = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)


    @Singleton
    @Provides
    fun provideLocationDbInstanceInstance(
        sharedPreferences: SharedPreferences
    ): LocationDbService{
        return LocationDbServiceImpl(sharedPreferences)
    }


}
