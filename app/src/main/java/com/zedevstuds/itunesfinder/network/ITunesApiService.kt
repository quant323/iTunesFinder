package com.zedevstuds.itunesfinder.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zedevstuds.itunesfinder.BuildConfig
import com.zedevstuds.itunesfinder.network.models.ResponseModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

// Для конвертации json в Kotlin data class исп. библиотеку Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Создаем объект Retrofit
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(getClient())
    .build()

// Для возможности логирования запросов Retrofit (tag in logcat - OkHttp)
fun getClient(): OkHttpClient {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()
    return client
}

// Интерфейс для совершения GET запросов
interface ITunesApiService {
    // https://itunes.apple.com/search?term=load&media=music&entity=album&attribute=albumTerm&explicit=no
    @GET("search")
    fun getAlbums(
        //  (encoded = true, иначе при совершении запроса знак + заменяется на %2B)
        @Query("term", encoded = true) term: String,    // load
        @Query("media") media: String,  // music
        @Query("entity") resultType: String, // album
        @Query("attribute") attribute: String, // albumTerm
        @Query("explicit") explicit: String // no
    ): Call<ResponseModel>

    // https://itunes.apple.com/lookup?id=1275551221&entity=song
    @GET("lookup")
    fun getSongsFromAlbum(
        @Query("id") albumId: Long?,         // 1275551221
        @Query("entity") resultType: String // song
    ): Call<ResponseModel>
}

// Для доступа к интерфейсу ITunesApiService, создаем синглтон
object ITunesApi {
    val retrofitService: ITunesApiService by lazy {
        retrofit.create(ITunesApiService::class.java)
    }
}