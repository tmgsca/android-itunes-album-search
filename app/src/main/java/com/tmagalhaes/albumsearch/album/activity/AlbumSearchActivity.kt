package com.tmagalhaes.albumsearch.album.activity

import android.app.Dialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.tmagalhaes.albumsearch.album.viewmodel.AlbumSearchViewModel
import com.tmagalhaes.albumsearch.R
import com.tmagalhaes.albumsearch.album.adapter.AlbumSearchRecyclerViewAdapter
import com.tmagalhaes.albumsearch.album.model.Album
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.dialog_album_detail.view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.coroutines.onQueryTextListener
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.okButton
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumSearchActivity : AppCompatActivity() {

    private val viewModel: AlbumSearchViewModel by viewModel()
    private lateinit var detailDialog: DialogInterface
    private lateinit var recyclerViewAdapter: AlbumSearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupRecyclerView()
        setupBindings()
    }

    override fun onPause() {
        super.onPause()
        detailDialog.dismiss()
    }

    private fun setupRecyclerView() {
        recyclerViewAdapter = AlbumSearchRecyclerViewAdapter(emptyList()) {
            viewModel.didSelectAlbum(it)
        }
        recyclerView.adapter = recyclerViewAdapter
    }

    private fun setupBindings() {
        viewModel.isEmpty().observe(this, Observer(::showEmptyView))
        viewModel.isLoading().observe(this, Observer(::showLoading))
        viewModel.getAlbums().observe(this, Observer(::updateRecyclerView))
        viewModel.getRequestErrorMessage().observe(this, Observer(::showErrorDialog))
        viewModel.selectedAlbum().observe(this, Observer(::showAlbumDetail))
    }

    private fun showErrorDialog(message: String?) {
        message?.let {
            rootView.snackbar(message).show()
            viewModel.didShowError()
        }
    }

    private fun showAlbumDetail(album: Album?) {
        album?.let {
           detailDialog = alert {
                val customDialogView = layoutInflater.inflate(R.layout.dialog_album_detail, null)
                customDialogView.albumPrimaryGenreName.text = it.primaryGenreName
                customDialogView.albumCollectionPrice.text = it.collectionPrice.toString()
                customDialogView.albumCurrency.text = it.currency
                customDialogView.albumCopyright.text = it.copyright
                customView = customDialogView
                okButton { dialog ->
                    viewModel.didUnselectAlbum()
                    dialog.dismiss()
                }
            }.show()
        }
    }

    private fun showEmptyView(show: Boolean) {
        emptyView.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun updateRecyclerView(albums: List<Album>) {
        recyclerViewAdapter.data = albums
        recyclerViewAdapter.notifyDataSetChanged()
    }

    private fun showLoading(show: Boolean) {
        progressBarContainer.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu.findItem(R.id.search)
        (searchItem.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            onQueryTextListener {
                onQueryTextSubmit {
                    viewModel.queryAlbums(query.toString())
                    true
                }
            }
        }
        return true
    }
}
