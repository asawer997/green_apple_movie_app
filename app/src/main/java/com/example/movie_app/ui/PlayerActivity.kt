package com.example.movie_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.movie_app.databinding.ActivityPlayerBinding

class PlayerActivity : BaseActivity<ActivityPlayerBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "VIDEO_PLAYER_ACTIVITY"

    override val bindingInflater: (LayoutInflater) -> ActivityPlayerBinding =
        ActivityPlayerBinding::inflate

    override fun setup() {

    }

    override fun addCallbacks() {    }

}