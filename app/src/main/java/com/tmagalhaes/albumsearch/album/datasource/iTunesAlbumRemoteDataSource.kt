package com.tmagalhaes.albumsearch.album.datasource

import com.tmagalhaes.albumsearch.album.api.AlbumApi
import com.tmagalhaes.albumsearch.album.model.Album
import com.tmagalhaes.albumsearch.common.model.Outcome
import com.tmagalhaes.albumsearch.common.model.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class iTunesAlbumRemoteDataSource(private val api: AlbumApi) : AlbumRemoteDataSource {

    // We add this reference in order to cancel ongoing requests in case another one is sent before it finishes
    private var currentSearchAlbumsCall: Call<SearchResult<Album>>? = null

    override fun getAlbums(query: String, callback: (Outcome<SearchResult<Album>>) -> Unit) {
        currentSearchAlbumsCall?.cancel()
        currentSearchAlbumsCall = api.searchAlbums(query)
        currentSearchAlbumsCall?.enqueue(object : Callback<SearchResult<Album>> {
            override fun onFailure(call: Call<SearchResult<Album>>, t: Throwable?) {
                if (!call.isCanceled) {
                    currentSearchAlbumsCall = null
                    Outcome.Error(
                        message = t?.message ?: "Server request failed due to an unknown exception",
                        cause = t
                    )
                }
            }

            override fun onResponse(call: Call<SearchResult<Album>>, response: Response<SearchResult<Album>>) {
                currentSearchAlbumsCall = null
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback(Outcome.Success(value = it))
                    } ?: callback(Outcome.Error(message = "Response body is empty", cause = null))
                } else {
                    callback(Outcome.Error(message = response.message(), cause = null))
                }
            }
        })
    }
}