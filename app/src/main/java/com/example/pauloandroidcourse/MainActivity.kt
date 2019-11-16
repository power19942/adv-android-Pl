package com.example.pauloandroidcourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var fmg = supportFragmentManager
       // var fragment = fmg.findFragmentById(R.id.frame)
        var fragment = MainFragment()
        fmg.beginTransaction()
            .add(R.id.frame,fragment)
            .commit()

    }
}
