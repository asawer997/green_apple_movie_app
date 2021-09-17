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
import com.example.movie_app.databinding.ActivityVideoBinding
import com.example.movie_app.util.Constant
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory

class VideoActivity : BaseActivity<ActivityVideoBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "VIDEO_PLAYER_ACTIVITY"
    private var player: SimpleExoPlayer? = null

    private lateinit var pictureInPictureParams: PictureInPictureParams.Builder
    override val bindingInflater: (LayoutInflater) -> ActivityVideoBinding =
        ActivityVideoBinding::inflate



    override fun setup() {
        initializeExoPlayer()


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPictureParams = PictureInPictureParams.Builder()
        }

    }


    override fun addCallbacks() {


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
        Log.i("m","${videoUrl}vv")
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