package com.zedevstuds.itunesfinder.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumModel
import com.zedevstuds.itunesfinder.network.ITunesApi
import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainScreenViewModel : ViewModel() {

    private val term = "Master+of+Puppets"
    private val media = "music"
    private val resultType = "album"

    private val _albums = MutableLiveData<List<AlbumModel>>()
    val albums: LiveData<List<AlbumModel>>
        get() = _albums


    init {
        getAlbums(term)
    }

    private fun getAlbums(term: String) {
        ITunesApi.retrofitService.getAlbums(term, media, resultType).enqueue(
            object : Callback<AlbumListModel> {
                override fun onResponse(call: Call<AlbumListModel>, albumList: Response<AlbumListModel>) {
                    _albums.value = albumList.body()?.results
                }

                override fun onFailure(call: Call<AlbumListModel>, t: Throwable) {
                    _albums.value = ArrayList()
                }
            }
        )
    }

}