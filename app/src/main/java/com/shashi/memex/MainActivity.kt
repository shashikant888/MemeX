package com.shashi.memex

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
//import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Request.Method
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.squareup.picasso.Picasso
import javax.xml.transform.ErrorListener

class MainActivity : AppCompatActivity() {
    lateinit var imageView:ImageView
    lateinit var btnShare:Button
    lateinit var btnNext:Button
//    lateinit var progressBar: ProgressBar

    private val apiUrl = "https://meme-api.com/gimme"

//    var currentImageUrl:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnShare = findViewById(R.id.btnShare)
        btnNext = findViewById(R.id.btnNext)
        imageView = findViewById(R.id.imageView)
//        progressBar=findViewById(R.id.progressBar)

        loading()

        btnNext.setOnClickListener{
            Toast.makeText(this@MainActivity,"nextClicked",Toast.LENGTH_SHORT).show()
            this.loading()

        }

        btnShare.setOnClickListener{
            Toast.makeText(this@MainActivity,"shareClicked",Toast.LENGTH_SHORT).show()
            shareMeme()
//            shareMemeX()
        }

    }

    private fun shareMeme() {
        val bitmapDrawable = imageView.drawable as BitmapDrawable
        val bitmap = bitmapDrawable.bitmap
        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver,bitmap,"Title",null)

        val bitmapUri = Uri.parse(bitmapPath)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM,bitmapUri)
        startActivity(Intent.createChooser(intent,"share To : "))
    }

    private fun loading() {
         val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET,this.apiUrl,null,
        { response ->
            Log.d("result",response.toString())
            Picasso.get()
                .load(response.get("url").toString())
                .placeholder(R.drawable.loding)
                .into(imageView)

        },
        {
            Log.d("error", it.toString())
            Toast.makeText(applicationContext, "loading error", Toast.LENGTH_LONG).show()
        }
        )
        queue.add(request)
    }


//    private fun loadMeme(){
//        progressBar.visibility=View.VISIBLE
//
//        val url = "https://meme-api.com/gimme"
//
//        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
//            { response ->
//                currentImageUrl= response.getString(url)
//
//                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
//
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        progressBar.visibility=View.GONE
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        progressBar.visibility=View.GONE
//                        return false
//                    }
//                }).into(imageView)
//
//            },
//            { error ->
//                Toast.makeText(this,"some error occurred bro...",Toast.LENGTH_SHORT).show()
//            }
//
//        )
////        Add the request to the RequestQueue.
////        queue.add(jsonObjectRequest)
//        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
//
//    }
//
//    private fun shareMemeX() {
//        val intent = Intent (Intent.ACTION_SEND)
//        intent.type = "text/plane"
//        intent.putExtra(Intent.EXTRA_TEXT,"Bro...ye meme dekh, Reddit se mila $currentImageUrl")
//        val chooser = Intent.createChooser(intent,"Share this meme using...")
//        startActivity(chooser)
//    }

}