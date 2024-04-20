package com.example.soundsphere

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView

class PlayActivity : AppCompatActivity() {

    lateinit var fabPlay: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabPause: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabStop: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var seekBar: SeekBar
    lateinit var back: Button
    lateinit var musicname: TextView

    private var mp: MediaPlayer? = null
    private var currentSongId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        fabPlay = findViewById(R.id.fab_play)
        fabPause = findViewById(R.id.fab_pause)
        fabStop = findViewById(R.id.fab_stop)
        seekBar = findViewById(R.id.seekbar)
        back = findViewById(R.id.back)
        musicname = findViewById(R.id.musicname)

        val music = intent.getStringExtra("music")
        musicname.text = music
        currentSongId = getSongId(music)

        controlSound(currentSongId)

        back.setOnClickListener {
            finish()
        }
    }

    private fun getSongId(music: String?): Int {
        return when (music) {
            "Sunflower" -> R.raw.sunflower
            "Happy" -> R.raw.happy
            "Rock" -> R.raw.rock
            "Pop" -> R.raw.pop
            else -> R.raw.sunflower // Default to Sunflower if music not recognized
        }
    }

    private fun controlSound(songId: Int) {
        fabPlay.setOnClickListener {
            if (mp == null) {
                mp = MediaPlayer.create(this, songId)
                initialiseSeekBar()
            }
            mp?.start()
        }

        fabPause.setOnClickListener {
            mp?.pause()
        }

        fabStop.setOnClickListener {
            mp?.stop()
            mp?.release()
            mp = null
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mp?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initialiseSeekBar() {
        mp?.let {
            seekBar.max = it.duration

            val handler = Handler()
            handler.postDelayed(object : Runnable {
                override fun run() {
                    try {
                        seekBar.progress = mp?.currentPosition ?: 0
                        handler.postDelayed(this, 1000)
                    } catch (e: Exception) {
                        seekBar.progress = 0
                    }
                }
            }, 0)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mp?.release()
        mp = null
    }
}
