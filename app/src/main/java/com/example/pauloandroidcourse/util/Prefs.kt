package com.example.pauloandroidcourse.util

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {


    var prefrences: SharedPreferences = context.getSharedPreferences("SCORE", Context.MODE_PRIVATE)

    fun saveHigestScore(score: Int) {
        var currentScore = score
        var lastScore = prefrences.getInt("high_score", 0)
        if (currentScore > lastScore) {
            prefrences.edit().putInt("high_score", currentScore).apply()
        }
    }

    fun getHigestScore(): Int {

        return prefrences.getInt("high_score", 0)

    }

}


