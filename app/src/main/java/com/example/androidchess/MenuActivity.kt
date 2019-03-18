package com.example.androidchess

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar!!.hide()

        val textField = findViewById<TextView>(R.id.textView)
        textField.text = "Username: \n" + User.name

        val multiPlayerButton = findViewById<Button>(R.id.multiplayerbutton)
        multiPlayerButton.setOnClickListener { playMultiplayer() }

        val aiButton = findViewById<Button>(R.id.aibutton)
        aiButton.setOnClickListener { playAI() }

        val optionsButton = findViewById<Button>(R.id.optionbutton)
        optionsButton.setOnClickListener{ options() }

        val logOutButton = findViewById<Button>(R.id.logoutbutton)
        logOutButton.setOnClickListener { logOut() }

        val shareByte = findViewById<ImageButton>(R.id.shareButton)
        shareByte.setOnClickListener{share()}
    }

    //TODO: implement intents for changing activity
    fun playMultiplayer(){

    }

    fun playAI(){

    }

    fun options(){

    }

    fun logOut(){
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    fun share(){
        val content = ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.facebook.com/AndroidChess-592467867902828/")).
            setQuote("Join me on AndroidChess").build()
        ShareDialog(this).show(content)
    }
}


