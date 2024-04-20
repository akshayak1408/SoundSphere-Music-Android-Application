package com.example.soundsphere

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView

class PlayActivity : AppCompatActivity() {

    lateinit var fabPlay: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabPause: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var fabStop: com.google.android.material.floatingactionbutton.FloatingActionButton
    lateinit var seekBar: SeekBar
    lateinit var back: Button
    lateinit var musicname: TextView
    lateinit var imageView: ImageView
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
        imageView = findViewById(R.id.imageView)
        val music = intent.getStringExtra("music")
        musicname.text = music
        currentSongId = getSongId(music)
        imageView.setImageResource(getImageResource(music))

        controlSound(currentSongId)

        back.setOnClickListener {
            finish()
        }
    }

    private fun getSongId(music: String?): Int {
        return when (music) {
            "Awaken" -> R.raw.awaken
            "Happy" -> R.raw.happy
            "Movement" -> R.raw.movement
            "Order" -> R.raw.order
            "Pop" -> R.raw.pop
            "Rock" -> R.raw.rock
            "Separation" -> R.raw.separation
            "Smoke" -> R.raw.smoke
            "Sunflower" -> R.raw.sunflower
            "Titanium" -> R.raw.titanium
            "Waterfall" -> R.raw.waterfall


            else -> R.raw.sunflower // Default to Sunflower if music not recognized
        }
    }

    private fun getImageResource(music: String?): Int {
        return when (music) {
            "Awaken" -> R.drawable.awaken
            "Happy" -> R.drawable.happy
            "Movement" -> R.drawable.movement
            "Order" -> R.drawable.order
            "Pop" -> R.drawable.pop
            "Rock" -> R.drawable.rock
            "Separation" -> R.drawable.separation
            "Smoke" -> R.drawable.smoke
            "Sunflower" -> R.drawable.sunflower
            "Titanium" -> R.drawable.titanium
            "Waterfall" -> R.drawable.waterfall
            else -> R.drawable.img // Default image
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
