package com.example.movie_app.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movie_app.data.MovieResponse
import com.example.movie_app.databinding.ItemMovieFunnyBinding
import com.example.movie_app.ui.PlayerActivity
import com.example.movie_app.util.Constant.ART_POSTER
import com.example.movie_app.util.Constant.DESCRIPTION_VIDEO
import com.example.movie_app.util.Constant.DIRECTOR_VIDEO
import com.example.movie_app.util.Constant.DURATION
import com.example.movie_app.util.Constant.TITLE_VIDEO
import com.example.movie_app.util.Constant.URL_VIDEO
import com.example.movie_app.util.Constant.YEAR_VIDEO

class FunnyAdapter(private val movie: MovieResponse, private val context: Context) :
    RecyclerView.Adapter<FunnyAdapter.MovieViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: ItemMovieFunnyBinding =
            ItemMovieFunnyBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MovieViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = movie.feed[position].items[0]

        holder.binding!!.apply {
            Glide.with(context).load(currentItem.art).into(poster)
            title.text = currentItem.title

        }
        holder.binding.poster.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.apply {

                putExtra(URL_VIDEO, currentItem.url)
                putExtra(TITLE_VIDEO, currentItem.title)
                putExtra(YEAR_VIDEO, currentItem.year)
                putExtra(DIRECTOR_VIDEO, currentItem.director)
                putExtra(DESCRIPTION_VIDEO, currentItem.description)
                putExtra(ART_POSTER, currentItem.art)
                putExtra(DURATION,currentItem.duration)
            }
            context.startActivity(intent)
        }
        holder.binding.writerName.text = "Directed by: ${currentItem.director}"
        holder.binding.typeMovie.text = movie.feed[position].id
    }




    override fun getItemCount() = movie.feed.size

    inner class MovieViewHolder(val binding: ItemMovieFunnyBinding?) :
        RecyclerView.ViewHolder(binding?.root!!)

}