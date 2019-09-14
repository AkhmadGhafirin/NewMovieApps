package com.cascer.madesubmission2.feature.detail

import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.feature.main.MainViewModel
import com.cascer.madesubmission2.utils.GlideApp
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val from = intent.getStringExtra("from")
        supportActionBar?.title = "Detail $from"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (from == "Movie") {
            val data = intent.getParcelableExtra<MoviesItem>("data")
            requestAndSetupViewMovie(data)
        } else {
            val data = intent.getParcelableExtra<TvShowItem>("data")
            requestAndSetupViewTvShow(data)
        }
    }

    private fun requestAndSetupViewMovie(data: MoviesItem?) {
        val id = data?.id ?: 0
        viewModel.getDetailMovie(id, resources.getString(R.string.language))
            .observe(this, Observer {
                it?.let {
                    GlideApp.with(this)
                        .load("https://image.tmdb.org/t/p/w1280/${it.backdropPath}")
                        .into(iv_movie_detail)
                    tv_title.text = it.title
                    tv_release.text = it.releaseDate
                    tv_rating.text = it.voteAverage.toString()
//        tv_duration.text = data?.duration
                    tv_overview.text = it.overview

                    val genres = arrayListOf<String>()
                    for (item in it.genres ?: emptyList()) {
                        genres.add(item.name ?: "")
                    }
                    tv_genres.text = TextUtils.join(", ", genres)
                }
            })
    }

    private fun requestAndSetupViewTvShow(data: TvShowItem?) {
        val id = data?.id ?: 0
        viewModel.getDetailTvShow(id, resources.getString(R.string.language))
            .observe(this, Observer {
                it?.let {
                    GlideApp.with(this)
                        .load("https://image.tmdb.org/t/p/w1280/${it.backdropPath}")
                        .into(iv_movie_detail)
                    tv_title.text = it.name
                    tv_release.text = it.firstAirDate
                    tv_rating.text = it.voteAverage.toString()
//        tv_duration.text = data?.duration
                    tv_overview.text = it.overview

                    val genres = arrayListOf<String>()
                    for (item in it.genres ?: emptyList()) {
                        genres.add(item.name ?: "")
                    }
                    tv_genres.text = TextUtils.join(", ", genres)
                }
            })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}
