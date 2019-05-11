package com.tmagalhaes.albumsearch.common.model

/* I've created this wrapper class in order to be future proof, because the iTunes API returns different objects
 * depending on the specified entity, so adding support to other entities should be easier.
 */
data class SearchResult<T>(val resultCount: Int, val results: List<T>)