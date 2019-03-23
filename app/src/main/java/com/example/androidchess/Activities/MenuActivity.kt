package com.example.androidchess.Activities

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.androidchess.R
import com.example.androidchess.User
import com.example.androidchess.chessboard.GameActivity
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog

class MenuActivity : AppCompatActivity() {
    private lateinit var soundPool: SoundPool
    private var clickSound = 0

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        supportActionBar!!.hide()
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(6)
            .setAudioAttributes(audioAttributes)
            .build()

        clickSound = soundPool.load(this, R.raw.move, 1)

        val userField = findViewById<TextView>(R.id.userView)
        userField.text = "Username: \n" + User.name

        val eloField = findViewById<TextView>(R.id.eloView)
        eloField.text = "Elo: \n" + User.elo

        val multiPlayerButton = findViewById<ImageButton>(R.id.multiplayerbutton)
        multiPlayerButton.setOnClickListener { playMultiplayer() }

        val aiButton = findViewById<ImageButton>(R.id.aibutton)
        aiButton.setOnClickListener { playAI() }

        val optionsButton = findViewById<ImageButton>(R.id.optionbutton)
        optionsButton.setOnClickListener{ options() }

        val logOutButton = findViewById<ImageButton>(R.id.logoutbutton)
        logOutButton.setOnClickListener { logOut() }

        val shareByte = findViewById<ImageButton>(R.id.shareButton)
        shareByte.setOnClickListener{share()}
    }

    //TODO: implement intents for changing activity
    fun playMultiplayer(){
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
    }
        val intent = Intent(this, LobbyActivity::class.java)
        startActivity(intent)

    }

    fun playAI(){
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun options(){
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val intent = Intent(this, OptionActivity::class.java)
        startActivity(intent)
    }

    fun logOut(){
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        finish()
        val toLogIn = Intent(this, LogInActivity::class.java)
        startActivity(toLogIn)
    }

    fun share(){
        val content = ShareLinkContent.Builder().setContentUrl(Uri.parse("https://www.facebook.com/AndroidChess-592467867902828/")).
            setQuote("Join me on AndroidChess").build()
        ShareDialog(this).show(content)
    }
}


