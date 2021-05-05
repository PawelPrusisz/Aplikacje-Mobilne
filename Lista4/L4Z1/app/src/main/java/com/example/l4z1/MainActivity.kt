package com.example.l4z1

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.media.Image
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.configuration
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    companion object{
        private val PATH_KEY = "PATH_KEY"
        private val NAME_KEY = "NAME_KEY"
        private val DESC_KEY = "DESC_KEY"
        private val RATING_KEY = "RATING_KEY"
    }

    private var imageRecycler: RecyclerView? = null
    private var orientation: Int = 0
    private var images: ArrayList<com.example.l4z1.Image>? = ArrayList<com.example.l4z1.Image>()

    private var imagePaths: ArrayList<String> = ArrayList<String>()
    private var imageNames: ArrayList<String> = ArrayList<String>()
    private var descriptions: ArrayList<String> = ArrayList<String>()
    private var ratings: ArrayList<Int> = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this@MainActivity,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    101
            )
        }

        Log.i("Main: onCreate", "ping")
        Log.i("Orientation", resources.configuration.orientation.toString())
        imageRecycler = findViewById<RecyclerView>(R.id.galeryRecycler)

        orientation = resources.configuration.orientation

        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            Log.i("landscape", "abc")
            imageRecycler?.layoutManager = GridLayoutManager(this, 5)
        }
        else
        {
            Log.i("portrait", "abc")
            imageRecycler?.layoutManager = GridLayoutManager(this, 3)
        }

        Log.i("...", "...")
        imageRecycler?.setHasFixedSize(false)

        images = ArrayList<com.example.l4z1.Image>()
        if(savedInstanceState != null)
        {
            Log.i("Bundle notNull", "abc")
            imagePaths = savedInstanceState.getStringArrayList(PATH_KEY)!!
            imageNames = savedInstanceState.getStringArrayList(NAME_KEY)!!
            descriptions = savedInstanceState.getStringArrayList(DESC_KEY)!!
            ratings = savedInstanceState.getIntegerArrayList(RATING_KEY)!!
            for(i in 0 until imagePaths.size)
            {
                val image = Image()
                image.imagePath = imagePaths[i]
                image.imageName= imageNames[i]
                image.description = descriptions[i]
                image.rating = ratings[i]
                images!!.add(image)
            }
        }
        else
        {
            Log.i("bundle is null", "abc")
            if(images!!.isEmpty())
            {
                images = getImages()
            }
        }

        imageRecycler?.adapter = ImageAdapter(this, images!!)

    }

    private fun getImages(): ArrayList<com.example.l4z1.Image>? {
        val img = ArrayList<com.example.l4z1.Image>()

        val allImgUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val projection = arrayOf(MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.DISPLAY_NAME)

        val cursor = this@MainActivity.contentResolver.query(allImgUri, projection, null, null)

        try {
            cursor!!.moveToFirst()
            do{
                val image = Image()
                image.imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                image.imageName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
                image.description = image.imageName + " placeholder description"
                image.rating = 0
                imagePaths.add(image.imagePath + "")
                imageNames.add(image.imageName + "")
                descriptions.add(image.imageName + " placeholder description")
                ratings.add(0)
                img.add(image)
            }while(cursor.moveToNext())
            cursor.close()
        }catch (e:Exception)
        {
            Log.i("db", "error :(")
        }
        return img
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("result", "incoming")
        if(requestCode == 101)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                val index = data?.getIntExtra("index", -1)
                val rating = data?.getIntExtra("rating", 0)
                Log.i("result", "$index, $rating")
                if(index != -1)
                {
                    val A = listOf(1, 2, 3, 4, 5)
                    images?.get(index!!)?.rating = rating
                    ratings[index!!] = rating!!
                    ratings.sortWith(compareByDescending { it })
                    val sortedList: List<com.example.l4z1.Image>? = images?.sortedByDescending { it.rating }
                    var sortedImages: ArrayList<com.example.l4z1.Image> = ArrayList<com.example.l4z1.Image>()
                    for ( el in sortedList!!)
                    {
                        sortedImages.add(el)
                    }
                    images = sortedImages
                    imageRecycler?.adapter = ImageAdapter(this, images!!)
                }
            }
        }
        else
        {

        }
    }
    override fun onStart() {
        super.onStart()
        Log.i("Main: onStart", "ping")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Main: onResume", "ping")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Main: onPause", "ping")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Main: onStop", "ping")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Main: onRestart", "ping")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        Log.i("Main: onRestoreInstanceState", "ping")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(PATH_KEY, imagePaths)
        outState.putStringArrayList(NAME_KEY, imageNames)
        outState.putStringArrayList(DESC_KEY, descriptions)
        outState.putIntegerArrayList(RATING_KEY, ratings)
        Log.i("Main: onSaveInstanceState", "ping")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Main: onDestroy", "ping")
    }


}