package com.cascer.madesubmission2.feature.main.tv_show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.feature.detail.DetailActivity
import com.cascer.madesubmission2.feature.main.MainActivity
import com.cascer.madesubmission2.feature.main.TvShowAdapter
import com.cascer.madesubmission2.utils.KEY_TV_SHOW_LIST_VALUE
import com.cascer.madesubmission2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_tv_show.*
import kotlinx.android.synthetic.main.movie_shimmer_container.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TvShowFragment : Fragment() {

    private val adapter by lazy {
        TvShowAdapter {
            toDetail(
                it
            )
        }
    }
    private var tvShowList: ArrayList<TvShowItem> = arrayListOf()

    private val viewModel: MainViewModel by inject { parametersOf(resources.getString(R.string.language)) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv_show, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()

        if (savedInstanceState == null) {
            requestAndInsert()
        } else {
            tvShowList =
                savedInstanceState.getParcelableArrayList<TvShowItem>(KEY_TV_SHOW_LIST_VALUE)
                    ?: arrayListOf()
            adapter.insertList(tvShowList)
            if (shimmer_container != null) {
                shimmer_container.stopShimmer()
                shimmer_container.visibility = View.GONE
            }
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
        intent.putExtra("id", data.id ?: 0)
        startActivity(intent)
    }

    private fun requestAndInsert() {
        viewModel.tvShowListLiveData
            .observe(this, Observer {
                it?.let {
                    tvShowList.addAll(it)
                    adapter.insertList(it)
                    if (shimmer_container != null) {
                        shimmer_container.stopShimmer()
                        shimmer_container.visibility = View.GONE
                    }
                }
            })
    }
}