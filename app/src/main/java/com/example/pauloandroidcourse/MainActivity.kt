package com.example.pauloandroidcourse

import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var batAnim : AnimationDrawable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bat.setBackgroundResource(R.drawable.bat_anim)
        batAnim = bat.background as AnimationDrawable

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Toast.makeText(this@MainActivity,"touchec",Toast.LENGTH_SHORT).show()
        batAnim.start()
        return super.onTouchEvent(event)
    }
}
