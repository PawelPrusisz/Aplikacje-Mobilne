package com.example.l2z1kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.l2z1kotlin.databinding.ActivityMainBinding

class Board5x5 : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding;
    var gameboard = arrayListOf(intArrayOf(-1, -1, -1, -1, -1), intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1));
    var gameboardTranslate = arrayListOf(intArrayOf(-1, -1, -1, -1, -1), intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1));
    var player = -1;

    fun hasWon(player : Int): Boolean
    {
        //columns
        if(gameboard[0][0] == player && gameboard[0][1] == player && gameboard[0][2] == player && gameboard[0][3] == player && gameboard[0][4] == player) return true;
        if(gameboard[1][0] == player && gameboard[1][1] == player && gameboard[1][2] == player && gameboard[1][3] == player && gameboard[1][4] == player) return true;
        if(gameboard[2][0] == player && gameboard[2][1] == player && gameboard[2][2] == player && gameboard[2][3] == player && gameboard[2][4] == player) return true;
        if(gameboard[3][0] == player && gameboard[3][1] == player && gameboard[3][2] == player && gameboard[3][3] == player && gameboard[3][4] == player) return true;
        if(gameboard[4][0] == player && gameboard[4][1] == player && gameboard[4][2] == player && gameboard[4][3] == player && gameboard[4][4] == player) return true;
        //rows
        if(gameboard[0][0] == player && gameboard[1][0] == player && gameboard[2][0] == player && gameboard[3][0] == player && gameboard[4][0] == player) return true;
        if(gameboard[0][1] == player && gameboard[1][1] == player && gameboard[2][1] == player && gameboard[3][1] == player && gameboard[4][1] == player) return true;
        if(gameboard[0][2] == player && gameboard[1][2] == player && gameboard[2][2] == player && gameboard[3][2] == player && gameboard[4][2] == player) return true;
        if(gameboard[0][3] == player && gameboard[1][3] == player && gameboard[2][3] == player && gameboard[3][3] == player && gameboard[4][3] == player) return true;
        if(gameboard[0][4] == player && gameboard[1][4] == player && gameboard[2][4] == player && gameboard[3][4] == player && gameboard[4][4] == player) return true;
        //diagonals
        if(gameboard[0][0] == player && gameboard[1][1] == player && gameboard[2][2] == player && gameboard[3][3] == player && gameboard[4][4] == player) return true;
        if(gameboard[4][0] == player && gameboard[3][1] == player && gameboard[2][2] == player && gameboard[1][3] == player && gameboard[0][4] == player) return true;
        return false;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board5x5);
        binding = ActivityMainBinding.inflate(layoutInflater);
        val view = binding.root;
        this.gameboard = arrayListOf(intArrayOf(-1, -1, -1, -1, -1), intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1),intArrayOf(-1, -1, -1, -1, -1));
        this.player = 0;
        for( i in 0 .. 4)
        {
            for(j in 0 .. 4)
            {
                val buttonId = "button" + i + j;
                val resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                gameboardTranslate[i][j] = resId;
            }
        }
    }

    fun button(view: View)
    {
        val id = view.id
        //Log.i("5x5", id.toString());
        for(i in 0 .. 4)
        {
            for(j in 0 .. 4)
            {
                if(id == gameboardTranslate[i][j])
                {
                    if(gameboard[i][j] == -1)
                    {
                        gameboard[i][j] = this.player;
                        if(player == 0)
                        {
                            findViewById<Button>(id).setText("X");
                            //Log.i("5x5", findViewById<Button>(id).text.toString());
                            if(hasWon(player))
                            {
                                val myIntent = Intent();
                                myIntent.putExtra("Winner", player.toString());
                                setResult(RESULT_OK, myIntent);
                                finish();
                            }
                            player = 1;
                            val id = getResources().getIdentifier("turn", "id", getPackageName());
                            findViewById<TextView>(id).text = "Ruch gracza \'O\'";
                        }
                        else if(player == 1)
                        {
                            findViewById<Button>(id).setText("O");
                            if(hasWon(player))
                            {
                                val myIntent = Intent();
                                myIntent.putExtra("Winner", player.toString());
                                setResult(RESULT_OK, myIntent);
                                finish();
                            }
                            player = 0;
                            val id = getResources().getIdentifier("turn", "id", getPackageName());
                            findViewById<TextView>(id).text = "Ruch gracza \'X\'";
                        }

                    }
                }
            }
        }
        var draw = true;
        for(i in 0..4)
        {
            for(j in 0..4)
            {
                if(gameboard[i][j] == -1)
                {
                    draw = false;
                }
            }
        }
        if(draw)
        {
            val myIntent = Intent();
            myIntent.putExtra("Winner", "-1");
            setResult(RESULT_OK, myIntent);
            finish();
        }
    }
}