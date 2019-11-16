package com.example.pauloandroidcourse

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var media: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        media = MediaPlayer.create(applicationContext,R.raw.watch_me)
        var counter = 0
        playButton.setOnClickListener {
            if(counter == 0)
            {
                media?.start()
                counter = 1
                playButton.text = "Pause"
            }
            else
            {
                media?.pause()
                counter = 0
                playButton.text = "play"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        media?.pause()
        media?.release()
    }
}
