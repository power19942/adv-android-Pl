package com.example.pauloandroidcourse.util

import java.util.*

object Constants {

    val URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson"
    val LIMIT = 30


    fun randomInt(max: Int, min: Int): Int {
        return Random().nextInt(max - min) + min

    }
}
