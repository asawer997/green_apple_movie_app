package com.example.movie_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.movie_app.R
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ActivityMainBinding
import com.example.movie_app.util.Constant
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class HomeActivity : BaseActivity<ActivityMainBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "HOME_ACTIVITY"
    private val client= OkHttpClient()


    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setup() {

        makeRequest()


    }

    override fun addCallbacks() {    }




private fun makeRequest() {

    val request= Request.Builder().url(Constant.URL).build()

    client.newCall(request).enqueue(object : Callback {


        override fun onFailure(call: Call, e: IOException) {
            e.message?.let { Log.i(LOG_TAG,it) }
        }

        override fun onResponse(call: Call, response: Response) {
            response.body?.string()?.let { jsonString->

                val result= Gson().fromJson(jsonString,Movie::class.java)


                runOnUiThread{
                    binding?.apply {
                       test.text=result.feed.joinToString { it.description }
                    }}
            }
        }

    })
}}
