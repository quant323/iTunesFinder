package com.zedevstuds.itunesfinder.details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zedevstuds.itunesfinder.ALBUM_ID
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.FragmentDetailsBinding
import com.zedevstuds.itunesfinder.getSongList
import com.zedevstuds.itunesfinder.network.models.AlbumSongModel
import com.zedevstuds.itunesfinder.trimDate

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)
        val albumId = arguments?.getLong(ALBUM_ID)

        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        viewModel.songs.observe(viewLifecycleOwner, Observer {
            setView(it)
        })
        viewModel.getSongsFromAlbum(albumId)
        return binding.root
    }

    // Устанавливает значение view-элементов экрана
    private fun setView(albumSongList: List<AlbumSongModel>) {
        // Устанавливаем данные альбома
        val album = albumSongList[0]
        binding.artistDetailTextView.text = getString(R.string.artist_name, album.artistName)
        binding.albumDetailTextView.text = getString(R.string.album_name, album.collectionName)
        binding.genreTextView.text = getString(R.string.genre, album.primaryGenreName)
        binding.releaseDateTextView.text = getString(R.string.release_date, trimDate(album.releaseDate))
        binding.priceTextView.text = getString(R.string.price, album.collectionPrice, album.currency)

        // Загружаем картинку
        Glide.with(this).load(album.artworkUrl100)
            .apply(
                RequestOptions().placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image_24)
            )
            .into(binding.albumImageBig)

        // Устанавливаем список песен
        binding.listOfSongs.text = getString(R.string.songs_list, getSongList(albumSongList))
    }

}