package app.seven.weatherforcastapp.ui.screens.select_location

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.databinding.FragmentSelectLocationBinding
import app.seven.weatherforcastapp.ui.screens.home.MainViewModel

class SelectLocationFragment : Fragment() {
    
    lateinit var binding: FragmentSelectLocationBinding

    companion object {
        fun newInstance() = SelectLocationFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSelectLocationBinding.inflate(layoutInflater)
        
        return binding.root
    }


}