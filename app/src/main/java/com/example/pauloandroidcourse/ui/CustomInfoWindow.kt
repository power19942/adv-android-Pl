package com.example.pauloandroidcourse.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.example.pauloandroidcourse.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoWindow(var context: Context) : GoogleMap.InfoWindowAdapter {

    lateinit var view:View
    override fun getInfoWindow(p0: Marker?): View {
        view = LayoutInflater.from(context).inflate(R.layout.custom_info_window, null)
        return view
    }

    override fun getInfoContents(marker: Marker?): View {
        var title = view.findViewById<TextView>(R.id.winTitle)
        title.text = marker?.title
        var mag = view.findViewById<TextView>(R.id.magnitude)
        mag.text = marker?.snippet
        return view
    }


}