package com.tmagalhaes.albumsearch.album.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tmagalhaes.albumsearch.album.viewmodel.SearchViewModel
import com.tmagalhaes.albumsearch.R
import com.tmagalhaes.albumsearch.album.model.Album
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.snackbar
import org.koin.android.ext.android.inject

class SearchActivity : AppCompatActivity() {

    private val viewModel : SearchViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupBindings()
        viewModel.queryAlbums("Blind Guardian")
    }

    private fun setupBindings() {
        viewModel.isLoading().observe(this, Observer(::showLoading))
        viewModel.getAlbums().observe(this, Observer(::updateRecyclerView))
        viewModel.getRequestErrorMessage().observe(this, Observer(::showErrorDialog))
    }

    private fun showErrorDialog(message: String?) {
        message?.let {
            rootView.snackbar(message).show()
            viewModel.didShowError()
        }
    }

    private fun updateRecyclerView(albums: List<Album>) {
        alert(albums.map { it.artistName }.firstOrNull() ?: "No Results") {  }.show()
        //TODO: Update recycler
    }

    private fun showLoading(show: Boolean) {
        progressBarContainer.visibility = if (show) View.VISIBLE else View.GONE
    }
}
