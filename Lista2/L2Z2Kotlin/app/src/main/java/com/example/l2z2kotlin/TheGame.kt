package com.example.l2z2kotlin

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class TheGame : AppCompatActivity() {
    val keyboardTranslate = arrayListOf(arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0));
    val keyboardAccess = arrayListOf(arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0), arrayListOf(0, 0, 0, 0, 0, 0, 0));
    val keyboard = arrayListOf(arrayListOf("a", "b", "c", "d", "e" , "f" , "g" ), arrayListOf("h" , "i" , "j" , "k" , "l" , "m", "n"), arrayListOf("o", "p", "q", "r" , "s" , "t" , "u"), arrayListOf("", "v" , "w" , "x" , "y" , "z", ""));
    var guess = "";
    var world = "";
    var wrong = 0;
    lateinit var dictionary: Array<String>;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_game);

        for(i in 0 .. 3)
        {

            for(j in 0 .. 6)
            {
                if(i != 3 || (j != 0 && j != 6))
                {
                    val buttonId = "button" + i + j;
                    val resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                    keyboardTranslate[i][j] = resId;

                    findViewById<Button>(resId).text = keyboard[i][j].capitalize();

                }
            }
        }
        Init();

    }

    fun Init()
    {
        this.wrong = 1;
        this.dictionary = resources.getStringArray(R.array.slownik);
        val a = 0;
        val b = this.dictionary.size-1;
        val random = roll(a,b);
        this.world = this.dictionary[random];
        this.guess = "";
        for(a in this.world)
        {
            if(a.equals(" "))
            {
                this.guess += a;
            }
            else
            {
                this.guess += "_ ";
            }
        }
        Log.i("theGameInit", this.world);
        val resId = getResources().getIdentifier("Guess", "id", getPackageName());
        findViewById<TextView>(resId).text = this.guess;
        for(i in 0 .. 3)
        {

            for(j in 0 .. 6)
            {
                if(i != 3 || (j != 0 && j != 6))
                {
                    keyboardAccess[i][j] = 0;
                }
            }
        }
    }
    fun roll (a : Int, b : Int): Int
    {
        return (a..b).random();
    }
    fun setupWord()
    {
        var resId = getResources().getIdentifier("Guess", "id", getPackageName());
        this.guess = "";
        for(a in this.world)
        {
            Log.i("setupWord letter", a.toString());
            if(a.equals(" "))
            {
                this.guess += "  ";
            }
            else
            {
                for(i in 0 .. 3)
                {
                    for(j in 0 .. 6)
                    {
                        if(i != 3 || (j != 0 && j != 6))
                        {
                            if(keyboard[i][j].equals(a.toString()))
                            {
                                Log.i("setupWord letter", "i'm in");
                                if(keyboardAccess[i][j] == 1)
                                {
                                    this.guess += a.toString();
                                    Log.i("setupWord letter", "correct");
                                }
                                else
                                {
                                    this.guess += " _ ";
                                }
                            }
                        }
                    }
                }
            }
        }
        Log.i("setupWord", guess);
        findViewById<TextView>(resId).text = this.guess;
        if(this.guess.equals(this.world))
        {
            resId = getResources().getIdentifier("Title", "id", getPackageName());
            findViewById<TextView>(resId).text = "Wygrałeś :)";
        }
    }
    //returns -1 when wrong, 0 if allready guessed and 1 if guessed correctly
    fun Mark (letter : Char): Int
    {
        var ans = -1;
        for(i in 0 .. 3)
        {
            for(j in 0 .. 6)
            {
                if(i != 3 || (j != 0 && j != 6))
                {
                    if(keyboard[i][j].equals(letter.toString()))
                    {
                        if(keyboardAccess[i][j] == 0)
                        {
                            Log.i("Mark", world);
                            Log.i("Mark", letter.toString());
                            if(world.contains(letter.toString()))
                            {
                                keyboardAccess[i][j] = 1;
                                ans = 1;
                            }
                            else
                            {
                                this.wrong += 1;
                                setImg();
                                ans = -1;
                            }
                        }
                        else
                        {
                            ans = 0;
                        }
                    }
                }
            }
        }
        setupWord();
        return ans;
    }

    fun setImg()
    {
        var resId = getResources().getIdentifier("imageView", "id", getPackageName());
        if(this.wrong == 1)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec01_foreground)
        }
        if(this.wrong == 2)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec02_foreground)
        }
        if(this.wrong == 3)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec03_foreground)
        }
        if(this.wrong == 4)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec04_foreground)
        }
        if(this.wrong == 5)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec05_foreground)
        }
        if(this.wrong == 6)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec06_foreground)
        }
        if(this.wrong == 7)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec07_foreground)
        }
        if(this.wrong == 8)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec08_foreground)
        }
        if(this.wrong == 9)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec09_foreground)
        }
        if(this.wrong == 10)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec10_foreground);
        }
        if(this.wrong >= 11)
        {
            findViewById<ImageView>(resId).setImageResource(R.mipmap.wisielec11_foreground)
            resId = getResources().getIdentifier("Title", "id", getPackageName());
            findViewById<TextView>(resId).text = "Przegrałeś :(";
        }
    }

    fun button(view: View)
    {
        val but : Button = view as Button;
        if(wrong >= 11)
        {
            finish();
        }
        if(this.guess.equals(this.world))
        {
            finish();
        }
        val id = view.id;
        for(i in 0 .. 3)
        {
            for(j in 0 .. 6)
            {
                if(i != 3 || (j != 0 && j != 6))
                {
                    if(keyboardTranslate[i][j] == id)
                    {
                        val ret = Mark(keyboard[i][j][0]);
                        if(ret == -1)
                        {
                            Log.i("Button", "incorrect");
                            but.setTextColor(0xFFFFFF);
                        }
                        else if(ret == 1)
                        {
                            Log.i("Button", "correct");
                            but.setTextColor(0xFFFFFF);
                        }
                    }
                }
            }
        }
    }
}