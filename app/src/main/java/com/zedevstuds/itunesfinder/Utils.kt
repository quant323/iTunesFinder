package com.zedevstuds.itunesfinder

const val ALBUM_ID = "albumId"

fun reformatQuery(text: String): String {
    return text.trim().replace(" ", "+")
}