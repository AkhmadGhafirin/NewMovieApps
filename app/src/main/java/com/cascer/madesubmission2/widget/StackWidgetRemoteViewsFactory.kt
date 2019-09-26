package com.cascer.madesubmission2.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.request.target.Target
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.utils.EXTRA_ITEM
import com.cascer.madesubmission2.utils.GlideApp
import com.cascer.madesubmission2.viewmodel.MainViewModel
import java.util.concurrent.ExecutionException

class StackWidgetRemoteViewsFactory(
    private val viewModel: MainViewModel, private val context: Context
) : RemoteViewsService.RemoteViewsFactory {

    private lateinit var bitmap: Bitmap

    private var favoriteList: List<FavoriteMovie> = emptyList()

    override fun onCreate() {}

    override fun getItemId(id: Int): Long = 0

    override fun onDataSetChanged() {
        favoriteList = viewModel.getFavoriteMovieList()
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val data = favoriteList[position]
        val remote = RemoteViews(context.packageName, R.layout.stack_widget_item)

        try {
            bitmap = GlideApp.with(context)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w1280/${data.posterPath}")
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        } catch (e: ExecutionException) {
            e.printStackTrace()
        }

        remote.setImageViewBitmap(R.id.image_view, bitmap)

        val extras = Bundle()
        extras.putString(EXTRA_ITEM, data.title)
        val intent = Intent()
        intent.putExtras(extras)

        remote.setOnClickFillInIntent(R.id.image_view, intent)
        return remote
    }

    override fun getCount(): Int = favoriteList.size

    override fun getViewTypeCount(): Int = 1

    override fun onDestroy() {}

    override fun getLoadingView(): RemoteViews? = null
}