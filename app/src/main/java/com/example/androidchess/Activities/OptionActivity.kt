package com.example.androidchess.Activities

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import com.example.androidchess.*

class OptionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_option)

        supportActionBar!!.hide()

        val updateButton = findViewById<ImageButton>(R.id.options_password_button)
        updateButton.setOnClickListener { val goTo = Intent(this,NewPasswordActivity::class.java)
         startActivity(goTo)}

        val soundSwitch = findViewById<Switch>(R.id.soundSwitch)
        val sharedPrefs = getSharedPreferences("chesspref", Context.MODE_PRIVATE)
        soundSwitch.isChecked = sharedPrefs.getBoolean("sounds", false)
        /*
        Put future switches here
         */
    }

    override fun onBackPressed() {
        saveOptions()
        super.onBackPressed()
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
