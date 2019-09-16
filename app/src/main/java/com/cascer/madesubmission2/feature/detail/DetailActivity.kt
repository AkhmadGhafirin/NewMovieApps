package com.cascer.madesubmission2.feature.detail

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.utils.GlideApp
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.iv_movie_detail
import kotlinx.android.synthetic.main.activity_detail.tv_genres
import kotlinx.android.synthetic.main.activity_detail.tv_overview
import kotlinx.android.synthetic.main.activity_detail.tv_rating
import kotlinx.android.synthetic.main.activity_detail.tv_release
import kotlinx.android.synthetic.main.activity_detail.tv_title
import kotlinx.android.synthetic.main.detail_shimmer_container.*
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val from = intent.getStringExtra("from")
        supportActionBar?.title = "Detail $from"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val id = intent.getIntExtra("id", 0)
        setupViewModel(from)
        requestDetail(from, id)
    }

    private fun requestDetail(from: String?, id: Int) {
        if (from == "Movie") {
            viewModel.requestDetailMovie(id, resources.getString(R.string.language))
        } else {
            viewModel.requestDetailTvShow(id, resources.getString(R.string.language))
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(DetailActivity::class.java.simpleName, count.toString())
        count++
    }

    private fun setupViewModel(from: String?) {
        if (from == "Movie") {
            viewModel.detailMovieLiveData
                .observe(this, Observer {
                    it?.let {
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
                })
        } else {
            viewModel.detailTvShowLiveData
                .observe(this, Observer {
                    it?.let {
                        GlideApp.with(this)
                            .load("https://image.tmdb.org/t/p/w1280/${it.backdropPath}")
                            .into(iv_movie_detail)
                        tv_title.text = it.name
                        tv_release.text = it.firstAirDate
                        tv_rating.text = it.voteAverage.toString()
                        tv_overview.text = it.overview

                        val genres = arrayListOf<String>()
                        for (item in it.genres ?: emptyList()) {
                            genres.add(item.name ?: "")
                        }
                        tv_genres.text = TextUtils.join(", ", genres)
                        stopShimmer()
                    }
                })
        }

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
