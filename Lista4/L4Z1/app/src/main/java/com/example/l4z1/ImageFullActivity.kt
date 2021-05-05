package com.example.l4z1

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

class ImageFullActivity : AppCompatActivity() {


    companion object{
        private var INDEX_KEY = "INDEX_KEY"
        private var RATING_KEY = "RATING_KEY"
    }
    var index: Int = 0
    var rating: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_full)

        Log.i("onCreate", "ping")

        val imagePath = intent.getStringExtra("path")
        val imageName = intent.getStringExtra("name")
        val description = intent.getStringExtra("description")

        if(savedInstanceState != null)
        {
            index = savedInstanceState.getInt(INDEX_KEY)
            rating = savedInstanceState.getInt(RATING_KEY)
        }
        else
        {
            rating = intent.getIntExtra("rating", 0)
            index = intent.getIntExtra("index", -1)
        }


        Glide.with(this)
            .load(imagePath)
            .into(findViewById(R.id.imageView))
        findViewById<TextView>(R.id.name).text = imageName
        findViewById<TextView>(R.id.description).text = description
        val ratingBar = findViewById<RatingBar>(R.id.ratingBar)
        ratingBar.numStars = 5
        val curRating: Float = (rating*1.0/2).toFloat()
        ratingBar.rating = curRating
        ratingBar.setOnRatingBarChangeListener{ ratingBar: RatingBar, fl: Float, b: Boolean ->
            onRatingChanged(ratingBar, fl)
        }



    }

    private fun onRatingChanged(ratingBar: RatingBar, f1: Float)
    {
        this.rating = (f1*2).roundToInt()
        Log.i("new Rating", "$rating")
        Log.i("Setting result", "rating: $rating, index: $index")
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("index", index)
        intent.putExtra("rating", rating)
        setResult(Activity.RESULT_OK, intent)
    }
    override fun onStart() {
        super.onStart()
        Log.i("onStart", "ping")

    }

    override fun onResume() {
        super.onResume()
        Log.i("onResume", "ping")
    }

    override fun onPause() {
        super.onPause()


        Log.i("onPause", "ping")
    }

    override fun onStop() {
        super.onStop()
        Log.i("onStop", "ping")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("onRestart", "ping")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(INDEX_KEY, index)
        outState.putInt(RATING_KEY, rating)
        Log.i("Main: onSaveInstanceState", "ping")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("onDestroy", "ping")
    }
}