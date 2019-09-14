package com.cascer.madesubmission2.feature.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.utils.GlideApp
import kotlinx.android.synthetic.main.movie.view.*

class MovieAdapter(private val listener: (data: MoviesItem) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var dataSet = emptyList<MoviesItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }


    override fun getItemCount(): Int = dataSet.size

    fun insertList(dataList: List<MoviesItem>) {
        dataSet = dataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.invoke(dataSet[adapterPosition])
            }
        }

        fun bind(data: MoviesItem) {
            itemView.tv_title.text = data.title
            itemView.tv_rating.text = data.voteAverage.toString()
//            itemView.tv_duration.text = data.duration
            GlideApp.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w1280/${data.posterPath}")
                .centerInside()
                .into(itemView.iv_movie)
        }
    }
}