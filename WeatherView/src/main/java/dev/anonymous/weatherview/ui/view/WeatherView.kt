package dev.anonymous.weatherview.ui.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewTreeObserver
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import androidx.core.view.updateLayoutParams
import dev.anonymous.weatherview.R
import dev.anonymous.weatherview.databinding.WeatherItemBinding
import dev.anonymous.weatherview.model.DayModel
import dev.anonymous.weatherview.model.WeatherStatus
import dev.anonymous.weatherview.ui.utils.PositionX
import dev.anonymous.weatherview.ui.utils.PositionY
import dev.anonymous.weatherview.ui.utils.ScreenUtils

class WeatherView(context: Context, attrs: AttributeSet) : HorizontalScrollView(context, attrs) {
    private lateinit var linearLayout: LinearLayout

    private var weatherViewHeight: Int = 0

    private var itemWidth: Int = 0
    private var dayWeatherViewAvailableDrawingAreaHeight: Int = 0
    private var dayWeatherViewPaddingTop: Int = 0
    private var dayWeatherViewCenterX: Int = 0

    private var weatherImageSize: Float? = null

    private var timeTextSize: Float? = null
    private var timeTextColor: Int? = null

    private var temperatureTextSize: Float? = null
    private var temperatureTextColor: Int? = null

    private var linesColor: Int? = null
    private var linesWidth: Float? = null

    init {
        initDimensions()
        initWeatherViewHeight()
        initLinerLayout()
        getAttrs(attrs)
    }

    private fun initDimensions() {
        weatherViewHeight = ScreenUtils.dpToPixel(230f, context)
        itemWidth = ScreenUtils.dpToPixel(70f, context)
        val dayWeatherViewHeight = ScreenUtils.dpToPixel(150f, context)

        dayWeatherViewPaddingTop = ScreenUtils.dpToPixel(50f, context)
        val dayWeatherViewPaddingBottom = ScreenUtils.dpToPixel(20f, context)

        dayWeatherViewAvailableDrawingAreaHeight =
            dayWeatherViewHeight - dayWeatherViewPaddingTop - dayWeatherViewPaddingBottom

        // centerX = half item width
        dayWeatherViewCenterX = itemWidth / 2
    }

    private fun initWeatherViewHeight() {
        // set WeatherView height
        viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                updateLayoutParams {
                    height = weatherViewHeight
                }
                println("removeOnGlobalLayoutListener")
                viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

    private fun initLinerLayout() {
        linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        // add horizontal linear layout to horizontal scroll view as a child
        addView(linearLayout)
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.WeatherView)
        setTypedArray(typedArray)
    }

    private fun setTypedArray(typedArray: TypedArray) {
        for (i in 0..typedArray.indexCount) {
            when (val attr = typedArray.getIndex(i)) {
                R.styleable.WeatherView_timeTextSize -> {
                    timeTextSize = typedArray.getDimension(attr, -1f).takeIf { it != -1f }
                }

                R.styleable.WeatherView_timeTextColor -> {
                    timeTextColor = typedArray.getColor(attr, -1).takeIf { it != -1 }
                }

                R.styleable.WeatherView_weatherImageSize -> {
                    weatherImageSize = typedArray.getDimension(attr, -1f).takeIf { it != -1f }
                }

                R.styleable.WeatherView_temperatureTextSize -> {
                    temperatureTextSize = typedArray.getDimension(attr, -1f).takeIf { it != -1f }
                }

                R.styleable.WeatherView_temperatureTextColor -> {
                    temperatureTextColor = typedArray.getColor(attr, -1).takeIf { it != -1 }
                }

                R.styleable.WeatherView_linesColor -> {
                    linesColor = typedArray.getColor(attr, -1).takeIf { it != -1 }
                }

                R.styleable.WeatherView_linesWidth -> {
                    linesWidth = typedArray.getDimension(attr, -1f).takeIf { it != -1f }
                }
            }
        }
        typedArray.recycle()
    }

    fun setDaysList(daysList: List<DayModel>) {
        validationDaysListArg(daysList)
        val allCenterPointsY = getAllCenterPointsY(daysList)

        daysList.forEachIndexed { index, dayModel ->
            var firstPoint: Pair<PositionX, PositionY>? = null
            var lastPoint: Pair<PositionX, PositionY>? = null

            // center point X is the same for all items
            val centerPoint = Pair(
                dayWeatherViewCenterX,
                allCenterPointsY[index]
            )

            // in the first item we don't draw the first line
            if (index != 0) {
                firstPoint = getFirstPoint(
                    allCenterPointsY[index - 1],
                )
            }

            // in the last item we don't draw the last line
            if (index != daysList.size - 1) {
                lastPoint = getLastPoint(
                    allCenterPointsY[index + 1]
                )
            }
            addWeather(
                dayModel.temperature,
                dayModel.time,
                dayModel.weatherStatus,
                firstPoint,
                centerPoint,
                lastPoint
            )
        }
    }

