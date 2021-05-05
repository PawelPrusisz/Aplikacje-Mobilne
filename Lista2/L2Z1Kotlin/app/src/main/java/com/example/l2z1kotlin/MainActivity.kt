package com.example.l2z1kotlin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.l2z1kotlin.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun board3(view: View)
    {
        val myIntent = Intent(this, Board3x3::class.java);
        startActivityForResult(myIntent, 3);
    }

    fun board5(view: View)
    {
        val myIntent = Intent(this, Board5x5::class.java);
        startActivityForResult(myIntent, 5);
    }

    override fun onPause() {
        super.onPause()
        Log.i("main", "onPause");
    }

    override fun onResume() {
        super.onResume()
        Log.i("main", "onResume");
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);
        val resId = getResources().getIdentifier("textView3", "id", getPackageName());
        Log.i("main", resId.toString());
        val back : String? = data?.getStringExtra("Winner");
        val Won = Integer.parseInt(back);
        if(requestCode == 3)
        {
            if(Won == 0)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 3x3\n" +
                        "Wygrał \'X\'";
            }
            else if(Won == 1)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 3x3\n" +
                        "Wygrał \'O\'";
            }
            else if(Won == -1)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 3x3\n" +
                        "REMIS";
            }
        }
        else if(requestCode == 5)
        {
            if(Won == 0)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 5x5\n" +
                        "Wygrał \'X\'";
            }
            else if(Won == 1)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 5x5\n" +
                        "Wygrał \'O\'";
            }
            else if(Won == -1)
            {
                findViewById<TextView>(resId).text = "Ostatnia gra: plansza 5x5\n" +
                        "REMIS";
            }
        }
        else
        {
            findViewById<TextView>(resId).text = "Ostatnia gra: \'tba\'";
        }
    }
    override fun onRestart() {
        super.onRestart();
        super.onResume()
        Log.i("main", "onRestart");
        val extra : String? = intent.getStringExtra("Winner");
        if(extra != null)
        {

            val Won = Integer.parseInt(extra);

        }
    }

}