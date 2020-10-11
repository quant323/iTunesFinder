package com.zedevstuds.itunesfinder

import com.zedevstuds.itunesfinder.network.list_of_songs.SongModel

const val ALBUM_ID = "albumId"

fun reformatQuery(text: String): String {
    return text.trim().replace(" ", "+")
}

fun getSongList(songs: List<SongModel>): String {
    val result = StringBuilder("")
    for (i in 1 until songs.size) {
        result.append(i).append(". ").append(songs[i].trackName).append("\n")
    }
    return result.toString()
}