package com.example.booksapp.ui

import android.app.Activity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.booksapp.R
import com.example.booksapp.model.Book

import com.example.booksapp.databinding.DetailViewBinding
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.booksapp.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailViewActivity : AppCompatActivity() {

    private lateinit var binding: DetailViewBinding
    lateinit var viewModel: MainViewModel
    lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailViewBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar1)
        binding.toolbar1.title = "Details"


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        book = intent.getParcelableExtra<Book>("Key")!!



        if (book != null) {

            binding.book = book
            binding.desc1.movementMethod = ScrollingMovementMethod()

        }


    }
    private fun deleteBook() {
        viewModel.delete(book.id)

        val returnIntent = Intent()
        val index = intent.getIntExtra("index",0)
        Log.d("me","$index")
        returnIntent.putExtra("index",index)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_item, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.delete -> {
          deleteBook()
            true
        }
        R.id.share -> {

            val shareIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, book.volumeInfo?.title)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Sharing"))

            true
        }
        else -> {
            super.onOptionsItemSelected(item)

        }
    }
}



