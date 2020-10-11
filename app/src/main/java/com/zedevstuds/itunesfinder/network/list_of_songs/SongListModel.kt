package com.zedevstuds.itunesfinder.network.list_of_songs

data class SongListModel(
    val resultCount: Long,
    val results: List<SongModel>
)