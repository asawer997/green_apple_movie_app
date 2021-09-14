package com.example.movie_app.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_app.data.Movie
import com.example.movie_app.databinding.ItemMovieFunnyBinding
import com.example.movie_app.ui.PlayerActivity
import com.example.movie_app.util.Constant

class FunnyAdapter (val movie: Movie, private val context: Context) :
    RecyclerView.Adapter<FunnyAdapter.MovieViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: ItemMovieFunnyBinding =
            ItemMovieFunnyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movie.feed[position].items[0]

        holder.binding!!.apply {
            Glide.with(context).load(currentItem.art).into(poster)
            title.text=currentItem.title

        }
        holder.binding.poster.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.apply {

                putExtra(Constant.PLAYER_ACTIVITY,currentItem.url)
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