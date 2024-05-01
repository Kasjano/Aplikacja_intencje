package com.example.lab3

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var szerokosc: EditText
    lateinit var dlugosc: EditText
    var photo: Bitmap? = null
    private var ZDJECIE_KEY = "ZDJECIE_KEY"

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Zapisz tutaj potrzebne dane do obiektu Bundle
        outState.putParcelable(ZDJECIE_KEY, photo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        if(savedInstanceState != null) {
            photo = savedInstanceState.getParcelable(ZDJECIE_KEY)
            findViewById<ImageView>(R.id.imageView).setImageBitmap(photo)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun OpenSettings(view: View){
        val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
        try{
            startActivity(intent)
        }
        catch(e: ActivityNotFoundException){}
    }

    fun OpenMap(view: View) {
        dlugosc = findViewById(R.id.editNumber2)
        szerokosc = findViewById(R.id.editNumber)
        val szerokosc2 = szerokosc.text.toString()
        val dlugosc2 = dlugosc.text.toString()
        val geolocation = "geo:$szerokosc2,$dlugosc2"
        val intent = Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(geolocation) }
        try {
            startActivity(intent)
        }
        catch(e: ActivityNotFoundException){}
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val image: ImageView = findViewById(R.id.imageView)
        if (requestCode == 111 && resultCode == RESULT_OK) {
            // BitMap is data structure of image file which store the image in memory
            photo = data?.extras?.get("data") as Bitmap
            // Set the image in imageview for display
            image.setImageBitmap(photo)
        }
    }

    fun OpenCamera(view: View){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try{
            startActivityForResult(intent,111)
        }
        catch(e: ActivityNotFoundException){}
    }
}