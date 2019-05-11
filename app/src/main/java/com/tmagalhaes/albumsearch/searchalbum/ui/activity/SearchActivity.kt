package com.tmagalhaes.albumsearch.searchalbum.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.tmagalhaes.albumfinder.ui.viewmodel.SearchViewModel
import com.tmagalhaes.albumsearch.R
import com.tmagalhaes.albumsearch.searchalbum.model.Album
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.design.snackbar

class SearchActivity : AppCompatActivity() {

    private val viewModel = SearchViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupBindings()
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
        //TODO: Update recycler
    }

    private fun showLoading(show: Boolean) {
        progressBarContainer.visibility = if (show) View.VISIBLE else View.GONE
    }
}
