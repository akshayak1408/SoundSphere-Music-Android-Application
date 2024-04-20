package com.example.soundsphere

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView

    private val musicOptions = arrayOf(
        "Happy",
        "Pop",
        "Rock",
        "Sunflower"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_view_music_options)

        val adapter = ArrayAdapter(
            this,
            R.layout.list_item_custom,
            R.id.text_music_option,
            musicOptions
        )

        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedOption = musicOptions[position]
            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("music", selectedOption)
            startActivity(intent)

        }
    }
}
