package com.tmagalhaes.albumsearch.common.model

import com.squareup.moshi.Json
import com.tmagalhaes.albumsearch.album.model.Album

/* I've created this wrapper class in order to be future proof, because the iTunes API returns different objects
 * depending on the specified entity, so adding support to other entities should be easier.
 */
data class SearchResult<T>(
    @field:Json(name = "resultCount") val resultCount: Int,
    @field:Json(name = "results") val results: List<T>
)