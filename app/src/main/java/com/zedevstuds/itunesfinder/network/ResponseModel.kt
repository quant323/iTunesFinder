package com.zedevstuds.itunesfinder.network

data class ResponseModel(
    val resultCount: Double,
    val results: List<AlbumModel>
)