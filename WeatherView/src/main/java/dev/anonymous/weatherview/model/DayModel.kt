package dev.anonymous.weatherview.model

enum class WeatherStatus {
    Sun,
    Moon,
    NightPrecipitationSnow,
    DayPrecipitationSnow,
    NightPrecipitation,
    DayPrecipitation,
    NightLightning,
    DayLightning,
    NightRain,
    DayRain,
    NightDropRain,
    DayDropRain,
    NightCloudy,
    DayCloudy,
    NightWindy,
    DayWindy
}

data class DayModel(
    val temperature: Int,
    val time: String,
    val weatherStatus: WeatherStatus
)
