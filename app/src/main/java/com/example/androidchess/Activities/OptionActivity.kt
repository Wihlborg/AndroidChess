package com.example.androidchess.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Switch
import com.example.androidchess.*

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        supportActionBar!!.hide()

        val applyButton = findViewById<Button>(R.id.applyButton)
        val updateButton = findViewById<Button>(R.id.options_password_button)
        applyButton.setOnClickListener { saveOptions() }
        updateButton.setOnClickListener { val goTo = Intent(this,NewPasswordActivity::class.java)
         startActivity(goTo)}

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
        User.sounds = findViewById<Switch>(R.id.soundSwitch).isChecked
        /*
        Put future options here
         */
        editor.apply()

        super.onBackPressed()
    }
}
