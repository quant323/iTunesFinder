package com.zedevstuds.itunesfinder.details_screen

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

class DetailsViewModel : ViewModel() {

    private val entity = "song"

    private val _songs = MutableLiveData<List<AlbumSongModel>>()
    val songs: LiveData<List<AlbumSongModel>>
        get() = _songs

    // Получаем список песен и информацию об альбоме
    fun getSongsFromAlbum(albumId: Long?) {
        ITunesApi.retrofitService.getSongsFromAlbum(albumId, entity).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    _songs.value = response.body()?.results
                }
                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    _songs.value = ArrayList()
                    Log.d("detailsScreen", "Failure ${t.message}")
                }
            }
        )
    }

}