package com.cascer.madesubmission2.feature.detail

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse
import com.cascer.madesubmission2.utils.GlideApp
import com.cascer.madesubmission2.utils.KEY_DETAIL_VALUE
import com.cascer.madesubmission2.utils.KEY_TITLE
import com.cascer.madesubmission2.utils.PopUpMessage
import com.cascer.madesubmission2.viewmodel.DetailViewModel
import com.valdesekamdem.library.mdtoast.MDToast
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
    lateinit var from: String
    lateinit var tvShowDetailResponse: TvShowDetailResponse
    lateinit var movieDetailResponse: MovieDetailResponse
    private var mMenu: Menu? = null
    private var isFavorite: Boolean = false

    private val viewModel: DetailViewModel by inject {
        parametersOf(
            from, id, resources.getString(R.string.language)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        from = intent.getStringExtra("from") ?: ""
        id = intent.getIntExtra("id", 0)
        supportActionBar?.title = "Detail $from"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (savedInstanceState == null) {
            setupViewModel(from)
        } else {
            if (from == "Movie") {
                movieDetailResponse =
                    savedInstanceState.getParcelable(KEY_DETAIL_VALUE) ?: MovieDetailResponse()
                setupViewMovie(movieDetailResponse)
            } else {
                tvShowDetailResponse =
                    savedInstanceState.getParcelable(KEY_DETAIL_VALUE) ?: TvShowDetailResponse()
                setupViewTvShow(tvShowDetailResponse)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_TITLE, from)
        if (from == "Movie") {
            outState.putParcelable(KEY_DETAIL_VALUE, movieDetailResponse)
        } else {
            outState.putParcelable(KEY_DETAIL_VALUE, tvShowDetailResponse)
        }
        super.onSaveInstanceState(outState)
    }

    private fun setupFavorite() {
        if (from == "Movie") {
            viewModel.getFavoriteMovie(id).observe(this, Observer {
                isFavorite = it
                favoriteState()
            })
        } else {
            viewModel.getFavoriteTvShow(id).observe(this, Observer {
                isFavorite = it
                favoriteState()
            })
        }
    }

    private fun addFavorite(favorite: Boolean) {
        if (from == "Movie") {
            viewModel.updateFavoriteMovie(favorite, id)
        } else {
            viewModel.updateFavoriteTvShow(favorite, id)
        }
    }

    private fun favoriteState() {
        if (isFavorite) {
            mMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite)
        } else {
            mMenu?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        mMenu = menu
        setupFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_favorite) {
            if (isFavorite) {
                PopUpMessage().popUpToast(application, getString(R.string.remove_favorite_message))
                addFavorite(false)
            } else {
                PopUpMessage().popUpToast(application, getString(R.string.add_favorite_message))
                addFavorite(true)
            }
            setupFavorite()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel(from: String?) {
        if (from == "Movie") {
            viewModel.detailMovieLiveData
                .observe(this, Observer { it?.let { setupViewMovie(it) } })
        } else {
            viewModel.detailTvShowLiveData
                .observe(this, Observer { it?.let { setupViewTvShow(it) } })
        }
    }

    private fun setupViewMovie(it: MovieDetailResponse) {
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

    private fun setupViewTvShow(it: TvShowDetailResponse) {
        tvShowDetailResponse = it
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
