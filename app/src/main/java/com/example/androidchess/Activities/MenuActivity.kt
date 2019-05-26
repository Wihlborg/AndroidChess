package com.example.androidchess.Activities

import android.content.Intent
import android.media.AudioAttributes
import android.media.SoundPool
import android.net.Uri
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.example.androidchess.GameMode
import com.example.androidchess.R
import com.example.androidchess.User
import com.example.androidchess.chessboard.GameActivity
import com.example.androidchess.chessboard.TimerInfo
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
        multiPlayerButton.setOnClickListener { multiplayerChoice() }

        val localGameButton = findViewById<ImageButton>(R.id.localgame)
        localGameButton.setOnClickListener { timerChoice() }

        val wifiGameButton = findViewById<ImageButton>(R.id.onlinegame)
        wifiGameButton.setOnClickListener { playMultiplayer() }

        val timerButton = findViewById<ImageButton>(R.id.timerbutton)
        timerButton.setOnClickListener {
            TimerInfo.enable = true
            playLocal()
        }

        val classicButton = findViewById<ImageButton>(R.id.classicbutton)
        classicButton.setOnClickListener {
            TimerInfo.enable = false
            playLocal()
        }

        val aiButton = findViewById<ImageButton>(R.id.aibutton)
        aiButton.setOnClickListener { playAI() }

        val optionsButton = findViewById<ImageButton>(R.id.optionbutton)
        optionsButton.setOnClickListener { options() }

        val logOutButton = findViewById<ImageButton>(R.id.logoutbutton)
        logOutButton.setOnClickListener { logOut() }

        val shareByte = findViewById<ImageButton>(R.id.shareButton)
        shareByte.setOnClickListener { share() }

        TimerInfo.enable = false
    }

    override fun onBackPressed() {
        val layout = findViewById<ConstraintLayout>(R.id.multiplayerlayout)
        if (layout.visibility == View.VISIBLE) {
            layout.visibility = View.GONE
            val localLayout = findViewById<ConstraintLayout>(R.id.localwifi)
            localLayout.visibility = View.VISIBLE
            val timerLayout = findViewById<ConstraintLayout>(R.id.timeroption)
            timerLayout.visibility = View.GONE
        } else
            super.onBackPressed()
    }

    override fun onResume() {
        TimerInfo.enable = false
        val layout = findViewById<ConstraintLayout>(R.id.multiplayerlayout)
        layout.visibility = View.GONE
        val localLayout = findViewById<ConstraintLayout>(R.id.localwifi)
        localLayout.visibility = View.VISIBLE
        val timerLayout = findViewById<ConstraintLayout>(R.id.timeroption)
        timerLayout.visibility = View.GONE
        super.onResume()
    }

    fun timerChoice() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val layout = findViewById<ConstraintLayout>(R.id.localwifi)
        layout.visibility = View.GONE
        val timerLayout = findViewById<ConstraintLayout>(R.id.timeroption)
        timerLayout.visibility = View.VISIBLE
    }

    fun multiplayerChoice() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val layout = findViewById<ConstraintLayout>(R.id.multiplayerlayout)
        layout.visibility = View.VISIBLE
    }

    fun playLocal() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        GameMode.mode = GameMode.Mode.Local
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    //TODO: implement intents for changing activity
    fun playMultiplayer() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val intent = Intent(this, LobbyActivity::class.java)
        startActivity(intent)

    }

    fun playAI() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        GameMode.mode = GameMode.Mode.AI
        val intent = Intent(this, GameActivity::class.java)
        startActivity(intent)
    }

    fun options() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        val intent = Intent(this, OptionActivity::class.java)
        startActivity(intent)
    }

    fun logOut() {
        if (User.sounds) {
            soundPool.play(clickSound, 1.0F, 1.0F, 0, 0, 1.0F)
        }
        this.finish()
        val toLogIn = Intent(this, LogInActivity::class.java)
        startActivity(toLogIn)
    }

    fun share() {
        val content = ShareLinkContent.Builder()
            .setContentUrl(Uri.parse("https://www.facebook.com/AndroidChess-592467867902828/"))
            .setQuote("Join me on AndroidChess").build()
        ShareDialog(this).show(content)
    }
}


