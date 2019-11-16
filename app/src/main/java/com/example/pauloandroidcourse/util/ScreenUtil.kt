package com.example.pauloandroidcourse.util

import android.app.Activity
import android.util.DisplayMetrics

class ScreenUtil(activity: Activity) {

    var dpHeight:Float
    var dpWidth:Float

    init {
        var display = activity.windowManager.defaultDisplay
        var outMetrics = DisplayMetrics()

        display.getMetrics(outMetrics)
        var density = activity.resources.displayMetrics.density
        dpHeight = outMetrics.heightPixels / density
        dpWidth = outMetrics.widthPixels / density
    }
}
