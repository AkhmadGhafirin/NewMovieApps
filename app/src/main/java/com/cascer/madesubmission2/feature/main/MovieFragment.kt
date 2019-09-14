package com.cascer.madesubmission2.feature.main

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
import kotlinx.android.synthetic.main.fragment_movie.*
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
        intent.putExtra("data", data)
        startActivity(intent)
    }

    private fun requestAndInsert() {
        viewModel.getNowPlayingMovie(resources.getString(R.string.language))
            .observe(this, Observer {
                it?.let {
                    adapter.insertList(it)
                }
            })
    }

//    private fun getMovieList(): List<DataItem> {
//        val result = arrayListOf<DataItem>()
//        try {
//            val inputStream = (context as MainActivity).assets.open("movies.json")
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//
//            inputStream.read(buffer)
//            inputStream.close()
//
//            var body: JSONObject? = null
//            body = JSONObject(String(buffer, StandardCharsets.UTF_8))
//            val response = Gson().fromJson(body.toString(), DataResponse::class.java)
//            if (response.data != null) {
//                result.addAll(response.data)
//            }
//        } catch (e: IOException) {
//            Log.d("IOException", "Error IOE= ${e.message}")
//        } catch (e: JSONException) {
//            Log.d("JSONException", "Error JSON= ${e.message}")
//        }
//
//        return result
//    }
}