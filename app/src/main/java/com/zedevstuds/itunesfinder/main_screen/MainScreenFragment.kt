package com.zedevstuds.itunesfinder.main_screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.zedevstuds.itunesfinder.ALBUM_ID
import com.zedevstuds.itunesfinder.LoadingStatus
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.FragmentMainScreenBinding
import com.zedevstuds.itunesfinder.reformatQuery

class MainScreenFragment : Fragment() {

    private var _binding: FragmentMainScreenBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainScreenViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        // Создаем adapter и устанавливаем onClickListener на каждый элемент списка
        val adapter = MainScreenAdapter(MainScreenAdapter.OnClickListener {
            val bundle = Bundle()
            bundle.putLong(ALBUM_ID, it.collectionId)
            // Передаем id альбома в DetailsActivity
            this.findNavController().navigate(R.id.action_mainFragment_to_detailsFragment, bundle)
        })
        binding.albumResView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainScreenViewModel::class.java)
        // Подписываемся на изменение сипска альбомов
        viewModel.albums.observe(viewLifecycleOwner, Observer {
            // Сообщаем адаптеру об изменении списка
            adapter.submitList(it)
        })

        // Подписываемся на изменение статуса загрузки
        viewModel.status.observe(viewLifecycleOwner, Observer {status ->
            when(status) {
                LoadingStatus.LOADING -> {
                    Toast.makeText(this.context, "Loadind", Toast.LENGTH_SHORT).show()
                    binding.loadingProgressBar.visibility = View.VISIBLE
                    binding.noFoundTextVeiw.visibility = View.GONE
                }
                LoadingStatus.DONE -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.noFoundTextVeiw.visibility = View.GONE
                }
                LoadingStatus.ERROR -> {
                    binding.noFoundTextVeiw.text = getString(R.string.error_message)
                    binding.noFoundTextVeiw.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.GONE
                }
                LoadingStatus.EMPTY_LIST -> {
                    binding.noFoundTextVeiw.text = getString(R.string.empty_list_text)
                    binding.noFoundTextVeiw.visibility = View.VISIBLE
                    binding.loadingProgressBar.visibility = View.GONE
                }
                else -> {
                    binding.loadingProgressBar.visibility = View.GONE
                    binding.noFoundTextVeiw.visibility = View.GONE
                }
            }
        })

        // Нажатие на кнопку поиска
        binding.searchBtn.setOnClickListener {
            val enteredQuery = binding.searchEditText.text.toString()
            if (enteredQuery.isNotEmpty()) {
                val searchQuery = reformatQuery(enteredQuery)
                viewModel.getAlbums(searchQuery)
                hideKeyboard(it)
            }
        }
        return binding.root
    }

    // Прячет soft keyboard
    private fun hideKeyboard(view: View) {
        val imm = this.context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}