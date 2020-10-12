package com.zedevstuds.itunesfinder.network.models

data class AlbumSongModel(
    // Поля альбомов
    val artistId: Long = -1L,
    val collectionId: Long = -1L,
    val artistName: String = "",
    val collectionName: String = "",
    val artworkUrl100: String = "",
    val collectionPrice: Double = -1.0,
    val trackCount: Long = -1L,
    val currency: String = "",
    val releaseDate: String = "",
    val primaryGenreName: String = "",

    // Поля песен
    val trackId: Long = -1L,
    val trackName: String = ""
)