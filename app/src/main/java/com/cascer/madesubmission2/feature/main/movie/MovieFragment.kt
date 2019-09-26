package com.cascer.madesubmission2.feature.main.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.feature.detail.DetailActivity
import com.cascer.madesubmission2.feature.main.MainActivity
import com.cascer.madesubmission2.utils.KEY_MOVIE_LIST_VALUE
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie.*
import kotlinx.android.synthetic.main.movie_shimmer_container.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private val adapter by lazy {
        MovieAdapter {
            toDetail(
                it
            )
        }
    }

    private var movieList: ArrayList<MoviesItem> = arrayListOf()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        setupRV()

        setupSearch()

        if (savedInstanceState == null) {
            setupViewModel()
        } else {
            movieList =
                savedInstanceState.getParcelableArrayList<MoviesItem>(KEY_MOVIE_LIST_VALUE)
                    ?: arrayListOf()
            adapter.insertList(movieList)
            startShimmer(false)
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
        intent.putExtra("type", "basic")
        intent.putExtra("data", data)
        startActivity(intent)
    }

    private fun setupSearch() {
        sv_movie.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) search(newText)
                else adapter.insertList(emptyList())
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) search(query)
                else adapter.insertList(emptyList())
                return true
            }
        })
    }

    private fun search(query: String) {
        startShimmer(true)
        viewModel.searchMovie(getString(R.string.language), query)
    }

    private fun startShimmer(start: Boolean) {
        if (shimmer_container != null) {
            if (start) {
                shimmer_container.visibility = View.VISIBLE
                shimmer_container.startShimmer()
            } else {
                shimmer_container.stopShimmer()
                shimmer_container.visibility = View.GONE
            }
        }
    }


    private fun setupViewModel() {
        adapter.insertList(emptyList())
        Log.d("TESTING_CUY", "DATA_NYA_ADA_GA")
        viewModel.movieListLiveData
            .observe(this, Observer {
                it?.let {
                    movieList.addAll(it)
                    adapter.insertList(it)
                    startShimmer(false)
                }
            })
    }
}