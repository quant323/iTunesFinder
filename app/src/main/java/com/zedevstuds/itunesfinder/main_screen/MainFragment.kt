package com.zedevstuds.itunesfinder.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.FragmentMainBinding
import java.util.*

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel
    private lateinit var observerAlbums: Observer<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        observerAlbums = Observer {
 //           val imgUri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(this).load(it).into(binding.imageTest)
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, observerAlbums)

        return binding.root
    }

}