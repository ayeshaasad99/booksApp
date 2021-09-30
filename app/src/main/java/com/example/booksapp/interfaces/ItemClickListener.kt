package com.example.booksapp.interfaces

import android.view.View
import com.example.booksapp.model.Book


interface ItemClickListener {

    fun onItemClick(book: Book?, view: View)

}