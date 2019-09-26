package com.cascer.favoritemovieapp.di

import com.cascer.favoritemovieapp.data.repository.MainRepository
import com.cascer.favoritemovieapp.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val viewModelModule = module {
    single { MainRepository(androidApplication(), get()) }
    viewModel { (id: Int) -> MainViewModel(get(), id) }
}