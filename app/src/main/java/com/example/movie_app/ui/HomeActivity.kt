package com.example.movie_app.ui

import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ActivityMainBinding
import com.example.movie_app.ui.adapter.MovieAdapter
import com.example.movie_app.util.Constant
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import java.util.*

class HomeActivity : BaseActivity<ActivityMainBinding>() {
    //type the content after make override

    override val LOG_TAG: String = "HOME_ACTIVITY"
    private val client= OkHttpClient()
    private val gson = GsonBuilder().create()
    private lateinit var linearLayoutManager: LinearLayoutManager



    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setup() {

        makeRequest()

        linearLayoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding?.funnyRecycler?.layoutManager = linearLayoutManager
    }

    override fun addCallbacks() {

    }




private fun makeRequest() {

    val request= Request.Builder().url(Constant.URL).build()

    client.newCall(request).enqueue(object : Callback {


        override fun onFailure(call: Call, e: IOException) {
            e.message?.let { Log.i(LOG_TAG,it) }
        }

        override fun onResponse(call: Call, response: Response) {
            val body = response.body?.string()
            val result = gson.fromJson(body, Movie::class.java)



                runOnUiThread{
                    binding?.apply {
//                       test.text=result.feed.joinToString { it.description }
                      funnyRecycler.apply {
                            adapter = MovieAdapter(result, this@HomeActivity)
                            setHasFixedSize(true)
                        }



                    }
            }
        }

    })
}}
