package com.mivas.myukulelesongs.ui.adapter

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mivas.myukulelesongs.R
import com.mivas.myukulelesongs.database.model.Song
import com.mivas.myukulelesongs.listeners.SongsActivityListener
import kotlinx.android.synthetic.main.item_song.view.*

class SongsAdapter(private val context: Context, private val listener: SongsActivityListener) : ListAdapter<Song, SongsAdapter.SongHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Song>() {

            override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
                return oldItem.title == newItem.title && oldItem.author == newItem.author
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongHolder(itemView)
    }

    override fun onBindViewHolder(holder: SongHolder, position: Int) {
        val currentSong: Song = getItem(position)

        with(holder) {
            titleText.text = currentSong.title
            authorText.text = currentSong.author
            authorText.visibility = if (currentSong.author.isEmpty()) View.GONE else View.VISIBLE

            val builder = MenuBuilder(context)
            MenuInflater(context).inflate(R.menu.menu_song_options, builder)
            val menu = MenuPopupHelper(context, builder, moreButton)
            builder.setCallback(object : MenuBuilder.Callback {
                override fun onMenuModeChange(menu: MenuBuilder?) = Unit
                override fun onMenuItemSelected(menu: MenuBuilder?, item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.action_edit_song -> listener.onSongEditClicked(getItem(adapterPosition))
                        R.id.action_delete_song -> listener.onSongDeleteClicked(getItem(adapterPosition))
                        else -> {}
                    }
                    return false
                }
            })
            moreButton.setOnClickListener { menu.show() }
            parent.setOnClickListener { listener.onSongClicked(getItem(adapterPosition)) }
        }
    }

    inner class SongHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleText: TextView = itemView.titleText
        var authorText: TextView = itemView.authorText
        var moreButton: View = itemView.moreButton
        var parent: View = itemView
    }

}