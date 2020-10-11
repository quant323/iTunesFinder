package com.zedevstuds.itunesfinder.main_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zedevstuds.itunesfinder.ALBUM_ID
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.FragmentMainScreenBinding
import com.zedevstuds.itunesfinder.network.list_of_albums.AlbumModel

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainScreenViewModel
    private lateinit var observerAlbums: Observer<List<AlbumModel>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        val adapter = MainScreenAdapter(MainScreenAdapter.OnClickListener {album ->
            val bundle = Bundle()
            bundle.putParcelable(ALBUM_ID, album)
            this.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        })
        binding.albumResView.adapter = adapter



        observerAlbums = Observer { albumList ->
            adapter.submitList(albumList)
            Toast.makeText(this.context, "Size is ${albumList.size}", Toast.LENGTH_SHORT).show()
        }

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)
        viewModel.albums.observe(viewLifecycleOwner, observerAlbums)

        binding.searchBtn.setOnClickListener {
            val enteredQuery = binding.searchEditText.text.toString()
            val searchQuery = reformatQuery(enteredQuery)
            viewModel.getAlbums(reformatQuery(enteredQuery))
            Toast.makeText(this.context, searchQuery, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    private fun reformatQuery(text: String): String {
        return text.replace(" ", "+")
    }

}