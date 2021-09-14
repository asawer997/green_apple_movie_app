package com.example.movie_app.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.movie_app.R
import com.example.movie_app.databinding.ActivityMainBinding

class HomeActivity : BaseActivity<ActivityMainBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "HOME_ACTIVITY"

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding =
        ActivityMainBinding::inflate

    override fun setup() {

    }

    override fun addCallbacks() {    }

}