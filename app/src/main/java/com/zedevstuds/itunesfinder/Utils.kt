package com.zedevstuds.itunesfinder

import com.zedevstuds.itunesfinder.network.models.AlbumSongModel

const val ALBUM_ID = "albumId"

enum class LoadingStatus { LOADING, ERROR, DONE, EMPTY_LIST}

// Заменяет пробелы на знак +
fun reformatQuery(text: String): String {
    return text.trim().replace("\\s+".toRegex(), "+")
}

// Возвращает список песен из полученного json листа и выводит каждую с новой строки
fun getSongList(songs: List<AlbumSongModel>): String {
    val result = StringBuilder("")
    for (i in 1 until songs.size) {
        result.append(i).append(". ").append(songs[i].trackName).append("\n")
    }
    return result.toString()
}

// Преобразует дату вида 2017-05-11T07:00:00Z в 2017-05-11
fun trimDate(date: String): String {
    return date.substring(0, date.indexOf("T"))
}