package com.example.androidchess

object GameMode{
     enum class Player { pOne, pTwo }
     var player: Player = Player.pOne
     enum class Mode { AI,Local,Online }
     var mode: Mode = Mode.Local
}