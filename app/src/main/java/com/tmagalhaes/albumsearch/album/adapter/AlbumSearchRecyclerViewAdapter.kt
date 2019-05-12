package com.tmagalhaes.albumsearch.album.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.tmagalhaes.albumsearch.R
import com.tmagalhaes.albumsearch.album.model.Album
import kotlinx.android.synthetic.main.item_album.view.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AlbumSearchRecyclerViewAdapter(var data: List<Album>, private val onClick: (Album) -> Unit) :
    RecyclerView.Adapter<AlbumSearchRecyclerViewAdapter.AlbumHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumHolder(inflated)
    }

    override fun onBindViewHolder(holder: AlbumHolder, position: Int) {
        holder.bind(data[position])
        holder.view.onClick { onClick(data[position]) }
    }

    class AlbumHolder(v: View) : RecyclerView.ViewHolder(v) {

        var view: View = v
        private var album: Album? = null

        fun bind(album: Album) {
            this.album = album
            Picasso.get().load(album.artworkUrl).fit().centerCrop().into(view.albumArtwork)
            view.albumName.text = album.collectionName
            view.albumReleaseDate.text = album.releaseDate
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ISO_DATE)
        }
    }
}
