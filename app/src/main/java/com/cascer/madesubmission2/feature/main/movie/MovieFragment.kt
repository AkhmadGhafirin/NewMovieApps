package com.cascer.madesubmission2.feature.main.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.feature.detail.DetailActivity
import com.cascer.madesubmission2.feature.main.MainActivity
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.movie_shimmer_container.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val adapter by lazy { MovieAdapter { toDetail(it) } }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        requestAndInsert()
    }

    private fun setupRV() {
        rv_movie.layoutManager =
            GridLayoutManager(
                context, 2,
                GridLayoutManager.VERTICAL, false
            )
        rv_movie.adapter = adapter
    }

    private fun toDetail(data: MoviesItem) {
        val intent = Intent((context as MainActivity), DetailActivity::class.java)
        intent.putExtra("from", resources.getString(R.string.movie_label))
        intent.putExtra("id", data.id ?: 0)
        startActivity(intent)
    }

    private fun requestAndInsert() {
        viewModel.movieListLiveData
            .observe(this, Observer {
                it?.let {
                    adapter.insertList(it)
                    if (shimmer_container != null) {
                        shimmer_container.stopShimmer()
                        shimmer_container.visibility = View.GONE
                    }
                }
            })

        viewModel.requestNowPlayingMovie(resources.getString(R.string.language))
    }
}