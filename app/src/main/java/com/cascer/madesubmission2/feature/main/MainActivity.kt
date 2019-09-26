package com.cascer.madesubmission2.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.feature.SettingActivity
import com.cascer.madesubmission2.feature.main.favorite.FavoriteFragment
import com.cascer.madesubmission2.feature.main.movie.MovieFragment
import com.cascer.madesubmission2.feature.main.tv_show.TvShowFragment
import com.cascer.madesubmission2.utils.KEY_FRAGMENT_SELECTED
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var fragmentSelected: Fragment = MovieFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> {
                    fragmentSelected = MovieFragment()
                    replaceFragment(fragmentSelected)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tv_show -> {
                    fragmentSelected = TvShowFragment()
                    replaceFragment(fragmentSelected)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    fragmentSelected = FavoriteFragment()
                    replaceFragment(fragmentSelected)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        if (savedInstanceState == null) {
            replaceFragment(fragmentSelected)
        } else {
            fragmentSelected =
                supportFragmentManager.getFragment(savedInstanceState, KEY_FRAGMENT_SELECTED)
                    ?: MovieFragment()
            replaceFragment(fragmentSelected)
        }

        bottom_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        supportFragmentManager.putFragment(outState, KEY_FRAGMENT_SELECTED, fragmentSelected)
        super.onSaveInstanceState(outState)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction
            .replace(R.id.container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.change_language) {
            startActivity(Intent(this@MainActivity, SettingActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}
