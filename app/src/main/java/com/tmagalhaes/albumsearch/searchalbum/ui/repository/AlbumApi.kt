package com.tmagalhaes.albumsearch.searchalbum.ui.repository

import com.tmagalhaes.albumsearch.searchalbum.model.Album
import com.tmagalhaes.albumsearch.common.model.SearchResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlbumApi {

    @GET("search?media=music&entity=album&attribute=artistTerm")
    fun searchAlbums(@Query("term") term: String): Call<SearchResult<Album>>

}