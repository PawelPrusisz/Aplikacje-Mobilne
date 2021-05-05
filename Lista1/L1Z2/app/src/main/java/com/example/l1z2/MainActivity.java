package com.example.l1z2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private boolean bold = false;
    private int wygrane = 0;
    private int przegrane = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    private void roll(int choice)
    {
        Random rand = new Random();
        int i = rand.nextInt(3);
        if(i == 0)
        {
            TextView odp = (TextView)findViewById(R.id.odpowiedz);
            odp.setText("Kamień!");

        }
        else if(i == 1)
        {
            TextView odp = (TextView)findViewById(R.id.odpowiedz);
            odp.setText("Papier!");
        }
        else if(i == 2)
        {
            TextView odp = (TextView)findViewById(R.id.odpowiedz);
            odp.setText("Nożyce!");
        }

        if(i == choice)
        {
            Toast.makeText(this,"Remis", Toast.LENGTH_SHORT).show();
        }
        else if(i == 0 && choice == 1)
        {
            Toast.makeText(this,"Wygrałeś!", Toast.LENGTH_SHORT).show();
            wygrane += 1;
            TextView w = (TextView)findViewById(R.id.wygrane);
            w.setText("Wygrane\n" + Integer.toString(wygrane));
        }
        else if(i == 0 && choice == 2)
        {
            Toast.makeText(this,"Przegrałeś :(", Toast.LENGTH_SHORT).show();
            przegrane += 1;
            TextView w = (TextView)findViewById(R.id.przegrane);
            w.setText("Przegrane\n" + Integer.toString(przegrane));
        }
        else if(i == 1 && choice == 0)
        {
            Toast.makeText(this,"Przegrałeś :(", Toast.LENGTH_SHORT).show();
            przegrane += 1;
            TextView w = (TextView)findViewById(R.id.przegrane);
            w.setText("Przegrane\n" + Integer.toString(przegrane));
        }
        else if(i == 1 && choice == 2)
        {
            Toast.makeText(this,"Wygrałeś!", Toast.LENGTH_SHORT).show();
            wygrane += 1;
            TextView w = (TextView)findViewById(R.id.wygrane);
            w.setText("Wygrane\n" + Integer.toString(wygrane));
        }
        else if(i == 2 && choice == 0)
        {
            Toast.makeText(this,"Wygrałeś!", Toast.LENGTH_SHORT).show();
            wygrane += 1;
            TextView w = (TextView)findViewById(R.id.wygrane);
            w.setText("Wygrane\n" + Integer.toString(wygrane));
        }
        else if(i == 2 && choice == 1)
        {
            Toast.makeText(this,"Przegrałeś :(", Toast.LENGTH_SHORT).show();
            przegrane += 1;
            TextView w = (TextView)findViewById(R.id.przegrane);
            w.setText("Przegrane\n" + Integer.toString(przegrane));
        }
    }
    public void Kamien(View view) {
        roll(0);
    }

    public void Papier(View view) {
        roll(1);
    }

    public void Nozyce(View view) {
        roll(2);
    }

}