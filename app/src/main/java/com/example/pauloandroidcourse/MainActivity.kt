package com.example.pauloandroidcourse

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.SeekBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(),SurfaceHolder.Callback {

    lateinit var media:MediaPlayer
    lateinit var holder: SurfaceHolder

    override fun surfaceChanged(p0: SurfaceHolder?, p1: Int, p2: Int, p3: Int) {

    }

    override fun surfaceDestroyed(p0: SurfaceHolder?) {
    }

    override fun surfaceCreated(p0: SurfaceHolder?) {
        media.setDisplay(holder)
    }


    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        media = MediaPlayer.create(this@MainActivity,R.raw.outro_music)
        surfaceView.keepScreenOn = true
        holder = surfaceView.holder
        holder.addCallback(this@MainActivity)
        holder.setFixedSize(400,300)

        buttonPlay.setOnClickListener {
            media.start()
        }

        buttonPause.setOnClickListener {
            media.pause()
        }

        buttonSkip.setOnClickListener {
            media.seekTo(media.duration - 100)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        media?.release()
    }

    override fun onPause() {
        super.onPause()
        media?.pause()
        media?.release()
    }
}
