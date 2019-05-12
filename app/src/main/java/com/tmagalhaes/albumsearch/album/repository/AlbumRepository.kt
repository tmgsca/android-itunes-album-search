package com.tmagalhaes.albumsearch.album.repository

import com.tmagalhaes.albumsearch.album.datasource.AlbumDataSource
import com.tmagalhaes.albumsearch.album.datasource.AlbumLocalDataSource
import com.tmagalhaes.albumsearch.album.datasource.AlbumRemoteDataSource
import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.Outcome
import com.tmagalhaes.albumsearch.common.model.SearchResult
import org.koin.core.KoinComponent


class AlbumRepository(private val remoteDataSource: AlbumRemoteDataSource, private val localDataSource: AlbumLocalDataSource) : KoinComponent {

    /**
     * This method uses a fake cache query. The query will be cached on a HashMap after it's success in case it was not
     * previously available on the cache. On a real world situation, we would cache this on a database or another proper
     * storage.
     * This is just to illustrate how a production ready application could implement its caching policy with this
     * architectural pattern.
     */
    fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        localDataSource.getAlbums(query) { cacheOutcome ->
            when (cacheOutcome) {
                is Outcome.Success -> {
                    callback(cacheOutcome)
                }
                is Outcome.Error -> {
                    remoteDataSource.getAlbums(query) { remoteOutcome ->
                        when (remoteOutcome) {
                            is Outcome.Success ->
                                localDataSource.cacheAlbumQuery(query, remoteOutcome.value)
                        }
                        callback(remoteOutcome)
                    }
                }
            }
        }
    }
}