    private fun validationDaysListArg(daysList: List<DayModel>) {
        if (daysList.isEmpty()) {
            throw IllegalArgumentException("daysList is empty")
        }
        daysList.forEach {
            if (it.temperature > 60 || it.temperature < -60) {
                throw IllegalArgumentException("${it.temperature} temperature value not valid")
            }
        }
    }

    private fun getFirstPoint(previousCenterY: Int): Pair<PositionX, PositionY> {
        // the first point x start at the center of the previous item
        val x = 0 - (itemWidth * 0.5).toInt()
        return Pair(x, previousCenterY)
    }

    private fun getAllCenterPointsY(daysList: List<DayModel>): IntArray {
        // make array size equal to listDays size
        val allCenterPointsY = IntArray(daysList.size)

        val minTemperature = daysList.minByOrNull { it.temperature }!!.temperature
        val maxTemperature = daysList.maxByOrNull { it.temperature }!!.temperature

        // 20 = 50 - 30
        val value = maxTemperature - minTemperature
        // 5 = 100px / 20
        val fraction = dayWeatherViewAvailableDrawingAreaHeight.toFloat() / value

        daysList.forEachIndexed { index, dayModel ->
            // 50Y = (50 - 40) * 5
            var y = (maxTemperature - dayModel.temperature) * fraction
            // 70Y = 50 + 20
            y += dayWeatherViewPaddingTop
            allCenterPointsY[index] = y.toInt()
        }

        return allCenterPointsY
    }

    private fun getLastPoint(nextCenterY: Int): Pair<PositionX, PositionY> {
        // the last point x end at the center of the next item
        val x = (itemWidth * 1.5).toInt()
        return Pair(x, nextCenterY)
    }

    private fun addWeather(
        temperature: Int,
        time: String,
        weatherStatus: WeatherStatus,
        firstPoint: Pair<PositionX, PositionY>?,
        centerPoint: Pair<PositionX, PositionY>,
        lastPoint: Pair<PositionX, PositionY>?
    ) {
        WeatherItemBinding.inflate(
            LayoutInflater.from(context),
            linearLayout,
            false
        ).apply {
            timeText.text = time
            timeTextSize?.let { timeText.setTextSize(TypedValue.COMPLEX_UNIT_PX, it) }
            timeTextColor?.let { timeText.setTextColor(it) }
            weatherImage.setImageResource(getWeatherIconRecourse(weatherStatus))
            weatherImageSize?.let {
                weatherImage.updateLayoutParams {
                    width = it.toInt()
                    height = it.toInt()
                }
            }
            dayWeather.setAttrs(temperatureTextSize, temperatureTextColor, linesColor, linesWidth)
            dayWeather.setPointsPosition(temperature.toString(), firstPoint, centerPoint, lastPoint)
            Log.d("TAG_SIZE", "WeatherView: addWeather: setPointsPosition")

            linearLayout.addView(root)
            Log.d("TAG_SIZE", "WeatherView: addWeather: addView")
        }
    }

    /**
     * Return IntRes weather icon based on whether status
     */
    private fun getWeatherIconRecourse(weatherStatus: WeatherStatus): Int {
        return when (weatherStatus) {
            WeatherStatus.Moon -> R.drawable.moon
            WeatherStatus.Sun -> R.drawable.sun
            WeatherStatus.NightPrecipitationSnow -> R.drawable.night_precipitation_snow
            WeatherStatus.DayPrecipitationSnow -> R.drawable.day_precipitation_snow
            WeatherStatus.NightPrecipitation -> R.drawable.night_precipitation
            WeatherStatus.DayPrecipitation -> R.drawable.day_precipitation
            WeatherStatus.NightLightning -> R.drawable.night_lightning
            WeatherStatus.DayLightning -> R.drawable.day_lightning
            WeatherStatus.NightRain -> R.drawable.night_rain
            WeatherStatus.DayRain -> R.drawable.day_rain
            WeatherStatus.NightDropRain -> R.drawable.night_drop_rain
            WeatherStatus.DayDropRain -> R.drawable.day_drop_rain
            WeatherStatus.NightCloudy -> R.drawable.night_cloudy
            WeatherStatus.DayCloudy -> R.drawable.day_cloudy
            WeatherStatus.NightWindy -> R.drawable.night_windy
            WeatherStatus.DayWindy -> R.drawable.day_windy
        }
    }

    companion object {
        private const val TAG = "WeatherView"
    }
}