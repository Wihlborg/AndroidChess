package com.example.androidchess

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog

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

//Hur man sharear länk på FB: Vi kanske ska sharea bild istället?
/*val content = ShareLinkContent.Builder().setContentUrl(Uri.parse("www.google.com")).build()
ShareDialog(this).show(content)*/

