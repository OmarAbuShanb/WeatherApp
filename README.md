[![](https://jitpack.io/v/OmarAbuShanb/WeatherApp.svg)](https://jitpack.io/#OmarAbuShanb/WeatherApp)

---

# WeatherView

A view developed to display weather data with a temperature curve.

## Preview
<p align="center">
<img src="images/preview.gif" width="320">
<p>

## Setup
Add the following dependency in your root settings.gradle.kts at the end of repositories:
```Gradle
dependencyResolutionManagement {
    repositories {
        //...
        maven { url = uri("https://jitpack.io") }
    }
}
```
Add the dependency:
```Gradle
dependencies {
    implementation("com.github.OmarAbuShanb:WeatherApp:1.0-alpha")
}
```

## Simple usage with Kotlin
### Kotlin

```Kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listDays = listOf(
            DayModel(13, "1:00", WeatherStatus.Moon),
            DayModel(12, "2:00", WeatherStatus.NightCloudy),
            DayModel(14, "3:00", WeatherStatus.NightCloudy),
            DayModel(15, "4:00", WeatherStatus.NightDropRain),
            DayModel(15, "5:00", WeatherStatus.DayRain),
            DayModel(15, "6:00", WeatherStatus.DayRain),
            DayModel(15, "7:00", WeatherStatus.DayPrecipitation),
            DayModel(14, "8:00", WeatherStatus.DayLightning),
            DayModel(14, "9:00", WeatherStatus.DayPrecipitation),
            DayModel(16, "10:00", WeatherStatus.DayDropRain),
            DayModel(16, "11:00", WeatherStatus.DayRain),
            DayModel(16, "12:00", WeatherStatus.DayDropRain),
        )
        binding.weatherView.setDaysList(listDays)
    }
}
```

```Xml
    <dev.anonymous.weatherview.ui.view.WeatherView
        android:id="@+id/weatherView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
```

## License details

```
MIT License

Copyright (c) 2024 OmarAbuShanb

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
