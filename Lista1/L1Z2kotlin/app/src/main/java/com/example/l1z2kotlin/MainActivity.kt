package com.example.l1z2kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.l1z2kotlin.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var wygrane = 0;
    private var przegrane = 0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
    }
    fun Roll(choice: Int) {
        var i =  Random.nextInt(3);
        if(i == 0)
        {
            binding.Odpowiedz.text= "Kamień!";
        }
        else if (i == 1)
        {
            binding.Odpowiedz.text = "Papier!";
        }
        else if(i == 2)
        {
            binding.Odpowiedz.text = "Nożyce!";
        }

        if(i == choice)
        {
            Toast.makeText(this, "Remis", Toast.LENGTH_SHORT).show();
        }
        else if(i == 0 && choice == 1)
        {
            wygrane++;
            binding.Wygrane.text = "Wygrane\n" + wygrane;
            Toast.makeText(this, "Wygrałeś!", Toast.LENGTH_SHORT).show();
        }
        else if(i == 0 && choice == 2)
        {
            przegrane++
            binding.Przegrane.text = "Przegrane\n" + przegrane;
            Toast.makeText(this, "Przegrałeś  :(", Toast.LENGTH_SHORT).show();
        }
        else if(i == 1 && choice == 0)
        {
            przegrane++
            binding.Przegrane.text = "Przegrane\n" + przegrane;
            Toast.makeText(this, "Przegrałeś  :(", Toast.LENGTH_SHORT).show();
        }
        else if(i == 1 && choice == 2)
        {
            wygrane++;
            binding.Wygrane.text = "Wygrane\n" + wygrane;
            Toast.makeText(this, "Wygrałeś!", Toast.LENGTH_SHORT).show();
        }
        else if(i== 2 && choice == 0)
        {
            wygrane++;
            binding.Wygrane.text = "Wygrane\n" + wygrane;
            Toast.makeText(this, "Wygrałeś!", Toast.LENGTH_SHORT).show();
        }
        else if( i== 2 && choice == 1)
        {
            przegrane++
            binding.Przegrane.text = "Przegrane\n" + przegrane;
            Toast.makeText(this, "Przegrałeś  :(", Toast.LENGTH_SHORT).show();
        }
   }
    fun Kamien(view: View) {
        Roll(0);
    }

    fun Papier(view: View) {
        Roll(1)
    }

    fun Nozyce(view: View) {
        Roll(2);
    }

}