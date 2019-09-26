package com.cascer.favoritemovieapp.feature

import android.annotation.SuppressLint
import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.favoritemovieapp.R
import com.cascer.favoritemovieapp.data.response.FavoriteMovie
import com.cascer.favoritemovieapp.db.DBContract
import com.cascer.favoritemovieapp.utils.LoadNotesCallback
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LoadNotesCallback {

    private val adapter by lazy { FavoriteMovieAdapter { toDetail(it) } }

    private lateinit var dataObserver: DataObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRV()
        setupData()
    }

    private fun setupRV() {
        rv_favorite_movie.layoutManager =
            GridLayoutManager(
                this, 2,
                GridLayoutManager.VERTICAL, false
            )
        rv_favorite_movie.adapter = adapter
    }

    private fun setupData() {
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        dataObserver = DataObserver(handler)
        contentResolver.registerContentObserver(DBContract().CONTENT_URI, true, dataObserver)
        GetData().execute()
    }

    private fun toDetail(data: FavoriteMovie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", data.id ?: 0)
        startActivity(intent)
    }

    private fun cursorToArrayList(cursor: Cursor): ArrayList<FavoriteMovie> {
        val movies: ArrayList<FavoriteMovie> = arrayListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val title = cursor.getString(cursor.getColumnIndexOrThrow("title"))
            val poster = cursor.getString(cursor.getColumnIndexOrThrow("poster_path"))
            val voteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow("vote_average"))

            movies.add(
                FavoriteMovie(
                    id = id, title = title, posterPath = poster, voteAverage = voteAverage
                )
            )
        }

        return movies
    }

    override fun postExecute(cursor: Cursor) {
        val favoriteList = cursorToArrayList(cursor)
        if (favoriteList.size > 0) {
            adapter.insertList(favoriteList)
        } else {
            Toast.makeText(this, "Tidak Ada data saat ini", Toast.LENGTH_SHORT).show()
            adapter.insertList(emptyList())
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class GetData : AsyncTask<Void, Void, Cursor>() {

        override fun doInBackground(vararg voids: Void): Cursor? {
            return contentResolver.query(
                DBContract().CONTENT_URI,
                null,
                null,
                null,
                null
            )
        }

        override fun onPostExecute(data: Cursor) {
            super.onPostExecute(data)
            postExecute(data)
        }
    }

    private inner class DataObserver(handler: Handler) : ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            GetData().execute()
        }
    }
}
