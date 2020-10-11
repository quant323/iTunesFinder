package com.zedevstuds.itunesfinder.details_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zedevstuds.itunesfinder.ALBUM_ID
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.FragmentDetailsBinding
import com.zedevstuds.itunesfinder.databinding.FragmentMainScreenBinding
import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumModel
import com.zedevstuds.itunesfinder.network.list_of_songs.SongModel
import java.util.*

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var detailsViewModel: DetailsViewModel
    private lateinit var observerSongs: Observer<List<SongModel>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater, container, false)

        val album = arguments?.getParcelable<AlbumModel>(ALBUM_ID)
        setView(album)

        observerSongs = Observer {
            binding.listOfSongs.text = "Songs: ${it[1].trackName}"
        }

        detailsViewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        detailsViewModel.songs.observe(viewLifecycleOwner, observerSongs)

        detailsViewModel.getSongsFromAlbum(album?.collectionId)

        return binding.root
    }

    private fun setView(album: AlbumModel?) {
        if (album != null) {
            binding.artistDetailTextView.text = album.artistName
            binding.albumDetailTextView.text = album.collectionName
            binding.genreTextView.text = album.primaryGenreName
            binding.releaseDateTextView.text = album.releaseDate
            binding.priceTextView.text = album.collectionPrice.toString()

            Glide.with(this).load(album.artworkUrl100)
//                .apply(RequestOptions().placeholder(R.drawable.loading_animation)
//                    .error(R.drawable.ic_broken_image_24))
                .into(binding.albumImageBig)
        }
    }


}