package com.tmagalhaes.albumsearch.common.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.tmagalhaes.albumsearch.album.api.AlbumApi
import com.tmagalhaes.albumsearch.album.datasource.AlbumRemoteDataSource
import com.tmagalhaes.albumsearch.album.datasource.FakeAlbumLocalDataSource
import com.tmagalhaes.albumsearch.album.repository.AlbumRepository
import com.tmagalhaes.albumsearch.album.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.util.*

val networkModule = module {
    single {
        Moshi.Builder().add(Date::class.java, Rfc3339DateJsonAdapter()).build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com")
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }
}

val albumSearchModule = module {
    single { (get() as Retrofit).create(AlbumApi::class.java) }
    single { AlbumRemoteDataSource(get()) }
    single { FakeAlbumLocalDataSource() }
    single { AlbumRepository(get() as AlbumRemoteDataSource, get() as FakeAlbumLocalDataSource) }
    viewModel { SearchViewModel(get()) }
}