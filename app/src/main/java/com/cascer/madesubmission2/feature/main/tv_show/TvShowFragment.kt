package com.cascer.madesubmission2.feature.main.tv_show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.feature.detail.DetailActivity
import com.cascer.madesubmission2.feature.main.MainActivity
import com.cascer.madesubmission2.utils.KEY_TV_SHOW_LIST_VALUE
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.movie_shimmer_container.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {

    private val adapter by lazy {
        TvShowAdapter {
            toDetail(
                it
            )
        }
    }
    private var tvShowList: ArrayList<TvShowItem> = arrayListOf()

    private val viewModel: MainViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupSearch()

        if (savedInstanceState == null) {
            setupViewModel()
        } else {
            tvShowList =
                savedInstanceState.getParcelableArrayList<TvShowItem>(KEY_TV_SHOW_LIST_VALUE)
                    ?: arrayListOf()
            adapter.insertList(tvShowList)
            startShimmer(false)
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList(KEY_TV_SHOW_LIST_VALUE, tvShowList)
        super.onSaveInstanceState(outState)
    }

    private fun setupRV() {
        rv_tv_show.layoutManager =
            GridLayoutManager(
                context, 2,
                GridLayoutManager.VERTICAL, false
            )
        rv_tv_show.adapter = adapter
    }

    private fun toDetail(data: TvShowItem) {
        val intent = Intent((context as MainActivity), DetailActivity::class.java)
        intent.putExtra("from", resources.getString(R.string.tv_show_label))
        intent.putExtra("type", "basic")
        intent.putExtra("data", data)
        startActivity(intent)
    }

    private fun setupSearch() {
        sv_tv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
        viewModel.searchTvShow(getString(R.string.language), query)
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
        viewModel.tvShowListLiveData
            .observe(this, Observer {
                it?.let {
                    tvShowList.addAll(it)
                    adapter.insertList(it)
                    startShimmer(false)
                }
            })
    }
}