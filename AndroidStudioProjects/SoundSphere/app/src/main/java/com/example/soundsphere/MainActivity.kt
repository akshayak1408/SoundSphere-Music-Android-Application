package com.example.soundsphere

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var searchEditText: EditText
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var create_playlist: Button
    private lateinit var spinnerChoosePlaylist: Spinner
    private val playlists = arrayOf("My Playlist 1", "My Playlist 2", "My Playlist 3")

    private val allMusicOptions = arrayOf(
        "Awaken",
        "Happy",
        "Movement",
        "Order",
        "Pop",
        "Rock",
        "Separation",
        "Smoke",
        "Sunflower",
        "Titanium",
        "Waterfall"
    )

    private var filteredMusicOptions = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.list_view_music_options)
        searchEditText = findViewById(R.id.edit_text_search)
        create_playlist = findViewById(R.id.btn_create_playlist)

        filteredMusicOptions.addAll(allMusicOptions)

        adapter = ArrayAdapter(
            this,
            R.layout.list_item_custom,
            R.id.text_music_option,
            filteredMusicOptions
        )
        listView.adapter = adapter


        listView.setOnItemClickListener { parent, view, position, id ->
            val selectedOption = filteredMusicOptions[position]

            val intent = Intent(this@MainActivity, PlayActivity::class.java)
            intent.putExtra("music", selectedOption)
            startActivity(intent)
        }
        spinnerChoosePlaylist = findViewById(R.id.spinner_choose_playlist)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, playlists)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerChoosePlaylist.adapter = adapter


        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterMusicOptions(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        create_playlist.setOnClickListener {
            val intent = Intent(this, Playlist::class.java)
            startActivity(intent)
        }
    }

    private fun filterMusicOptions(query: String) {
        filteredMusicOptions.clear()
        if (query.isEmpty()) {
            filteredMusicOptions.addAll(allMusicOptions)
        } else {
            for (option in allMusicOptions) {
                if (option.contains(query, true)) {
                    filteredMusicOptions.add(option)
                }
            }
        }
        adapter.notifyDataSetChanged()
    }
}
