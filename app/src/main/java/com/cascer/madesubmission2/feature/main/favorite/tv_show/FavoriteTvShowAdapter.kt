package com.cascer.madesubmission2.feature.main.favorite.tv_show

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow
import com.cascer.madesubmission2.utils.GlideApp
import kotlinx.android.synthetic.main.movie.view.*

class FavoriteTvShowAdapter(private val listener: (data: FavoriteTvShow) -> Unit) :
    RecyclerView.Adapter<FavoriteTvShowAdapter.ViewHolder>() {

    private var dataSet = emptyList<FavoriteTvShow>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }


    override fun getItemCount(): Int = dataSet.size

    fun insertList(dataList: List<FavoriteTvShow>) {
        dataSet = dataList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.invoke(dataSet[adapterPosition])
            }
        }

        fun bind(data: FavoriteTvShow) {
            itemView.tv_title.text = data.name
            itemView.tv_rating.text = data.voteAverage.toString()
            GlideApp.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w1280/${data.posterPath}")
                .centerInside()
                .into(itemView.iv_movie)
        }
    }
}