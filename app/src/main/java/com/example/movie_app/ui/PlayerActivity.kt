package com.example.movie_app.ui

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Rational
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.example.movie_app.databinding.ActivityPlayerBinding
import com.example.movie_app.util.Constant.ART_POSTER
import com.example.movie_app.util.Constant.DESCRIPTION_VIDEO
import com.example.movie_app.util.Constant.DIRECTOR_VIDEO
import com.example.movie_app.util.Constant.DURATION
import com.example.movie_app.util.Constant.TAG
import com.example.movie_app.util.Constant.TITLE_VIDEO
import com.example.movie_app.util.Constant.URL_VIDEO
import com.example.movie_app.util.Constant.YEAR_VIDEO
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

    override fun setup() {

        initializeExoPlayer()

        getDataOfMovieFromAdapter()

        backToMainActivity()

        goToGitHubToSeeSourceCode()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPictureParams = PictureInPictureParams.Builder()
        }
    }

    override fun addCallbacks() {}

    @SuppressLint("SetTextI18n")
    private fun getDataOfMovieFromAdapter(){
        val mPoster = intent.getStringExtra(ART_POSTER).toString()
        val mDescription = intent.getStringExtra(DESCRIPTION_VIDEO).toString()
        val mDirection = intent.getStringExtra(DIRECTOR_VIDEO).toString()
        val mYear = intent.getIntExtra(YEAR_VIDEO,0)
        val mTitle = intent.getStringExtra(TITLE_VIDEO).toString()
        val mDuration = intent.getIntExtra(DURATION,0)


        binding?.apply {
            Glide.with(this@PlayerActivity).load(mPoster).into(posterImage)

            about.text = mDescription
            director.text = mDirection
            year.text = "At Year-$mYear"
            title.text = mTitle
            duration.text = "${mDuration.toString().subSequence(0,2)}K"

        }

    }

    private fun backToMainActivity(){
        binding?.back?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun goToGitHubToSeeSourceCode(){
        val repoUrl = "https://github.com/asawer997/green_apple_movie_app"
        binding?.sourceCodeLabel?.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repoUrl)
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
        val videoUrl = intent.getStringExtra(URL_VIDEO).toString()
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

    @SuppressLint("ObsoleteSdkInt")
    private fun getPictureInPictureMode() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            val videoDimension = binding?.exoPlayer
//            val aspectRation = videoDimension?.let { Rational(it.width, videoDimension.height) }
//            pictureInPictureParams.setAspectRatio(aspectRation).build()

            enterPictureInPictureMode(pictureInPictureParams.build())

        } else {
            Log.i(TAG, "Not available for this version")
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            if (!isInPictureInPictureMode) {
                getPictureInPictureMode()
            } else {
                Log.i(TAG, "Not available for this version")
            }
        }
    }

}