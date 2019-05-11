package com.tmagalhaes.albumsearch.album.datasource

import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.Outcome
import com.tmagalhaes.albumsearch.common.model.SearchResult

interface AlbumDataSource {

    fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit)
}