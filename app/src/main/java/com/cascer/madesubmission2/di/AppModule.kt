package com.cascer.madesubmission2.di

import com.cascer.madesubmission2.db.MainDB
import com.cascer.madesubmission2.db.MainDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val appModule = module {
    single { MainDB.getDatabase(androidContext()) }
    single { provideMovieDao(get()) }
    single { provideExecutor() }
}

fun provideMovieDao(mainDB: MainDB): MainDao = mainDB.dao()

fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()