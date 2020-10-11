package com.zedevstuds.itunesfinder.network.list_of_albums

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumModel(
    val wrapperType: String,
    val collectionType: String,
    val artistId: Long,
    val collectionId: Long,
    val amgArtistId: Long = 0,
    val artistName: String,
    val collectionName: String,
    val collectionCensoredName: String,
 //   val artistViewUrl: String,
    val collectionViewUrl: String,
    val artworkUrl60: String,
    val artworkUrl100: String,
    val collectionPrice: Double = 0.0,
    val collectionExplicitness: String,
    val trackCount: Long,
    val copyright: String,
    val country: String,
    val currency: String,
    val releaseDate: String,
    val primaryGenreName: String
) : Parcelable