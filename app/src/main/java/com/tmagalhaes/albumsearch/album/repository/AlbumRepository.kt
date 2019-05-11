package com.tmagalhaes.albumsearch.album.repository

import com.tmagalhaes.albumsearch.album.datasource.AlbumDataSource
import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.Outcome
import com.tmagalhaes.albumsearch.common.model.SearchResult


class AlbumRepository {

    private lateinit var remoteDataSource: AlbumDataSource //TODO: Inject
    private lateinit var localDataSource: AlbumDataSource //TODO: Inject

    /**
     * This method uses a fake cache query. The cache will always be empty. This is just to illustrate how a
     * production ready application could implement its caching policy with this architectural pattern.
     */
    fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit)  {
        localDataSource.getAlbums(query) { cacheOutcome ->
            when(cacheOutcome) {
                is Outcome.Success -> {
                    callback(cacheOutcome)
                }
                is Outcome.Error -> {
                    remoteDataSource.getAlbums(query) { remoteOutcome ->
                        callback(remoteOutcome)
                    }
                }
            }
        }
    }
}