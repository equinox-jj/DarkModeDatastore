package com.darkmodedatastore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.darkmodedatastore.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        setupListener()
        observer()
    }

    private fun observer() {
        viewModel.getTheme.observe(this@MainActivity) { isDarkMode ->
            checkDarkMode(isDarkMode)
        }
    }

    private fun setupListener() {
        binding.sMain.setOnCheckedChangeListener { _, isChecked ->
            lifecycleScope.launch {
                when (isChecked) {
                    true -> viewModel.setTheme(true)
                    false -> viewModel.setTheme(false)
                }
            }
        }
    }

    private fun checkDarkMode(isDarkMode: Boolean) {
        when (isDarkMode) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.sMain.isChecked = true
                binding.tvMain.text = "Dark Mode"
                binding.ivMain.setImageResource(R.drawable.ic_moon)
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.sMain.isChecked = false
                binding.tvMain.text = "Light Mode"
                binding.ivMain.setImageResource(R.drawable.ic_sun)
            }
        }
    }
}