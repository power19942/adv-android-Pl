package com.example.pauloandroidcourse.util

import android.app.Application

class JournalApi : Application{
    lateinit var username:String
    lateinit var userId:String

    companion object{
        var instance:JournalApi? = null

        fun getJournalInstance(): JournalApi? {
            if (instance == null)
                instance = JournalApi()
            return instance
        }
    }

    constructor(){

    }
}