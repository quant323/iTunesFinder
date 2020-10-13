package com.zedevstuds.itunesfinder.main_screen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zedevstuds.itunesfinder.network.ITunesApi
import com.zedevstuds.itunesfinder.network.models.AlbumSongModel
import com.zedevstuds.itunesfinder.network.models.ResponseModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreenViewModel : ViewModel() {

    private val media = "music"
    private val resultType = "album"
    private val albumTerm = "albumTerm"
    private val explicit = "no"

    private val _albums = MutableLiveData<List<AlbumSongModel>>()
    val albums: LiveData<List<AlbumSongModel>>
        get() = _albums

    // Запрашиваем список альбомов по переданному значению
    fun getAlbums(term: String) {
        ITunesApi.retrofitService.getAlbums(term, media, resultType, albumTerm, explicit).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(call: Call<ResponseModel>, albumList: Response<ResponseModel>) {
                    // Получаем и сортируем список альбомов
                    _albums.value = albumList.body()?.results?.sortedBy { it.collectionName }
                    Log.d("mainScreen", "Success")
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    _albums.value = ArrayList()
                    Log.d("mainScreen", "Failure ${t.message}")
                }
            }
        )
    }

}