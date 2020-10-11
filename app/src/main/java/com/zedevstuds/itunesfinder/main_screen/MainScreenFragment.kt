package com.zedevstuds.itunesfinder.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zedevstuds.itunesfinder.databinding.FragmentMainScreenBinding
import com.zedevstuds.itunesfinder.network.AlbumModel

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var screenViewModel: MainScreenViewModel
    private lateinit var observerAlbums: Observer<List<AlbumModel>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        val adapter = MainScreenAdapter()
        binding.albumResView.adapter = adapter

        observerAlbums = Observer { albumList ->
            adapter.submitList(albumList)
        }

        screenViewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)
        screenViewModel.albums.observe(viewLifecycleOwner, observerAlbums)

        return binding.root
    }

}