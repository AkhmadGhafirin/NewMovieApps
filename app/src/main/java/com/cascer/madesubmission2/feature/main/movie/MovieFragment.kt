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
import com.cascer.madesubmission2.feature.main.MovieAdapter
import com.cascer.madesubmission2.utils.KEY_MOVIE_LIST_VALUE
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.movie_shimmer_container.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MovieFragment : Fragment() {

    private val adapter by lazy { MovieAdapter { toDetail(it) } }
    private var movieList: ArrayList<MoviesItem> = arrayListOf()

    private val viewModel: MainViewModel by inject { parametersOf(resources.getString(R.string.language)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        if (savedInstanceState == null) {
            requestAndInsert()
        } else {
            movieList =
                savedInstanceState.getParcelableArrayList<MoviesItem>(KEY_MOVIE_LIST_VALUE)
                    ?: arrayListOf()
            adapter.insertList(movieList)
            if (shimmer_container != null) {
                shimmer_container.stopShimmer()
                shimmer_container.visibility = View.GONE
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_MOVIE_LIST_VALUE, movieList)
        super.onSaveInstanceState(outState)
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
                    movieList.addAll(it)
                    adapter.insertList(it)
                    if (shimmer_container != null) {
                        shimmer_container.stopShimmer()
                        shimmer_container.visibility = View.GONE
                    }
                }
            })
    }
}