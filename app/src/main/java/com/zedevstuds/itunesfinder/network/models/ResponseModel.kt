package com.zedevstuds.itunesfinder.network.models

data class ResponseModel(
    val resultCount: Long,
    val results: List<AlbumSongModel>
)