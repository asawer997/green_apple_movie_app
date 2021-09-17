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
        initializeExoPlayer()

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
        val mDuration = intent.getIntExtra(Constant.DURATION,0)


        binding?.apply {
            Glide.with(this@PlayerActivity).load(mPoster).into(view)

            textMoviesDesc.text = mDescription
            textMoviesCategory.text = " ${mYear }/${mDirection}"
            textMovieName.text = mTitle
            duration.text = "${mDuration.toString().subSequence(0,2)}K"

        }

    }

    override fun addCallbacks() {
        binding?.icMoveBack?.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }
    private fun initializeExoPlayer() {
        player = SimpleExoPlayer.Builder(this).build()
        binding?.exoPlayer?.player = player
        buildMediaSource().let {
            player?.setMediaSource(it)
            player?.prepare()
        }
    }

    private fun buildMediaSource(): MediaSource {
        val videoUrl = intent.getStringExtra(Constant.URL_VIDEO).toString()
        val dataSourceFactory = DefaultDataSourceFactory(this, "sample")
        return ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)))
    }

    override fun onResume() {
        super.onResume()
        player?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        player?.playWhenReady = false
        if (isFinishing) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        player?.release()
    }

    private fun getPictureInPictureMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            val videoDimension = binding?.exoPlayer
//            val aspectRation = videoDimension?.let { Rational(it.width, videoDimension.height) }
//            pictureInPictureParams.setAspectRatio(aspectRation).build()

            enterPictureInPictureMode(pictureInPictureParams.build())

        } else {
            Log.i(LOG_TAG, "Not available for this version")
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (!isInPictureInPictureMode) {
                getPictureInPictureMode()
            } else {
                Log.i(LOG_TAG, "Not available for this version")
            }
        }
    }


}