package app.seven.weatherforcastapp.ui.screens.weather_details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.databinding.FragmentWeatherDetailsBinding
import app.seven.weatherforcastapp.ui.MainActivity
import app.seven.weatherforcastapp.ui.adapters.DailyForcastAdapter
import app.seven.weatherforcastapp.ui.adapters.HourlyAdapter
import app.seven.weatherforcastapp.ui.screens.home.MainViewModel
import app.seven.weatherforcastapp.utils.Resource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherDetailsFragment : Fragment() {

    lateinit var binding: FragmentWeatherDetailsBinding
    companion object {
        fun newInstance() = WeatherDetailsFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherDetailsBinding.inflate(inflater)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.todayDateLabel.text = LocalDate.now().format(DateTimeFormatter.ofPattern("MMM, dd"))
        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel = (activity as MainActivity).viewModel
        setupHourlyRecyclerView()
        setupDailyRecyclerView()
    }

    private fun setupHourlyRecyclerView() {
        viewModel.getHourlyWeatherData()
        val adapter = HourlyAdapter()
        val hourlyRecyclerView = binding.hourlyRV
        hourlyRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        hourlyRecyclerView.adapter = adapter

        viewModel.hourlyWeatherLiveData.observe(viewLifecycleOwner, {
//            Log.d(this::class.java.simpleName, "Data => ${it.data}")
            if(it is Resource.Success){
                it.data?.let { it1 -> adapter.updateList(it1) }
            }
        })

    }

    private fun setupDailyRecyclerView() {
        viewModel.getDailyWeatherData()
        val adapter = DailyForcastAdapter()
        val dailyRecyclerView = binding.dailyForcastRv
        dailyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        dailyRecyclerView.adapter = adapter

        viewModel.dailyWeatherLiveData.observe(viewLifecycleOwner, {
//            Log.d(this::class.java.simpleName, "Data => ${it.data}")
            if(it is Resource.Success){
                it.data?.let { it1 -> adapter.updateList(it1) }
            }
        })

    }

}