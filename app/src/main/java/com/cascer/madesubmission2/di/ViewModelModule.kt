package com.cascer.madesubmission2.di

import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.viewmodel.DetailViewModel
import com.cascer.madesubmission2.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    single { MainRepository(androidApplication(), get(), get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { (from: String, id: Int, language: String) ->
        DetailViewModel(get(), from, id, language)
    }
}