package com.example.movie_app.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ItemMovieFunnyBinding
import com.example.movie_app.ui.PlayerActivity
import com.example.movie_app.util.Constant

class MovieAdapter (private val movie: Movie, private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: ItemMovieFunnyBinding =
            ItemMovieFunnyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movie.feed[position].items[0]
        Log.i("a",movie.feed.size.toString())

        holder.binding!!.apply {
            Glide.with(context).load(currentItem.art).into(poster)
            title.text=currentItem.title

        }
        holder.binding.poster.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.apply {

                putExtra(Constant.URL_VIDEO, currentItem.url)
                putExtra(Constant.TITLE_VIDEO, currentItem.title)
                putExtra(Constant.YEAR_VIDEO, currentItem.year)
                putExtra(Constant.DIRECTOR_VIDEO, currentItem.director)
                putExtra(Constant.DESCRIPTION_VIDEO, currentItem.description)
                putExtra(Constant.ART_POSTER, currentItem.art)
                putExtra(Constant.DURATION,currentItem.duration)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount():Int{

        return movie.feed.size
    }


    inner class MovieViewHolder(val binding: ItemMovieFunnyBinding?) :
        RecyclerView.ViewHolder(binding?.root!!)

}