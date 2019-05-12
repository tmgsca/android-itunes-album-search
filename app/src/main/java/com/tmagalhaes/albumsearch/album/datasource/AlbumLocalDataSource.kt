package com.tmagalhaes.albumsearch.album.datasource

import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.SearchResult

interface AlbumLocalDataSource : AlbumDataSource {

    fun cacheAlbumQuery(query: String, result: SearchResult<Album>)
}