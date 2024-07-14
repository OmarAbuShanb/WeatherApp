package dev.anonymous.weatherview.model

data class WeekModel(
    val minTemperature: Int,
    val maxTemperature: Int,
    val listDays: List<DayModel>
)
