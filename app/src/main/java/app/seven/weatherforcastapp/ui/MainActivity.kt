package app.seven.weatherforcastapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import app.seven.weatherforcastapp.R
import app.seven.weatherforcastapp.databinding.ActivityMainBinding
import app.seven.weatherforcastapp.ui.screens.home.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.background = ContextCompat.getDrawable(this, R.mipmap.bacground)


        val mainVM : MainViewModel by viewModels()
        viewModel = mainVM
    }
}