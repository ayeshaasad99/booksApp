package com.example.booksapp.model

import com.example.booksapp.model.Book
import com.google.gson.annotations.SerializedName

class Response {
    @SerializedName("items")
    var items: ArrayList<Book> = ArrayList()
}