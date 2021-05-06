package com.example.l5z1.main

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.l5z1.R
import com.example.l5z1.components.Game
import com.example.l5z1.components.GameView
import com.example.l5z1.interfaces.GameStateListener

class GameActivity : AppCompatActivity(), View.OnClickListener, GameStateListener {

    lateinit var ping: GameView
    lateinit var menuDialog: AlertDialog
    private var difficulty: Int = 0
    private var saveSlot: Int = 0
    private var gameStarted: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        difficulty = intent.getIntExtra("difficulty", 0)
        saveSlot = intent.getIntExtra("saveSlot", 0)

        Log.i("game parameters", "diff = $difficulty and saveSlot is $saveSlot")
        createDialog()
    }

    private fun createDialog(viewId: Int = R.layout.menu){
        menuDialog = AlertDialog.Builder(this).setView(viewId).setCancelable(false).create()

        menuDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        when(viewId)
        {
            R.layout.pause ->{

            }
        }
    }

    override fun onResume() {
        super.onResume()
        menuDialog.show()
    }

    override fun onPause() {
        super.onPause()
        if(gameStarted)ping.stop()
    }

    override fun onClick(v: View?) {
        if(menuDialog.isShowing){
            menuDialog.dismiss()
        }

        when(v?.id)
        {
            R.id.start -> {
                Log.i("difficulty", "$difficulty")
                gameStarted = true
                ping = GameView(this, difficulty, saveSlot)
                setContentView(ping)
            }
            R.id.quit -> this.finish()
            R.id.continueGame -> {
                ping.start()
            }
        }
    }

    override fun stateChanged(state: Game.STATE) {
        Log.i("state", "changed to $state")
        when(state){
            Game.STATE.END -> runOnUiThread {
                gameStarted = false
                createDialog(R.layout.menu)
                menuDialog.show()
            }
            Game.STATE.PAUSED -> runOnUiThread {
                createDialog(R.layout.pause)
                menuDialog.show()
            }

        }
    }



}