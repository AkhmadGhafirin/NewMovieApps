package com.cascer.madesubmission2.feature.main.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.cascer.madesubmission2.feature.main.favorite.movie.FavoriteMovieFragment
import com.cascer.madesubmission2.feature.main.favorite.tv_show.FavoriteTvShowFragment

class FavoriteTabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = listOf(
        FavoriteMovieFragment(),
        FavoriteTvShowFragment()
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Movies"
            }
            1 -> {
                "Tv Show"
            }
            else -> {
                ""
            }
        }
    }
}