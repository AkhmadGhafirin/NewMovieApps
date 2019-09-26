package com.cascer.favoritemovieapp.feature

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.cascer.favoritemovieapp.R
import com.cascer.favoritemovieapp.data.response.detail.MovieDetailResponse
import com.cascer.favoritemovieapp.utils.GlideApp
import com.cascer.favoritemovieapp.utils.KEY_DETAIL_VALUE
import com.cascer.favoritemovieapp.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.iv_movie_detail
import kotlinx.android.synthetic.main.activity_detail.tv_genres
import kotlinx.android.synthetic.main.activity_detail.tv_overview
import kotlinx.android.synthetic.main.activity_detail.tv_rating
import kotlinx.android.synthetic.main.activity_detail.tv_release
import kotlinx.android.synthetic.main.activity_detail.tv_title
import kotlinx.android.synthetic.main.detail_shimmer_container.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    var id: Int = 0
    lateinit var movieDetailResponse: MovieDetailResponse

    private val viewModel: MainViewModel by inject { parametersOf(id) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupCollapsingToolbar()

        id = intent.getIntExtra("id", 0)

        if (savedInstanceState == null) {
            setupViewModel()
        } else {
            movieDetailResponse =
                savedInstanceState.getParcelable(KEY_DETAIL_VALUE) ?: MovieDetailResponse()
            setupView(movieDetailResponse)
        }
    }

    private fun setupCollapsingToolbar() {
        collapsing_toolbar.title = "Detail Movie"
        collapsing_toolbar.setCollapsedTitleTextColor(
            ContextCompat.getColor(this, R.color.white)
        )
        collapsing_toolbar.setExpandedTitleColor(
            ContextCompat.getColor(this, R.color.white)
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(KEY_DETAIL_VALUE, movieDetailResponse)
        super.onSaveInstanceState(outState)
    }

    private fun setupViewModel() {
        viewModel.detailMovieLiveData
            .observe(this, Observer { it?.let { setupView(it) } })
    }

    private fun setupView(it: MovieDetailResponse) {
        movieDetailResponse = it
        GlideApp.with(this)
            .load("https://image.tmdb.org/t/p/w1280/${it.backdropPath}")
            .into(iv_movie_detail)
        tv_title.text = it.title
        tv_release.text = it.releaseDate
        tv_rating.text = it.voteAverage.toString()
        tv_overview.text = it.overview

        val genres = arrayListOf<String>()
        for (item in it.genres ?: emptyList()) {
            genres.add(item.name ?: "")
        }
        tv_genres.text = TextUtils.join(", ", genres)
        stopShimmer()
    }

    private fun stopShimmer() {
        if (shimmer_container != null) {
            shimmer_container.stopShimmer()
            shimmer_container.visibility = View.GONE
            row_detail.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
