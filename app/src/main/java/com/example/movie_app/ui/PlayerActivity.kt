package com.example.movie_app.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.movie_app.databinding.ActivityPlayerBinding
import com.example.movie_app.util.Constant

class PlayerActivity : BaseActivity<ActivityPlayerBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "VIDEO_PLAYER_ACTIVITY"

    override val bindingInflater: (LayoutInflater) -> ActivityPlayerBinding =
        ActivityPlayerBinding::inflate



    @SuppressLint("SetTextI18n")
    override fun setup() {

        val title = intent.getStringExtra(Constant.TITLE)
        val year = intent.getStringExtra(Constant.YEAR)
        val description = intent.getStringExtra(Constant.DESCRIPTION)
        val art = intent.getStringExtra(Constant.ART)
        val dirc = intent.getStringExtra(Constant.DIRC)

        binding?.apply {
            textMovieName.text=title
            textMoviesCategory.text=" ${year }/${dirc}"
            textMoviesDesc.text=description
            Glide.with(this@PlayerActivity).load(art).into(view)


        }


    }

    override fun addCallbacks() {

    }

}