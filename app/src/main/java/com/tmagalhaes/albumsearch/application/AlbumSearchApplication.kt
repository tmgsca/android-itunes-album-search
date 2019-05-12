package com.tmagalhaes.albumsearch.application

import android.app.Application
import com.tmagalhaes.albumsearch.common.di.albumSearchModule
import com.tmagalhaes.albumsearch.common.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AlbumSearchApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AlbumSearchApplication)
            modules(listOf(albumSearchModule, networkModule))
        }
    }
}