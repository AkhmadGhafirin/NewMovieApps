package com.cascer.madesubmission2.di

import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    single(createOnStart = true) { MainRepository(androidApplication(), get()) }
    viewModel { MainViewModel(get()) }
}