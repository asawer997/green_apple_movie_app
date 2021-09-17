package com.example.movie_app.ui

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.movie_app.databinding.ActivityPlayerBinding
import com.example.movie_app.util.Constant
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class PlayerActivity : BaseActivity<ActivityPlayerBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "VIDEO_PLAYER_ACTIVITY"
    private var player: SimpleExoPlayer? = null

    private lateinit var pictureInPictureParams: PictureInPictureParams.Builder
    override val bindingInflater: (LayoutInflater) -> ActivityPlayerBinding =
        ActivityPlayerBinding::inflate



    @SuppressLint("SetTextI18n")
    override fun setup() {

        getDataOfMovieFromAdapter()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPictureParams = PictureInPictureParams.Builder()
        }

    }
    @SuppressLint("SetTextI18n")
    private fun getDataOfMovieFromAdapter(){
        val mPoster = intent.getStringExtra(Constant.ART_POSTER).toString()
        val mDescription = intent.getStringExtra(Constant.DESCRIPTION_VIDEO).toString()
        val mDirection = intent.getStringExtra(Constant.DIRECTOR_VIDEO).toString()
        val mYear = intent.getIntExtra(Constant.YEAR_VIDEO,0)
        val mTitle = intent.getStringExtra(Constant.TITLE_VIDEO).toString()


        val url = intent.getStringExtra(Constant.URL_VIDEO).toString()
        val intent = Intent(this, VideoActivity::class.java)
        intent.putExtra(Constant.TITLE_VIDEO, url)


        binding?.apply {
            Glide.with(this@PlayerActivity).load(mPoster).into(view)

            textMoviesDesc.text = mDescription
            textMoviesCategory.text = " ${mYear }/${mDirection}"
            textMovieName.text = mTitle

        }

    }

    override fun addCallbacks() {
        binding?.icMoveBack?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        binding?.start?.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)}

    }




}