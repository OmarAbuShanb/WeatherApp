package dev.anonymous.weatherview.ui.utils

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics


class ScreenUtils {
    companion object {
        fun spToPixel(spValue: Float, context: Context): Float {
            return spValue * context.resources.displayMetrics.density
        }

        fun dpToPixel(dp: Float, context: Context): Int {
            return Math.round(
                dp * (context.resources.displayMetrics.densityDpi.toFloat() /
                        DisplayMetrics.DENSITY_DEFAULT)
            )
        }

        fun isNightModeEnabled(context: Context): Boolean {
            return context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK ==
                    Configuration.UI_MODE_NIGHT_YES
        }
    }
}