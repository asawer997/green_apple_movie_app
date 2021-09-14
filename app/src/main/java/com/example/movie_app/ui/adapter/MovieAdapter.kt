package com.example.movie_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ItemMovieBinding

class MovieAdapter (val movie: Movie, private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movie.feed[position].items[0]

        holder.binding!!.apply {
           testr.text=currentItem.id

        }
    }

    override fun getItemCount():Int{

        return movie.feed.count()
    }


    inner class MovieViewHolder(val binding: ItemMovieBinding?) :
        RecyclerView.ViewHolder(binding?.root!!)

}