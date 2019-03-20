package com.example.androidchess.Activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import com.example.androidchess.*

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        supportActionBar!!.hide()

        val applyButton = findViewById<Button>(R.id.applyButton)
        applyButton.setOnClickListener { saveOptions() }

        val soundSwitch = findViewById<Switch>(R.id.soundSwitch)
        val sharedPrefs = getSharedPreferences("chesspref", Context.MODE_PRIVATE)
        soundSwitch.isChecked = sharedPrefs.getBoolean("sounds", false)
        /*
        Put future switches here
         */
    }

    fun saveOptions(){
        val editor = getSharedPreferences("chesspref", Context.MODE_PRIVATE).edit()
        editor.putBoolean("sounds", findViewById<Switch>(R.id.soundSwitch).isChecked)
        /*
        Put future options here
         */
        editor.apply()

        super.onBackPressed()
    }
}
