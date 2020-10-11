package com.zedevstuds.itunesfinder.main_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zedevstuds.itunesfinder.network.AlbumModel
import com.zedevstuds.itunesfinder.network.ITunesApi
import com.zedevstuds.itunesfinder.network.ResponseModel
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
        getAlbums()
    }


    private fun getAlbums() {
        ITunesApi.retrofitService.getAlbums(term, media, resultType).enqueue(
            object : Callback<ResponseModel> {
                override fun onResponse(call: Call<ResponseModel>, response: Response<ResponseModel>) {
                    _albums.value = response.body()?.results
                }

                override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                    _albums.value = ArrayList()
                }
            }
        )
    }

}