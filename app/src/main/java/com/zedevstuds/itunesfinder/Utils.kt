package com.zedevstuds.itunesfinder

import com.zedevstuds.itunesfinder.network.models.AlbumSongModel

const val ALBUM_ID = "albumId"

fun reformatQuery(text: String): String {
    return text.trim().replace(" ", "+")
}

fun getSongList(songs: List<AlbumSongModel>): String {
    val result = StringBuilder("")
    for (i in 1 until songs.size) {
        result.append(i).append(". ").append(songs[i].trackName).append("\n")
    }
    return result.toString()
}