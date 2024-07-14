package dev.anonymous.weatherapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dev.anonymous.weatherapp.databinding.ActivityMainBinding
import dev.anonymous.weatherview.model.DayModel
import dev.anonymous.weatherview.model.WeatherStatus

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val listDays = listOf(
            DayModel(13, "1:00", WeatherStatus.Moon),
            DayModel(12, "2:00", WeatherStatus.NightCloudy),
            DayModel(12, "3:00", WeatherStatus.NightCloudy),
            DayModel(13, "4:00", WeatherStatus.NightCloudy),
            DayModel(14, "5:00", WeatherStatus.NightCloudy),
            DayModel(15, "6:00", WeatherStatus.NightDropRain),
            DayModel(15, "7:00", WeatherStatus.DayRain),
            DayModel(15, "8:00", WeatherStatus.DayRain),
            DayModel(16, "9:00", WeatherStatus.DayRain),
            DayModel(15, "10:00", WeatherStatus.DayPrecipitation),
            DayModel(14, "11:00", WeatherStatus.DayLightning),
            DayModel(14, "12:00", WeatherStatus.DayPrecipitation),
            DayModel(14, "13:00", WeatherStatus.DayPrecipitation),
            DayModel(15, "14:00", WeatherStatus.DayPrecipitation),
            DayModel(15, "15:00", WeatherStatus.DayDropRain),
            DayModel(16, "16:00", WeatherStatus.DayDropRain),
            DayModel(16, "17:00", WeatherStatus.DayRain),
            DayModel(16, "18:00", WeatherStatus.DayDropRain),
        )
        binding.weatherView.setDaysList(listDays)
    }
}