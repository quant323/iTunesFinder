package com.zedevstuds.itunesfinder.details_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zedevstuds.itunesfinder.network.ITunesApi
import com.zedevstuds.itunesfinder.network.list_of_songs.SongListModel
import com.zedevstuds.itunesfinder.network.list_of_songs.SongModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsViewModel : ViewModel() {

    private val entity = "song"

    private val _songs = MutableLiveData<List<SongModel>>()
    val songs: LiveData<List<SongModel>>
        get() = _songs

    fun getSongsFromAlbum(albumId: Long?) {
        ITunesApi.retrofitService.getSongsFromAlbum(albumId, entity).enqueue(
            object : Callback<SongListModel> {
                override fun onResponse(call: Call<SongListModel>, response: Response<SongListModel>) {
                    _songs.value = response.body()?.results
                }

                override fun onFailure(call: Call<SongListModel>, t: Throwable) {
                    _songs.value = ArrayList()
                }
            }
        )
    }

}