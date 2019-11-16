package com.example.pauloandroidcourse

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    var media: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        media = MediaPlayer.create(applicationContext, R.raw.watch_me)
        /*try {
            media = MediaPlayer()
            media?.setDataSource("http://buildappswithpaulo.com/music/watch_me.mp3")
            media?.prepareAsync()
            media?.setOnPreparedListener {
                it.start()
            }
        }catch (e:Exception){
            Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
        }*/


        media?.setOnCompletionListener {
            Toast.makeText(this@MainActivity, (it.duration / 1000) / 60, Toast.LENGTH_SHORT).show()
        }
        seekBarId.max = media!!.duration

        seekBarId.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                if (p2)
                    media?.seekTo(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })

        var counter = 0
        playButton.setOnClickListener {
            if (counter == 0) {
                media?.start()
                counter = 1
                playButton.text = "Pause"
            } else {
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
