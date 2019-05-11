package com.tmagalhaes.albumsearch.album.datasource

import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.Outcome
import com.tmagalhaes.albumsearch.common.model.SearchResult

/**
 * Fake class just to illustrate a cache or database implementation as a DataSource
 */
class FakeAlbumLocalDataSource : AlbumDataSource {

    private var cache: Map<String, SearchResult<Album>> = emptyMap()

    override fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        cache[query]?.let {
            callback(Outcome.Success(value = it))
        } ?:  callback(Outcome.Error(message = "No data found", cause = null))
    }
}