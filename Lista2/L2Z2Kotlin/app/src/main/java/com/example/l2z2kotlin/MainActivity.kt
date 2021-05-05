package com.example.l2z2kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startGame(view: View)
    {
        val myIntent = Intent(this, TheGame::class.java);
        startActivityForResult(myIntent, 1);
        Log.i("Main", "test");
    }
}