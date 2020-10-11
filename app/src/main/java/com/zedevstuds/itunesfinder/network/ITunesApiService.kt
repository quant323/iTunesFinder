package com.zedevstuds.itunesfinder.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
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
        @Query("term") term: String,
        @Query("media") media: String,
        @Query("entity") resultType: String
    ): Call<ResponseModel>

    // https://itunes.apple.com/lookup?id=1275551221&entity=song
    @GET("lookup")
    fun getSongsFromAlbum(
        @Query("id") albumId: Long,
        @Query("entity") resultType: String
    )
}

object ITunesApi {
    val retrofitService : ITunesApiService by lazy {
        retrofit.create(ITunesApiService::class.java)
    }
}