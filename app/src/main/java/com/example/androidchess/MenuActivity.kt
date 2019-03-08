package com.example.androidchess

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar!!.hide()

        val multiPlayerButton = findViewById<Button>(R.id.multiplayerbutton)
        multiPlayerButton.setOnClickListener { playMultiplayer() }

        val aiButton = findViewById<Button>(R.id.aibutton)
        aiButton.setOnClickListener { playAI() }

        val optionsButton = findViewById<Button>(R.id.optionbutton)
        optionsButton.setOnClickListener{ options() }

        val logOutButton = findViewById<Button>(R.id.logoutbutton)
        logOutButton.setOnClickListener { logOut() }
    }

    //TODO: implement intents for changing activity
    fun playMultiplayer(){

    }

    fun playAI(){

    }

    fun options(){

    }

    fun logOut(){

    }
}

