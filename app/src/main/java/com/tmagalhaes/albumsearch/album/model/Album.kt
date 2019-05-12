package com.tmagalhaes.albumsearch.album.model

import com.squareup.moshi.Json
import java.util.Date

data class Album(
    @field:Json(name = "artistName") val artistName: String,
    @field:Json(name = "collectionName") val collectionName: String,
    @field:Json(name = "releaseDate") val releaseDate: Date,
    @field:Json(name = "artworkUrl100") val artworkUrl: String,
    @field:Json(name = "primaryGenreName") val primaryGenreName: String,
    @field:Json(name = "collectionPrice") val collectionPrice: Float,
    @field:Json(name = "currency") val currency: String,
    @field:Json(name = "copyright") val copyright: String
)