package app.seven.weatherforcastapp.ui.screens.home

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.seven.weatherforcastapp.databinding.FragmentHomeBinding
import app.seven.weatherforcastapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

import android.content.Intent
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.model.CurrentWeatherData
import app.seven.weatherforcastapp.model.LocationInfo
import app.seven.weatherforcastapp.utils.getWeatherIcon
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.google.android.libraries.places.widget.AutocompleteActivity
import java.text.DateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@AndroidEntryPoint
class HomeFragment constructor(): Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        var inflater.inflate(R.layout.home_fragment, container, false)

        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.locationLabel.setOnClickListener { gotoSelectLocation() }
        binding.forcastReportBtn.setOnClickListener { gotoForcastFragment() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as MainActivity).viewModel


        val location = viewModel.getLocation();
        if(location == null){
            showNoLocation()
        }else{
            binding.locationLabel.text = location.name
            getWeather()
        }

    }

    val AUTOCOMPLETE_REQUEST_CODE = 1
    private fun setupAutoComplete(){
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), getString(R.string.api_key), Locale.US);
        }

        val fields: List<Place.Field> = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)

        // Start the autocomplete intent.

        // Start the autocomplete intent.
        val intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(requireContext())
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            when (resultCode) {
                RESULT_OK -> {
                    val place = Autocomplete.getPlaceFromIntent(data!!)

                    Log.i(this::class.java.simpleName, "Place: " + place.name + ", " + place.id + ", " + place.latLng)

                    if(place.name != null && place.latLng != null) {
                        viewModel.saveLocation(
                            LocationInfo(
                                place.name!!,
                                place.latLng!!.latitude,
                                place.latLng!!.longitude
                            )
                        )
                        binding.locationLabel.text = place.name!!
                        getWeather()
                    }
                }
                AutocompleteActivity.RESULT_ERROR -> {
        //                // TODO: Handle the error.
        //                val status: Status = Autocomplete.getStatusFromIntent(data!!)
        //                Log.i(TAG, status.getStatusMessage())
                }
                RESULT_CANCELED -> {
                    // The user canceled the operation.
                }
            }
            return
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun getWeather(){
        Log.d(this::class.java.simpleName, "Getting weather data")
        viewModel.getCurrentWeatherData()
        viewModel.currentWeatherLiveData.observe(viewLifecycleOwner, {event ->
            when {
                event.message != null -> {
                    Log.d(this::class.java.simpleName, event.message)
                    showError(event.message)
                }
                event.data != null -> {
                    Log.d(this::class.java.simpleName, "${event.data}")
                    showWeatherData(event.data)
                }
                else -> {
                    showLoading()
                }
            }
        })
    }

    private fun showNoLocation(){
        binding.weatherLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.noLocationLayout.visibility = View.VISIBLE
        binding.setLocationBtn.setOnClickListener {
            setupAutoComplete()
        }
    }

    private fun showLoading(){
        binding.weatherLayout.visibility = View.GONE
        binding.noLocationLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.VISIBLE
    }

    private fun showWeatherData(data: CurrentWeatherData) {

        binding.noLocationLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.weatherLayout.visibility = View.VISIBLE

        binding.dateLabel.text = "Today, ${LocalDate.now().format(DateTimeFormatter.ofPattern(" dd MMMM"))}"
        binding.humidityLabel.text = "${data.humidity}%"
        binding.windLabel.text = "${data.windSpeed} km/h"
        binding.weatherLabel.text = data.weather[0].description
        binding.temperatureLabel.text = "${data.temp}"+ getString(R.string.degreee)

        binding.weatherImageView.setImageResource(getWeatherIcon(data.weather[0].main))
    }

    private fun showError(errorMessage: String){
        binding.errorMessageLabel.text = errorMessage
        binding.noLocationLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.weatherLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }

    private fun gotoForcastFragment(){
        findNavController().navigate(R.id.action_homeFragment_to_weatherDetailsFragment)
    }

    private fun gotoSelectLocation(){
        setupAutoComplete()
        return;
        val navHostFragment =
            parentFragmentManager.findFragmentById(R.id.nav_host_fragment) as HomeFragment

        navHostFragment.findNavController().navigate(R.id.action_homeFragment_to_selectLocationFragment)
    }

}