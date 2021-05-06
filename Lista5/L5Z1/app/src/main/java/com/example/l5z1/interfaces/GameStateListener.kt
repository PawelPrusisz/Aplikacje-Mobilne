package com.example.l5z1.interfaces

import com.example.l5z1.components.Game

interface GameStateListener {
    fun stateChanged(state: Game.STATE)
}
