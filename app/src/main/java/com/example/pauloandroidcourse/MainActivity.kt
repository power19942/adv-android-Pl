package com.example.pauloandroidcourse

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    lateinit var media: SoundPool

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var sount1: Int
        var sount2: Int
        var sount3: Int
        var sount4: Int

        var attr = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .build()
        media = SoundPool.Builder()
            .setMaxStreams(4)
            .setAudioAttributes(attr)
            .build()


        sount1 = media.load(this@MainActivity, R.raw.complete, 1)
        sount2 = media.load(this@MainActivity, R.raw.correct, 1)
        sount3 = media.load(this@MainActivity, R.raw.defeat_one, 1)
        sount4 = media.load(this@MainActivity, R.raw.defeat_two, 1)

        buttonOne.setOnClickListener {

            media.play(sount1, 1f, 1f, 0, 0, 1f)
        }

        buttonTwo.setOnClickListener {

            media.play(sount2, 1f, 1f, 0, 0, 1f)
        }

        buttonThree.setOnClickListener {

            media.play(sount3, 1f, 1f, 0, 0, 1f)
        }

        buttonFour.setOnClickListener {

            media.play(sount4, 1f, 1f, 0, 0, 1f)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        media.release()
    }
}
