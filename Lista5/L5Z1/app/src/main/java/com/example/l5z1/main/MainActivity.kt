package com.example.l5z1.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.l5z1.DBHandler
import com.example.l5z1.R
import com.example.l5z1.components.BotPlayer
import com.example.l5z1.components.GameView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var difficulty = 0
    var saveSlot: Int = 1
    lateinit var menuDialog: AlertDialog

    fun buttonPress(view: View)
    {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("difficulty", difficulty)
            .putExtra("saveSlot", saveSlot)
        startActivity(intent)
    }

    fun saveSlotPicker(view: View)
    {
        val viewId = R.layout.save_slot_menu
        menuDialog = AlertDialog.Builder(this).setView(viewId).setCancelable(false).create()
        menuDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        menuDialog.show()
    }

    fun difficultyPicker(view: View)
    {
        val viewId = R.layout.difficulty_menu
        menuDialog = AlertDialog.Builder(this).setView(viewId).setCancelable(false).create()
        menuDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        menuDialog.show()
    }



    fun onClick(v: View?) {
        if(menuDialog.isShowing){
            menuDialog.dismiss()
        }

        when(v?.id)
        {
            R.id.diff1 ->
            {
                difficulty = 0
            }
            R.id.diff2 ->
            {
                difficulty = 1
            }
            R.id.diff3 ->
            {
                difficulty = 2
            }

            R.id.slot1 ->
            {
                saveSlot = 1
            }
            R.id.slot2 ->
            {
                saveSlot = 2
            }
            R.id.slot3 ->
            {
                saveSlot = 3
            }
        }
    }


}