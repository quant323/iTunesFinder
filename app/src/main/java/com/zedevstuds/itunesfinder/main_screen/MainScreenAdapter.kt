package com.zedevstuds.itunesfinder.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zedevstuds.itunesfinder.R
import com.zedevstuds.itunesfinder.databinding.AlbumItemBinding
import com.zedevstuds.itunesfinder.network.models.AlbumSongModel

class MainScreenAdapter(private val onClickListener: OnClickListener) : ListAdapter<AlbumSongModel, MainScreenAdapter.AlbumViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(album)
        }
        holder.bind(album)
    }


    class AlbumViewHolder(private val binding: AlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: AlbumSongModel) {
            // Устанваливаем значения в TextView
            binding.artistTextView.text = album.collectionName
            binding.albumTextView.text = album.artistName
            // Загружаем и устанавливаем картинку
            Glide.with(binding.root.context).load(album.artworkUrl100)
                .apply(RequestOptions().placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image_24))
                .into(binding.albumImageSmall)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<AlbumSongModel>() {
        override fun areItemsTheSame(oldItem: AlbumSongModel, newItem: AlbumSongModel): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AlbumSongModel, newItem: AlbumSongModel): Boolean {
            return oldItem.collectionId == newItem.collectionId
        }
    }


    class OnClickListener(val clickListener: (album: AlbumSongModel) -> Unit) {
        fun onClick(album: AlbumSongModel) = clickListener(album)
    }

}