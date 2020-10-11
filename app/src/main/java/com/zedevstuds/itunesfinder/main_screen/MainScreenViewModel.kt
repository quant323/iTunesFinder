package com.zedevstuds.itunesfinder.main_screen

import android.util.Log
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

    private val media = "music"
    private val resultType = "album"
    private val albumTerm = "albumTerm"
    private val explicit = "no"

    private val _albums = MutableLiveData<List<AlbumModel>>()
    val albums: LiveData<List<AlbumModel>>
        get() = _albums


//    init {
//        getAlbums(term)
//    }

    fun getAlbums(term: String) {
        ITunesApi.retrofitService.getAlbums(term, media, resultType, albumTerm, explicit).enqueue(
            object : Callback<AlbumListModel> {
                override fun onResponse(call: Call<AlbumListModel>, albumList: Response<AlbumListModel>) {
                    _albums.value = albumList.body()?.results
                    Log.d("mainScreen", "Success")
                }

                override fun onFailure(call: Call<AlbumListModel>, t: Throwable) {
                    _albums.value = ArrayList()
                    Log.d("mainScreen", "Failure ${t.message}")
                }
            }
        )
    }

}