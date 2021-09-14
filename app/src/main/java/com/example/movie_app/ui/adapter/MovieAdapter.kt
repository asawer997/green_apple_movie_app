package com.example.movie_app.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_app.data.Feed
import com.example.movie_app.data.Item
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ItemMovieBinding
import com.example.movie_app.ui.PlayerActivity
import com.example.movie_app.util.Constant

class MovieAdapter(val movie: Movie, private val context: Context) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: ItemMovieBinding =
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movie.feed[position].items[0]

        holder.binding!!.apply {
           title.text=currentItem?.title
            imbd.text=currentItem?.duration.toString()
            year.text=currentItem?.year.toString()
            Glide.with(context).load(currentItem.art).into(poster)
            Glide.with(context).load(currentItem.art).into(backPoster)



        }
        holder.binding.backPoster.setOnClickListener {
            val intent = Intent(context,PlayerActivity::class.java)
            intent.apply {

                putExtra(Constant.PLAYER_ACTIVITY,currentItem.url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount():Int{

        return movie.feed.size
    }


    inner class MovieViewHolder(val binding: ItemMovieBinding?) :
        RecyclerView.ViewHolder(binding?.root!!)

}