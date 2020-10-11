package com.zedevstuds.itunesfinder.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumListModel
import com.zedevstuds.itunesfinder.network.list_of_songs.SongListModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ITunesApiService {
    // https://itunes.apple.com/search?term=Master+of+Puppets&media=music&entity=album
    @GET("search")
    fun getAlbums(
        @Query("term") term: String,    // Master+of+Puppets
        @Query("media") media: String,  // music
        @Query("entity") resultType: String // album
    ): Call<AlbumListModel>

    // https://itunes.apple.com/lookup?id=1275551221&entity=song
    @GET("lookup")
    fun getSongsFromAlbum(
        @Query("id") albumId: Long?,         // 1275551221
        @Query("entity") resultType: String // song
    ): Call<SongListModel>
}

object ITunesApi {
    val retrofitService : ITunesApiService by lazy {
        retrofit.create(ITunesApiService::class.java)
    }
}