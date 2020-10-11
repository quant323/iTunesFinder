package com.zedevstuds.itunesfinder.network.list_of_albums

import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumModel

data class AlbumListModel(
    val resultCount: Double,
    val results: List<AlbumModel>
)